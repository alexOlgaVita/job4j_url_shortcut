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
public class SignupSiteRequestDTO {
    @Schema(description = "site", example = "dom.ru")
    @NotBlank
    @Size(max = 15)
    private String site;
}