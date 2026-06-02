package edu.ut.convocatoria.service.impl;

import edu.ut.convocatoria.domain.dto.request.UserLoginRequestDTO;
import edu.ut.convocatoria.domain.dto.request.UserRequestDTO;
import edu.ut.convocatoria.domain.dto.response.UserResponseDTO;
import edu.ut.convocatoria.domain.enumerated.ExceptionTypes;
import edu.ut.convocatoria.domain.exceptions.ConvocatoriaException;
import edu.ut.convocatoria.mapper.UserMapper;
import edu.ut.convocatoria.repository.UserRepository;
import edu.ut.convocatoria.security.JwtTokenProvider;
import edu.ut.convocatoria.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static java.util.Collections.emptyList;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            UserMapper userMapper,
            @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(usr -> new User(usr.getUsername(), usr.getPassword(), emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        var username = userRequestDTO.username();

        var encodedPassword = passwordEncoder.encode(userRequestDTO.password());

        var userEntity = userMapper.toEntity(
                userRequestDTO,
                username,
                encodedPassword,
                new HashSet<>()
        );
        userEntity.setActive(true);


        try {
            var userSaved = userRepository.save(userEntity);

            Authentication auth = new UsernamePasswordAuthenticationToken(username, encodedPassword, emptyList());
            final var token = jwtTokenProvider.generateToken(auth);

            return userMapper.toDTO(userSaved, token);
        } catch (DataIntegrityViolationException e) {
            throw new ConvocatoriaException(
                    "There was an error while saving the user.",
                    ExceptionTypes.TECHNICAL_ERROR
            );
        }

    }

    @Override
    public UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.username(),
                        userLoginRequestDTO.password()
                )
        );

        final var token = jwtTokenProvider.generateToken(auth);

        return userRepository
                .findByUsername(userLoginRequestDTO.username())
                .map(usr -> new UserResponseDTO(usr.getId(), token, usr.isActive()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userLoginRequestDTO.username()));
    }
}
