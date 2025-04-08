package ru.job4j.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.request.UrlRequestDTO;
import ru.job4j.dto.response.ConvertDTO;
import ru.job4j.dto.response.StatisticDTO;
import ru.job4j.jwt.JwtUtils;
import ru.job4j.service.UrlService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static ru.job4j.utils.UrlHandler.extractLastSegment;

@Tag(name = "UrlController", description = "UrlController management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(
            summary = "Converting url",
            description = "Converting url to code by specifying object UrlRequestDTO. The response is created ConvertDTO object with code.",
            tags = {"ConvertDTO", "urlConvert"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = ConvertDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})

    @PostMapping("/convert")
    public ResponseEntity<ConvertDTO> urlConvert(@Valid @RequestBody UrlRequestDTO urlRequest) {
        ConvertDTO convertDTO = urlService.convert(urlRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertDTO);
    }

    @Operation(
            summary = "Redirection to source url",
            description = "Redirection to source url by specifying code.",
            tags = {"redirect"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})

    @GetMapping("/redirect/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> redirect(@PathVariable("code")
                                         String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlService.findUrlByCode(code)));
        urlService.callsCounterIncrement(code);
        return new ResponseEntity<>(headers, HttpStatus.MOVED_TEMPORARILY);
    }

    /* арльтернативный способ редиректа: другая обработка параметров */
    @GetMapping("/redirect2/**")
    public ResponseEntity<Void> redirect2(HttpServletRequest httpServletRequest) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(
                urlService.findUrlByCode(extractLastSegment(httpServletRequest.getRequestURI()))));
        urlService.callsCounterIncrement(extractLastSegment(httpServletRequest.getRequestURI()));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_TEMPORARILY);
    }

    @Operation(
            summary = "Retrieve list of all links to the site with total calls count",
            description = "Get list of all links to the site. The response is list of StatisticDTO-objects.",
            tags = {"StatisticDTO", "statistic"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = StatisticDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})

    @GetMapping("/statistic/{siteName}")
    @ResponseStatus(HttpStatus.OK)
    public List<StatisticDTO> statistic(@PathVariable("siteName")
                                        String siteName) {
        return urlService.statistic(siteName);
    }
}