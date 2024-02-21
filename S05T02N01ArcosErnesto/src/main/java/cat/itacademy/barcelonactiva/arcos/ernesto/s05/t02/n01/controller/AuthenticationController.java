package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.controller;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.request.SignUpRequest;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.request.SigninRequest;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diceGame/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "SignUp new User")
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @Operation(summary = "SignIn User")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}