package ru.job4j.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSiteDTO {
    @Schema(description = "Registration", example = "true")
    private boolean registration;

    @Schema(description = "Login", example = "domRu")
    private String login;

    @Schema(description = "Password", example = "uu2%dfV(A2")
    private String password;

    public RegisterSiteDTO(boolean registration) {
        this.registration = registration;
    }
}
