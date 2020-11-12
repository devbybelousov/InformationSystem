package com.gazprom.InforamtionSystem.payload;

import com.gazprom.InforamtionSystem.model.Role;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;

    public JwtAuthenticationResponse(String accessToken, Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
