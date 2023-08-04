package dev.vultureweb.generator.domain;

import java.util.UUID;

public record Person(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String jobRole,
        boolean rating
) {

}
