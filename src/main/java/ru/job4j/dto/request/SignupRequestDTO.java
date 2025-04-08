package ru.job4j.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
    @Schema(description = "domain", example = "dom.ru")
    @NotBlank
    @Size(max = 50)
    private String domain;

    @Schema(description = "Login", example = "domRu")
    @NotBlank
    @Size(min = 3, max = 20)
    private String login;

    @Schema(description = "Password", example = "uu2%dfV(A2")
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}