package edu.ut.convocatoria.controller;

import edu.ut.convocatoria.domain.dto.request.UserLoginRequestDTO;
import edu.ut.convocatoria.domain.dto.request.UserRequestDTO;
import edu.ut.convocatoria.domain.dto.response.ErrorResponseDTO;
import edu.ut.convocatoria.domain.dto.response.UserResponseDTO;
import edu.ut.convocatoria.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(
        name = "Autenticacion",
        description = "Endpoints publicos los cuales le permiten al usuario iniciar sesion y registrarse."
)
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "Permite crear un nuevo usuario en el sistema y retorna el token JWT para un acceso inicial.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno debido a duplicidad o fallos en base de datos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        return ResponseEntity.ok(userService.createUser(userRequestDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar Sesión", description = "Valida las credenciales del usuario y genera un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticacion exitosa",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas o usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<UserResponseDTO> login(
            @Valid @RequestBody UserLoginRequestDTO userRequestDTO
    ) {
        return ResponseEntity.ok(userService.login(userRequestDTO));
    }
}
