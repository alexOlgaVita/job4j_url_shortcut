package ru.job4j.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @Schema(description = "Status", example = "Created")
    private HttpStatus status;

    @Schema(description = "Message", example = "Успешно зарегистриован")
    private String message;
}
