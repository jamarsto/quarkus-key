package uk.co.jasonmarston.key.output.port;

import io.smallrye.mutiny.Uni;

public interface KeyOutputPort {
    Uni<String> readKey();
}
