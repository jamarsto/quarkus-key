package uk.co.jasonmarston.key.input.port;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uk.co.jasonmarston.key.output.port.KeyOutputPort;
import uk.co.jasonmarston.key.usecase.KeyUseCase;

@ApplicationScoped
public class KeyInputPort implements KeyUseCase {
    @Inject
    private KeyOutputPort keyOutputPort;

    @Override
    public Uni<String> readKey() {
        return keyOutputPort.readKey();
    }
}
