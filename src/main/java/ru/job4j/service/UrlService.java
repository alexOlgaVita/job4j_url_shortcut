package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.dto.request.UrlRequestDTO;
import ru.job4j.dto.response.ConvertDTO;
import ru.job4j.dto.response.StatisticDTO;
import ru.job4j.model.Url;
import ru.job4j.repository.SiteRepository;
import ru.job4j.repository.UrlRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.job4j.utils.StringReform.generateCodeWithoutSpecialSymbol;
import static ru.job4j.utils.UrlHandler.DOMAIN_PATTERN;
import static ru.job4j.utils.UrlHandler.URL_PATTERN;

@Service
@AllArgsConstructor
public class UrlService {
    private PasswordEncoder encoder;
    private final UrlRepository urlRepository;
    private final SiteRepository siteRepository;

    public ConvertDTO convert(UrlRequestDTO urlRequestDTO) {
        if (!Pattern.compile(URL_PATTERN).matcher(urlRequestDTO.getUrl()).find()) {
            throw new IllegalArgumentException("Ссылка не соот-ет паттеру url");
        }
        Matcher matcher = Pattern.compile(DOMAIN_PATTERN).matcher(urlRequestDTO.getUrl());
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("Доменное имя '%s' не соответствует паттерну домена", urlRequestDTO.getUrl()));
        }
        var siteName = matcher.group();
        if (!siteRepository.existsByDomain(siteName)) {
            throw new IllegalArgumentException(String.format("Для начала зарегистрируйте сайт '%s'", siteName));
        }
        if (Boolean.TRUE.equals(urlRepository.existsByUrl(urlRequestDTO.getUrl()))) {
            return new ConvertDTO(urlRepository.findByUrl(urlRequestDTO.getUrl()).get().getCode());
        }
        var code = generateCodeWithoutSpecialSymbol();
        Url url = new Url(urlRequestDTO.getUrl(), code, siteRepository.findByDomain(siteName).get());
        urlRepository.save(url);
        return new ConvertDTO(code);
    }

    public String findUrlByCode(String code) {
        return urlRepository.findByCode(code).get().getUrl();
    }

    public String callsCounterIncrement(String code) {
        var url = urlRepository.findByCode(code).get();
        urlRepository.save(new Url(url.getId(), url.getUrl(), url.getCode(), url.getCallsCount() + 1,
                url.getSite()));

        return urlRepository.findByCode(code).get().getUrl();
    }

    public List<StatisticDTO> statistic(String site) {
        if (!siteRepository.existsByDomain(site)) {
            throw new IllegalArgumentException(String.format("Такой сайт '%s' не зарегистрирован", site));
        }

        return urlRepository.findAllUrlBySite(siteRepository.findByDomain(site).get())
                .stream().map(url -> new StatisticDTO(url.getUrl(), url.getCallsCount()))
                .toList();
    }
}