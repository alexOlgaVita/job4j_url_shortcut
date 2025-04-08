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
@Table(name = "sites")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^(?:www\\.)?[A-Za-z0-9-]{1,63}\\.[A-Za-z]{2,6}$",
            message = "domain должен соответствовать паттерну домена")
    private String domain;

    @NotBlank(message = "login не может быть пустым")
    @Length(min = 4,
            max = 15,
            message = "login должен быть не менее 4 и не более 15 символов")
    private String login;

    @NotBlank(message = "password не может быть пустым")
    @Length(min = 12,
            message = "password должен быть не 12 символов")
    private String password;

    public Site(String domain, String login, String password) {
        this.domain = domain;
        this.login = login;
        this.password = password;
    }
}