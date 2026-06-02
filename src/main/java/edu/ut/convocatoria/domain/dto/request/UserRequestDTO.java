package edu.ut.convocatoria.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @Email
        String email,

        @NotEmpty
        @Size(min = 10, max = 35)
        String username,

        @NotEmpty
        @Size(min = 10, max = 35)
        String name,

        @NotEmpty
        @Size(min = 8, max = 20)
        String password

) {
}
