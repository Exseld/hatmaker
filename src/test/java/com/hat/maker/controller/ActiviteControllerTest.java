package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.ActiviteService;
import com.hat.maker.service.dto.ActiviteDTO;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ActiviteController.class)
@ContextConfiguration(classes = {TestConfig.class, ActiviteController.class})
public class ActiviteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private ActiviteService activiteService;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerActiviteAvecSucces() throws Exception {
        ActiviteDTO activiteDTO = ActiviteDTO.builder()
                .nom("Tir")
                .build();

        ActiviteDTO activiteRetourDTO = ActiviteDTO.builder()
                .id(1L)
                .nom("Tir")
                .build();

        when(activiteService.createActivite(activiteDTO)).thenReturn(activiteRetourDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/activite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(activiteDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerActiviteAvecNomNull() throws Exception {
        ActiviteDTO activiteDTO = ActiviteDTO.builder()
                .nom(null)
                .build();

        when(activiteService.createActivite(activiteDTO)).thenThrow(new IllegalArgumentException("Le nom de l'activité ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/activite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(activiteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerActiviteAvecNomExistant() throws Exception {
        ActiviteDTO activiteDTO = ActiviteDTO.builder()
                .nom("Tir")
                .build();

        when(activiteService.createActivite(any(ActiviteDTO.class))).thenThrow(new IllegalArgumentException("Une activité avec ce nom existe déjà"));

        mockMvc.perform(MockMvcRequestBuilders.post("/activite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(activiteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllActiviteAvecListeVide() throws Exception {
        when(activiteService.getAllActivite()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/activite")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllActiviteAvecItems() throws Exception {
        List<ActiviteDTO> activiteList = Arrays.asList(
                ActiviteDTO.builder().id(1L).nom("Tir").build(),
                ActiviteDTO.builder().id(2L).nom("Kayak").build()
        );

        when(activiteService.getAllActivite()).thenReturn(activiteList);

        mockMvc.perform(MockMvcRequestBuilders.get("/activite")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'nom':'Tir'},{'id':2,'nom':'Kayak'}]"));
    }

    @Test
    void modifierActiviteAvecSucces() throws Exception {
        ActiviteDTO activiteDTO = ActiviteDTO.builder()
                .id(1L)
                .nom("Kayak")
                .build();

        ActiviteDTO activiteModifieDTO = ActiviteDTO.builder()
                .id(1L)
                .nom("Tir")
                .build();

        when(activiteService.modifierActivite(any(ActiviteDTO.class))).thenReturn(activiteModifieDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/activite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(activiteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(activiteModifieDTO)));
    }

    @Test
    void modifierActiviteAvecIdInexistant() throws Exception {
        ActiviteDTO activiteDTO = ActiviteDTO.builder()
                .id(1L)
                .nom("Kayak")
                .build();

        when(activiteService.modifierActivite(any(ActiviteDTO.class))).thenThrow(new IllegalArgumentException("L'activité n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.put("/activite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(activiteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifierActiviteAvecNomNull() throws Exception {
        ActiviteDTO activiteDTO = ActiviteDTO.builder()
                .id(1L)
                .nom(null)
                .build();

        when(activiteService.modifierActivite(any(ActiviteDTO.class))).thenThrow(new IllegalArgumentException("Le nom de l'activité ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.put("/activite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(activiteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void supprimerActiviteAvecSucces() throws Exception {
        ActiviteDTO activiteDTO = ActiviteDTO.builder()
                .id(1L)
                .nom("Tir")
                .build();

        ActiviteDTO activiteSupprimeDTO = ActiviteDTO.builder()
                .id(1L)
                .nom("Tir")
                .deleted(true)
                .build();

        when(activiteService.supprimerActivite(any(ActiviteDTO.class))).thenReturn(activiteSupprimeDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/activite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(activiteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(activiteSupprimeDTO)));
    }

    @Test
    void supprimerActiviteAvecIdInexistant() throws Exception {
        ActiviteDTO activiteDTO = ActiviteDTO.builder()
                .id(1L)
                .nom("ON")
                .build();

        when(activiteService.supprimerActivite(any(ActiviteDTO.class))).thenThrow(new IllegalArgumentException("L'activité n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/activite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(activiteDTO)))
                .andExpect(status().isBadRequest());
    }
}