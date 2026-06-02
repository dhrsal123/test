package edu.ut.convocatoria.controller;

import edu.ut.convocatoria.domain.dto.request.UserLoginRequestDTO;
import edu.ut.convocatoria.domain.dto.request.UserRequestDTO;
import edu.ut.convocatoria.domain.dto.response.UserResponseDTO;
import edu.ut.convocatoria.service.UserService;
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
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        return ResponseEntity.ok(userService.createUser(userRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(
            @Valid @RequestBody UserLoginRequestDTO userRequestDTO
    ) {
        return ResponseEntity.ok(userService.login(userRequestDTO));
    }
}
