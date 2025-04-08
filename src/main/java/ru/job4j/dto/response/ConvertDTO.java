package ru.job4j.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertDTO {
    @Schema(description = "code", example = "Yd3c7ST9")
    private String code;
}
