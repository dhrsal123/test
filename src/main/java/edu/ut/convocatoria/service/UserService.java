package edu.ut.convocatoria.service;

import edu.ut.convocatoria.domain.dto.request.UserLoginRequestDTO;
import edu.ut.convocatoria.domain.dto.request.UserRequestDTO;
import edu.ut.convocatoria.domain.dto.response.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);
}
