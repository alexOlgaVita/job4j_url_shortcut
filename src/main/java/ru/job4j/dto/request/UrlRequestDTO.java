package ru.job4j.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequestDTO {
    @NotBlank
    @Schema(description = "url", example = "https://dom.ru/podcasts")
    private String url;
}