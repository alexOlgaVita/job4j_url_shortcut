package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.dto.request.SignupRequestDTO;
import ru.job4j.dto.request.SignupSiteRequestDTO;
import ru.job4j.dto.response.RegisterDTO;
import ru.job4j.dto.response.RegisterSiteDTO;
import ru.job4j.model.Site;
import ru.job4j.repository.SiteRepository;

import static ru.job4j.utils.StringReform.generateCommonLangPassword;
import static ru.job4j.utils.StringReform.getLoginFromDomain;

@Service
@AllArgsConstructor
public class SiteService {
    private PasswordEncoder encoder;
    private final SiteRepository siteRepository;

    public RegisterDTO signUp(SignupRequestDTO signUpRequest) {
        if (Boolean.TRUE.equals(siteRepository.existsByDomain(signUpRequest.getDomain()))) {
            return new RegisterDTO(HttpStatus.BAD_REQUEST, "Error: Site is already taken!");
        }

        Site site = new Site(signUpRequest.getDomain(), signUpRequest.getLogin(),
                encoder.encode(signUpRequest.getPassword()));

        siteRepository.save(site);
        return new RegisterDTO(HttpStatus.OK, "Site registered successfully!");
    }

    public RegisterSiteDTO registration(SignupSiteRequestDTO signUpRequest2) {
        if (Boolean.TRUE.equals(siteRepository.existsByDomain(signUpRequest2.getSite()))) {
            return new RegisterSiteDTO(false);
        }

        var login = getLoginFromDomain(signUpRequest2.getSite());
        var password = generateCommonLangPassword();
        System.out.println(String.format("login = %s, password = %s", login, password));

        Site site = new Site(signUpRequest2.getSite(), login,
                encoder.encode(password));

        siteRepository.save(site);
        return new RegisterSiteDTO(true, login, password);
    }
}