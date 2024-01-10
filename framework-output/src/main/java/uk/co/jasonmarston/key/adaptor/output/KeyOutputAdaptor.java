package uk.co.jasonmarston.key.adaptor.output;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import uk.co.jasonmarston.key.output.port.KeyOutputPort;

import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;

@ApplicationScoped
@Slf4j
public class KeyOutputAdaptor implements KeyOutputPort {
    private static final String ERROR_MESSAGE =
        "Certificate Not Loaded From: {}";

    private String pemString;

    @Inject
    public KeyOutputAdaptor(
        @ConfigProperty(name = "key.certificate.location")
        final String certificateLocation
    ) {
        try {
            final RSAPublicKey publicKey = RSAPublicKeyUtility
                .getInstance()
                .getPublicKey(certificateLocation);
            this.pemString = PEMUtility
                .getInstance()
                .convertToPEM(publicKey);
        }
        catch(final CertificateException ce) {
            log.error(ERROR_MESSAGE, certificateLocation);
            this.pemString = null;
        }
    }

    @Override
    public Uni<String> readKey() {
        if(null == pemString) {
            return Uni
                .createFrom()
                .nullItem();
        }
        return Uni
            .createFrom()
            .item(pemString);
    }
}
