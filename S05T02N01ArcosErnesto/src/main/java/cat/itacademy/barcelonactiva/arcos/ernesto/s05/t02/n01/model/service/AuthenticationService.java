package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.request.SignUpRequest;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.request.SigninRequest;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
