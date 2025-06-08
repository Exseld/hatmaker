package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.TenteService;
import com.hat.maker.service.dto.*;
import org.junit.jupiter.api.BeforeEach;
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
@WebMvcTest(TenteController.class)
@ContextConfiguration(classes = {TestConfig.class, TenteController.class})
public class TenteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private TenteService tenteService;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    TenteDTO tenteDTO;

    @BeforeEach
    void setup() {
        List<MoniteurDTO> moniteursDTO = List.of(
                MoniteurDTO.builder()
                        .id(1L)
                        .nom("Moniteur1")
                        .departement(DepartementDTO.builder()
                                .id(1L)
                                .nom("Departement1")
                                .build())
                        .build(),
                MoniteurDTO.builder()
                        .id(2L)
                        .nom("Moniteur2")
                        .departement(DepartementDTO.builder()
                                .id(2L)
                                .nom("Departement2")
                                .build())
                        .build()
        );

        List<CampeurDTO> campeursDTO = List.of(
                CampeurDTO.builder()
                        .id(1L)
                        .nom("Campeur1")
                        .prenom("Prenom1")
                        .genre("M")
                        .information("Information1")
                        .groupe(GroupeDTO.builder()
                                .id(1L)
                                .nom("Groupe1")
                                .build())
                        .build(),
                CampeurDTO.builder()
                        .id(2L)
                        .nom("Campeur2")
                        .prenom("Prenom2")
                        .genre("F")
                        .information("Information2")
                        .groupe(GroupeDTO.builder()
                                .id(2L)
                                .nom("Groupe2")
                                .build())
                        .build()
        );

        tenteDTO = TenteDTO.builder()
                .id(1L)
                .nomTente("1")
                .moniteurs(moniteursDTO)
                .campeurs(campeursDTO)
                .build();
    }

    @Test
    void creerTenteAvecSucces() throws Exception {
        tenteDTO.setId(null);
        TenteDTO tenteRetourDTO = tenteDTO;
        tenteRetourDTO.setId(1L);

        when(tenteService.createTente(tenteDTO)).thenReturn(tenteRetourDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/tente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(tenteDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerTenteAvecNomNull() throws Exception {
        tenteDTO.setNomTente(null);
        when(tenteService.createTente(tenteDTO)).thenThrow(new IllegalArgumentException("Le nom de la tente ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/tente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(tenteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerTenteAvecNomExistant() throws Exception {
        when(tenteService.createTente(any(TenteDTO.class))).thenThrow(new IllegalArgumentException("Une tente avec ce nom existe déjà"));

        mockMvc.perform(MockMvcRequestBuilders.post("/tente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(tenteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllTenteAvecListeVide() throws Exception {
        when(tenteService.getAllTente()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/tente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllTenteAvecItems() throws Exception {
        TenteDTO tenteDTO1 = tenteDTO;
        TenteDTO tenteDTO2 = tenteDTO;
        tenteDTO2.setId(2L);

        List<TenteDTO> tenteList = Arrays.asList(
                tenteDTO1,
                tenteDTO2
        );
        when(tenteService.getAllTente()).thenReturn(tenteList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void modifierTenteAvecSucces() throws Exception {
        TenteDTO tenteModifieDTO = tenteDTO;
        tenteModifieDTO.setNomTente("2");

        when(tenteService.modifierTente(any(TenteDTO.class))).thenReturn(tenteModifieDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/tente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(tenteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(tenteModifieDTO)));
    }

    @Test
    void modifierTenteAvecIdInexistant() throws Exception {
        tenteDTO.setId(null);

        when(tenteService.modifierTente(any(TenteDTO.class))).thenThrow(new IllegalArgumentException("La tente n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.put("/tente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(tenteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifierTenteAvecNomNull() throws Exception {
        tenteDTO.setNomTente(null);

        when(tenteService.modifierTente(any(TenteDTO.class))).thenThrow(new IllegalArgumentException("Le nom de la tente ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.put("/tente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(tenteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void supprimerTenteAvecSucces() throws Exception {
        TenteDTO tenteSupprimeDTO = tenteDTO;
        tenteSupprimeDTO.setDeleted(true);

        when(tenteService.supprimerTente(any(TenteDTO.class))).thenReturn(tenteSupprimeDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(tenteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(tenteSupprimeDTO)));
    }

    @Test
    void supprimerTenteAvecIdInexistant() throws Exception {
        tenteDTO.setId(null);
        tenteDTO.setDeleted(true);

        when(tenteService.supprimerTente(any(TenteDTO.class))).thenThrow(new IllegalArgumentException("La tente n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/tente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(tenteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTenteByMoniteurIdSucces() throws Exception {
        when(tenteService.getTenteByMoniteurId(1L)).thenReturn(tenteDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/tente/moniteur/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(tenteDTO)));
    }

    @Test
    void getTenteByMoniteurIdAvecIdInexistant() throws Exception {
        when(tenteService.getTenteByMoniteurId(3L)).thenThrow(new IllegalArgumentException("Le moniteur n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.get("/tente/moniteur/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTenteByMoniteurIdAvecTenteInexistante() throws Exception {
        when(tenteService.getTenteByMoniteurId(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/tente/moniteur/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}