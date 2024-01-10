package uk.co.jasonmarston.key.adaptor.output;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import uk.co.jasonmarston.key.output.port.KeyOutputPort;

@ApplicationScoped
@Slf4j
public class KeyOutputAdaptor implements KeyOutputPort {
    private final String pemString;

    @Inject
    public KeyOutputAdaptor(
        @ConfigProperty(name = "key.certificate.location")
        final String certificateLocation
    ) {
        this.pemString = PEMUtility
            .getInstance()
            .convertToPEM(
                RSAPublicKeyUtility
                    .getInstance()
                    .getPublicKey(certificateLocation)
            );
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
