package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.repository.ResponsableRepository;
import com.hat.maker.service.ResponsableService;
import com.hat.maker.service.dto.ResponsableCreeDTO;
import com.hat.maker.service.dto.ResponsableDTO;
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
@WebMvcTest(ResponsableController.class)
@ContextConfiguration(classes = {TestConfig.class, ResponsableController.class})
public class ResponsableControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private ResponsableService responsableService;

    @MockitoBean
    private ResponsableRepository responsableRepository;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerResponsableAvecSucces() throws Exception {
        ResponsableCreeDTO responsableCreeDTO = ResponsableCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimapct@cc.com")
                .motDePasse("1")
                .build();

        ResponsableDTO responsableDTO = ResponsableDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .build();

        when(responsableService.createResponsable(responsableCreeDTO)).thenReturn(responsableDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/responsable/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(responsableCreeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerResponsableAvecNomNull() throws Exception {
        ResponsableCreeDTO responsableCreeDTO = ResponsableCreeDTO.builder()
                .id(1L)
                .nom(null)
                .courriel("leimpact@cc.com")
                .motDePasse("1")
                .build();

        when(responsableService.createResponsable(responsableCreeDTO)).thenThrow(new IllegalArgumentException("Nom NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/responsable/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(responsableCreeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerResponsableAvecEmailExistant() throws Exception {
        ResponsableCreeDTO responsableCreeDTO = ResponsableCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .motDePasse("1")
                .build();

        when(responsableService.createResponsable(any(ResponsableCreeDTO.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/responsable/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(responsableCreeDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void creerResponsableAvecCourrielNull() throws Exception {
        ResponsableCreeDTO responsableCreeDTO = ResponsableCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel(null)
                .motDePasse("1")
                .build();

        when(responsableService.createResponsable(responsableCreeDTO)).thenThrow(new IllegalArgumentException("Courriel NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/responsable/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(responsableCreeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerResponsableAvecMotDePasseNull() throws Exception {
        ResponsableCreeDTO responsableCreeDTO = ResponsableCreeDTO.builder()
                .id(1L)
                .nom("Le Impact")
                .courriel("leimpact@cc.com")
                .motDePasse(null)
                .build();

        when(responsableService.createResponsable(responsableCreeDTO)).thenThrow(new IllegalArgumentException("Mot de passe NULL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/responsable/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(responsableCreeDTO)))
                .andExpect(status().isBadRequest());
    }
}