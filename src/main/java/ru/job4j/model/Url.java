package ru.job4j.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urls")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$",
            message = "url должен соответствовать паттерну URL")
    private String url;

    @NotBlank(message = "code не может быть пустым")
    @Length(min = 4,
            max = 15,
            message = "code должен быть не менее 4 и не более 15 символов")
    private String code;

    @Version
    private long callsCount;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    public Url(String url, String code, Site site) {
        this.url = url;
        this.code = code;
        this.callsCount = 0;
        this.site = site;
    }
}