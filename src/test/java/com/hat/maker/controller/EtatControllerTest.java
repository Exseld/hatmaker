package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.EtatService;
import com.hat.maker.service.dto.EtatDTO;
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

@ContextConfiguration(classes = {TestConfig.class, EtatController.class})
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(EtatController.class)
public class EtatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private EtatService etatService;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerEtatAvecSucces() throws Exception {
        EtatDTO etatDTO = EtatDTO.builder()
                .nom("ON")
                .build();

        EtatDTO etatRetourDTO = EtatDTO.builder()
                .id(1L)
                .nom("ON")
                .build();

        when(etatService.createEtat(etatDTO)).thenReturn(etatRetourDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/etat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(etatDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerEtatAvecNomNull() throws Exception {
        EtatDTO etatDTO = EtatDTO.builder()
                .nom(null)
                .build();

        when(etatService.createEtat(etatDTO)).thenThrow(new IllegalArgumentException("Le nom de l'état ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/etat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(etatDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerEtatAvecNomExistant() throws Exception {
        EtatDTO etatDTO = EtatDTO.builder()
                .nom("ON")
                .build();

        when(etatService.createEtat(any(EtatDTO.class))).thenThrow(new IllegalArgumentException("Un état avec ce nom existe déjà"));

        mockMvc.perform(MockMvcRequestBuilders.post("/etat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(etatDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllEtatAvecListeVide() throws Exception {
        when(etatService.getAllEtat()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/etat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllEtatAvecItems() throws Exception {
        List<EtatDTO> etatList = Arrays.asList(
                EtatDTO.builder().id(1L).nom("ON").build(),
                EtatDTO.builder().id(2L).nom("OFF").build()
        );

        when(etatService.getAllEtat()).thenReturn(etatList);

        mockMvc.perform(MockMvcRequestBuilders.get("/etat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'nom':'ON'},{'id':2,'nom':'OFF'}]"));
    }

    @Test
    void modifierEtatAvecSucces() throws Exception {
        EtatDTO etatDTO = EtatDTO.builder()
                .id(1L)
                .nom("OFF")
                .build();

        EtatDTO etatModifieDTO = EtatDTO.builder()
                .id(1L)
                .nom("ON")
                .build();

        when(etatService.modifierEtat(any(EtatDTO.class))).thenReturn(etatModifieDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/etat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(etatDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(etatModifieDTO)));
    }

    @Test
    void modifierEtatAvecIdInexistant() throws Exception {
        EtatDTO etatDTO = EtatDTO.builder()
                .id(1L)
                .nom("OFF")
                .build();

        when(etatService.modifierEtat(any(EtatDTO.class))).thenThrow(new IllegalArgumentException("L'état n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.put("/etat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(etatDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifierEtatAvecNomNull() throws Exception {
        EtatDTO etatDTO = EtatDTO.builder()
                .id(1L)
                .nom(null)
                .build();

        when(etatService.modifierEtat(any(EtatDTO.class))).thenThrow(new IllegalArgumentException("Le nom de l'état ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.put("/etat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(etatDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void supprimerEtatAvecSucces() throws Exception {
        EtatDTO etatDTO = EtatDTO.builder()
                .id(1L)
                .nom("ON")
                .build();

        EtatDTO etatSupprimeDTO = EtatDTO.builder()
                .id(1L)
                .nom("ON")
                .deleted(true)
                .build();

        when(etatService.supprimerEtat(any(EtatDTO.class))).thenReturn(etatSupprimeDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/etat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(etatDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(etatSupprimeDTO)));
    }

    @Test
    void supprimerEtatAvecIdInexistant() throws Exception {
        EtatDTO etatDTO = EtatDTO.builder()
                .id(1L)
                .nom("ON")
                .build();

        when(etatService.supprimerEtat(any(EtatDTO.class))).thenThrow(new IllegalArgumentException("L'état n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/etat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(etatDTO)))
                .andExpect(status().isBadRequest());
    }
}