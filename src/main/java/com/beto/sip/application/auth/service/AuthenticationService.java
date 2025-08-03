package com.beto.sip.application.auth.service;

import com.beto.sip.infrastructure.web.dto.LoginRequest;
import com.beto.sip.infrastructure.web.dto.LoginResponse;
import com.beto.sip.infrastructure.web.dto.ApiResponse;

public interface AuthenticationService {
    ApiResponse<LoginResponse> login(LoginRequest request);
}
