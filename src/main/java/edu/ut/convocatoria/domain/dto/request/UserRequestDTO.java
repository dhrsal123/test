package edu.ut.convocatoria.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para el registro de un nuevo usuario en el sistema.")
public record UserRequestDTO(
        @Schema(description = "Correo electrónico.", example = "pepito.perez@correo.com")
        @Email
        String email,

        @Schema(description = "Nombre de usuario para el inicio de sesión.", example = "juan.perez123")
        @NotEmpty
        @Size(min = 10, max = 35)
        String username,

        @Schema(description = "Nombre del usuario.", example = "Juan Pepe Perez")
        @NotEmpty
        @Size(min = 10, max = 35)
        String name,

        @Schema(description = "Contraseña de acceso.", example = "MiContraseñaSegura1!")
        @NotEmpty
        @Size(min = 8, max = 20)
        String password

) {
}
