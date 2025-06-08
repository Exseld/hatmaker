package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.repository.MoniteurRepository;
import com.hat.maker.service.MoniteurService;
import com.hat.maker.service.dto.MoniteurCreeDTO;
import com.hat.maker.service.dto.MoniteurDTO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MoniteurController.class)
@ContextConfiguration(classes = {TestConfig.class, MoniteurController.class})
public class MoniteurControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private MoniteurService moniteurService;

    @MockitoBean
    private MoniteurRepository moniteurRepository;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerMoniteurAvecSucces() throws Exception {
        MoniteurCreeDTO moniteurCreeDTO = MoniteurCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimapct@cc.com")
                .motDePasse("1")
                .build();

        MoniteurDTO moniteurDTO = MoniteurDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .build();

        when(moniteurService.createMoniteur(moniteurCreeDTO)).thenReturn(moniteurDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/moniteur/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(moniteurCreeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerMoniteurAvecNomNull() throws Exception {
        MoniteurCreeDTO moniteurCreeDTO = MoniteurCreeDTO.builder()
                .id(1L)
                .nom(null)
                .courriel("leimpact@cc.com")
                .motDePasse("1")
                .build();

        when(moniteurService.createMoniteur(moniteurCreeDTO)).thenThrow(new IllegalArgumentException("Nom NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/moniteur/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(moniteurCreeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerMoniteurAvecEmailExistant() throws Exception {
        MoniteurCreeDTO moniteurCreeDTO = MoniteurCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .motDePasse("1")
                .build();

        when(moniteurService.createMoniteur(any(MoniteurCreeDTO.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/moniteur/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(moniteurCreeDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void creerMoniteurAvecCourrielNull() throws Exception {
        MoniteurCreeDTO moniteurCreeDTO = MoniteurCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel(null)
                .motDePasse("1")
                .build();

        when(moniteurService.createMoniteur(moniteurCreeDTO)).thenThrow(new IllegalArgumentException("Courriel NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/moniteur/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(moniteurCreeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerMoniteurAvecMotDePasseNull() throws Exception {
        MoniteurCreeDTO moniteurCreeDTO = MoniteurCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .motDePasse(null)
                .build();

        when(moniteurService.createMoniteur(moniteurCreeDTO)).thenThrow(new IllegalArgumentException("Mot de passe NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/moniteur/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(moniteurCreeDTO)))
                .andExpect(status().isBadRequest());
    }
}