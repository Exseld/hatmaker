package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.UtilisateurService;
import com.hat.maker.service.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UtilisateurController.class)
@ContextConfiguration(classes = {TestConfig.class, UtilisateurController.class})
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UtilisateurService utilisateurService;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void connexionUtilisateurAvecSucces() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
                .courriel("leimpact@cc.com")
                .motDePasse("1")
                .build();

        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWltcGFjdEBjYy5jb20iLCJpYXQiOjE3Mzg5NDI5OTQsImV4cCI6MTczODk0NjU5NCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFU1BPTlNBQkxFIn1dLCJpZCI6MX0.8_l_-iZ5KhG8_Zcw1fnIgyUeBKZZ2vaZpx7pZHBKUgty88FFEeEWYsjEDP2BJhD-";
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);

        when(utilisateurService.connexionUtilisateur(loginDTO)).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/connexion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(jwtAuthResponse)));
    }

    @Test
    void connexionUtilisateurAvecEchec() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
                .courriel("invalid@gmail.com")
                .motDePasse("wrongpassword")
                .build();

        when(utilisateurService.connexionUtilisateur(loginDTO)).thenThrow(new RuntimeException("Incorrect username or password"));

        mockMvc.perform(MockMvcRequestBuilders.post("/connexion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(status().isBadRequest());
    }
}