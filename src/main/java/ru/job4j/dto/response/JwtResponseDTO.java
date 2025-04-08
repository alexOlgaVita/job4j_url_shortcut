package ru.job4j.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JwtResponseDTO {
    @Schema(description = "Token",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJheGVuaXhQcm8iLCJpYXQiOjE3NDQxNDQ5NzIsImV4cCI6MTc0NDE0ODU3Mn0."
                    + "Mx0iMY4f1LvEHwLT0CBDnNWejS3BFPmWq_t12nWlhc0")
    private String token;

    @Schema(description = "Type of authorization")
    private String type = "Bearer";

    @Schema(description = "Id", example = "1")
    private Long id;

    @Schema(description = "Domain", example = "dom.ru")
    private String domain;

    @Schema(description = "Login", example = "domRu")
    private String login;

    public JwtResponseDTO(String accessToken, Long id, String domain, String login) {
        this.token = accessToken;
        this.id = id;
        this.domain = domain;
        this.login = login;
    }
}
