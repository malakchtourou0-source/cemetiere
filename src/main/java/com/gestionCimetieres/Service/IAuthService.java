package com.gestionCimetieres.Service;

import com.gestionCimetieres.dto.LoginRequest;
import com.gestionCimetieres.dto.LoginResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest request);
}
