package uk.co.jasonmarston.key.usecase;

import io.smallrye.mutiny.Uni;

public interface KeyUseCase {
    Uni<String> readKey();
}
