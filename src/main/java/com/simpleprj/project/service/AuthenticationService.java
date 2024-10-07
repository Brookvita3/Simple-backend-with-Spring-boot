package com.simpleprj.project.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.simpleprj.project.dto.request.IntroSpectRequest;
import com.simpleprj.project.dto.response.AuthenticationResponse;
import com.simpleprj.project.dto.response.IntrospectResponse;
import com.simpleprj.project.entity.User;
import com.simpleprj.project.exception.AppException;
import com.simpleprj.project.exception.ErrorCode;
import com.simpleprj.project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component

public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;

    @NonFinal
    @Value("${SECRET_KEY}")
    private String SECRET;

    public AuthenticationResponse checkPassword(String username, String password) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)) ;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(password, user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // xac thuc token tai day
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .success(true)
                .build();


    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("simpleprj.com")
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("can't create token");
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntroSpectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SECRET.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expirationDate.after(new Date()))
                .build();

    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
//        if (user.getRoles() != null) {
//            user.getRoles().forEach(stringJoiner::add);
//        }
        return stringJoiner.toString();
    }
}
