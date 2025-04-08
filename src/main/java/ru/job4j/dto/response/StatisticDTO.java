package ru.job4j.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    @Schema(description = "url", example = "https://dom.ru/podcasts")
    private String url;

    @Schema(description = "url", example = "3")
    private Long total;
}
