package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.request.SignUpRequest;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.request.SigninRequest;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.User;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.enums.Role;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.BadCredentialsException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.UserAlreadyExistException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.AuthenticationService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        Optional<User> isPresent = userRepository.findByEmail(request.getEmail());
        if (isPresent.isPresent()) {
            throw new UserAlreadyExistException("Email ya registrado.");
        }

        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();

    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Email o contraseña inválidos."));
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        } catch (Exception e) {
            throw new BadCredentialsException("Usuario o contraseña no válida.");
        }
    }
}

