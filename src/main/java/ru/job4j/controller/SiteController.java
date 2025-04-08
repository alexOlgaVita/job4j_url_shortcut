package ru.job4j.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.request.LoginRequestDTO;
import ru.job4j.dto.request.SignupSiteRequestDTO;
import ru.job4j.dto.response.JwtResponseDTO;
import ru.job4j.dto.response.RegisterSiteDTO;
import ru.job4j.jwt.JwtUtils;
import ru.job4j.service.SiteService;
import ru.job4j.userdetails.SiteDetailsImpl;

@Tag(name = "SiteController", description = "SiteController management APIs")
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(
            summary = "Registration a new site",
            description = "Registration a new Site by specifying object SignupSiteRequestDTO. The response is "
                    + "created RegisterSiteDTO object with login and password.",
            tags = {"RegisterSiteDTO", "registerSite"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = RegisterSiteDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})

    @PostMapping("/registration")
    public ResponseEntity<RegisterSiteDTO> registerSite(@Valid @RequestBody SignupSiteRequestDTO signUpRequest) {
        RegisterSiteDTO registerSiteDTO = siteService.registration(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerSiteDTO);

    }

    @Operation(
            summary = "Sign in",
            description = "Signing by specifying object LoginRequestDTO. The response is created JwtResponseDTO object.",
            tags = {"Site", "authenticateSite"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RegisterSiteDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())})})

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDTO> authenticateSite(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getLogin(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        SiteDetailsImpl userDetails = (SiteDetailsImpl) authentication.getPrincipal();
        return ResponseEntity
                .ok(new JwtResponseDTO(jwt, userDetails.getId(), userDetails.getDomain(), userDetails.getUsername()));
    }
}