package com.simpleprj.project.configuration;

import com.nimbusds.jose.JOSEException;
import com.simpleprj.project.dto.request.IntroSpectRequest;
import com.simpleprj.project.exception.AppException;
import com.simpleprj.project.exception.ErrorCode;
import com.simpleprj.project.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;


@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${SECRET_KEY}")
    String SECRET_KEY;

    final AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    public CustomJwtDecoder(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var auth = authenticationService.introspect(IntroSpectRequest.builder().token(token).build());
            if (!auth.isValid()) throw new AppException(ErrorCode.UNAUTHENTICATED);
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
