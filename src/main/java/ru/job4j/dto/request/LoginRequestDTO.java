package ru.job4j.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @Schema(description = "Login", example = "mySite")
    @NotBlank
    private String login;

    @Schema(description = "Password", example = "uu2%dfV(A2")
    @NotBlank
    private String password;

}
