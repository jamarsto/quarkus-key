package uk.co.jasonmarston.key.adaptor.output;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import uk.co.jasonmarston.key.output.port.KeyOutputPort;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@ApplicationScoped
@Slf4j
public class KeyOutputAdaptor implements KeyOutputPort {
    private final static String CERTIFICATE_TYPE = "X.509";
    private static final int LINE_WIDTH = 64;
    private static final String NEW_LINE = "\n";
    private static final String BEGIN_PUBLIC_KEY =
        "-----BEGIN PUBLIC KEY-----";
    private static final String END_PUBLIC_KEY =
        "-----END PUBLIC KEY-----";
    private static final String ERROR_MESSAGE =
        "Certificate Not Loaded From: {}";

    private final String pemString;

    @Inject
    public KeyOutputAdaptor(
        @ConfigProperty(name = "key.certificate.location")
        final String certificateLocation
    ) {
        String tmpPemString = null;
        try {
            final InputStream inputStream =	InputStreamUtility
                .getInstance()
                .getInputStream(certificateLocation);
            final X509Certificate certificate =
                (X509Certificate) CertificateFactory
                    .getInstance(CERTIFICATE_TYPE)
                    .generateCertificate(inputStream);
            final RSAPublicKey publicKey = (RSAPublicKey) certificate
                .getPublicKey();
            tmpPemString = convertToPEM(publicKey);
        }
        catch(final CertificateException ce) {
            log.error(ERROR_MESSAGE, certificateLocation);
        }
        this.pemString = tmpPemString;
    }

    @Override
    public Uni<String> readKey() {
        return Uni
            .createFrom()
            .item(pemString);
    }

    private String convertToPEM(final RSAPublicKey publicKey) {
        final byte[] publicKeyBytes = publicKey.getEncoded();
        final String publicKeyBase64 = Base64
            .getEncoder()
            .encodeToString(publicKeyBytes);
        final StringBuilder builder = new StringBuilder();

        builder.append(BEGIN_PUBLIC_KEY);
        builder.append(NEW_LINE);

        int index = 0;
        while (index < publicKeyBase64.length()) {
            final String line = publicKeyBase64.substring(
                index,
                Math.min(index + LINE_WIDTH, publicKeyBase64.length())
            );
            builder.append(line);
            builder.append(NEW_LINE);
            index += LINE_WIDTH;
        }

        builder.append(END_PUBLIC_KEY);

        return builder.toString();
    }
}
