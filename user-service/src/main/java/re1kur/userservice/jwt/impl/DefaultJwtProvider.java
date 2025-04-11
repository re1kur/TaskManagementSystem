package re1kur.userservice.jwt.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import re1kur.userservice.entity.Role;
import re1kur.userservice.entity.User;
import re1kur.userservice.exception.VerificationJwtTokenException;
import re1kur.userservice.jwt.JwtProvider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

@Slf4j
@Component
public class DefaultJwtProvider implements JwtProvider {
    @Value("${custom.jwt.privateKeyPath}")
    private String privateKeyPath;

    @Value("${custom.jwt.publicKeyPath}")
    private String publicKeyPath;

    @Value("${custom.jwt.keySize}")
    private int keySize;

    private static final ObjectMapper serializer = new ObjectMapper();

    @Override
    public String provide(User user) {
        return generate(new Credentials(user.getId(), user.getEmail(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toList()));
    }

    @SneakyThrows
    public String generate(Credentials credentials) {
        if (!Files.exists(Paths.get(publicKeyPath))) {
            generateKeyPair();
        }

        RSAPrivateKey privateKey = readPrivateKeyFromFile(privateKeyPath);
        RSAPublicKey publicKey = readPublicKeyFromFile(publicKeyPath);
        RSASSASigner signer = new RSASSASigner(privateKey);


        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();


        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .claim("sub", credentials.id())
                .claim("email", credentials.email())
                .claim("scope", serializer.writeValueAsString(credentials.roles()))
                .build();


        SignedJWT jwt = new SignedJWT(header, payload);
        jwt.sign(signer);

        verifyJWTSign(publicKey, jwt);

        return jwt.serialize();
    }


    @SneakyThrows
    private void generateKeyPair() {
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
        pairGenerator.initialize(keySize);
        KeyPair keyPair = pairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Files.write(Paths.get(publicKeyPath), publicKey.getEncoded(), StandardOpenOption.CREATE_NEW);
        Files.write(Paths.get(privateKeyPath), privateKey.getEncoded(), StandardOpenOption.CREATE_NEW);
    }

    @SneakyThrows
    private RSAPrivateKey readPrivateKeyFromFile(String path) {
        byte[] keyContent = Files.readAllBytes(Paths.get(path));
        return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(keyContent));
    }

    @SneakyThrows
    private RSAPublicKey readPublicKeyFromFile(String path) {
        byte[] keyContent = Files.readAllBytes(Path.of(path));
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(keyContent));
    }

    @SneakyThrows
    private void verifyJWTSign(RSAPublicKey publicKey, SignedJWT jwt) throws VerificationJwtTokenException {
        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        boolean verify = verifier.verify(jwt.getHeader(), jwt.getSigningInput(), jwt.getSignature());
        log.info("Test verification with public key: {}", verify ? "DONE" : "FAILED");
        if (!verify) throw new VerificationJwtTokenException("JWT verification failed");
    }

    public record Credentials(Long id, String email, List<String> roles) {
    }
}
