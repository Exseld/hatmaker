package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.repository.SpecialisteRepository;
import com.hat.maker.service.SpecialisteService;
import com.hat.maker.service.dto.SpecialisteCreeDTO;
import com.hat.maker.service.dto.SpecialisteDTO;
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
@WebMvcTest(SpecialisteController.class)
@ContextConfiguration(classes = {TestConfig.class, SpecialisteController.class})
public class SpecialisteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private SpecialisteService specialisteService;

    @MockitoBean
    private SpecialisteRepository specialisteRepository;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerSpecialisteAvecSucces() throws Exception {
        SpecialisteCreeDTO specialisteCreeDTO = SpecialisteCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimapct@cc.com")
                .motDePasse("1")
                .build();

        SpecialisteDTO specialisteDTO = SpecialisteDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .build();

        when(specialisteService.createSpecialiste(specialisteCreeDTO)).thenReturn(specialisteDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/specialiste/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(specialisteCreeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerSpecialisteAvecNomNull() throws Exception {
        SpecialisteCreeDTO specialisteCreeDTO = SpecialisteCreeDTO.builder()
                .id(1L)
                .nom(null)
                .courriel("leimpact@cc.com")
                .motDePasse("1")
                .build();

        when(specialisteService.createSpecialiste(specialisteCreeDTO)).thenThrow(new IllegalArgumentException("Nom NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/specialiste/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(specialisteCreeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerSpecialisteAvecEmailExistant() throws Exception {
        SpecialisteCreeDTO specialisteCreeDTO = SpecialisteCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .motDePasse("1")
                .build();

        when(specialisteService.createSpecialiste(any(SpecialisteCreeDTO.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/specialiste/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(specialisteCreeDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void creerSpecialisteAvecCourrielNull() throws Exception {
        SpecialisteCreeDTO specialisteCreeDTO = SpecialisteCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel(null)
                .motDePasse("1")
                .build();

        when(specialisteService.createSpecialiste(specialisteCreeDTO)).thenThrow(new IllegalArgumentException("Courriel NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/specialiste/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(specialisteCreeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerSpecialisteAvecMotDePasseNull() throws Exception {
        SpecialisteCreeDTO specialisteCreeDTO = SpecialisteCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .motDePasse(null)
                .build();

        when(specialisteService.createSpecialiste(specialisteCreeDTO)).thenThrow(new IllegalArgumentException("Mot de passe NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/specialiste/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(specialisteCreeDTO)))
                .andExpect(status().isBadRequest());
    }
}