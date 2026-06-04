package edu.ut.convocatoria.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciales del usuario para iniciar sesión.")
public record UserLoginRequestDTO(
        @Schema(description = "Nombre de usuario registrado.", example = "pepito.perez123")
        @NotEmpty
        @Size(min = 10, max = 35)
        String username,

        @Schema(description = "Contraseña de acceso.", example = "MiContraseñaSegura1!")
        @NotEmpty
        @Size(min = 8, max = 20)
        String password

) {
}
