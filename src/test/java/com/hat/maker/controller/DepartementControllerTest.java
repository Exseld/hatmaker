package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.DepartementService;
import com.hat.maker.service.dto.DepartementDTO;
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

@ContextConfiguration(classes = {TestConfig.class, DepartementController.class})
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DepartementController.class)
public class DepartementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private DepartementService departementService;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerDepartementAvecSucces() throws Exception {
        DepartementDTO departementDTO = DepartementDTO.builder()
                .nom("Programmation")
                .build();

        DepartementDTO departementRetourDTO = DepartementDTO.builder()
                .id(1L)
                .nom("Programmation")
                .build();

        when(departementService.createDepartement(departementDTO)).thenReturn(departementRetourDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(departementDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerDepartementAvecNomNull() throws Exception {
        DepartementDTO departementDTO = DepartementDTO.builder()
                .nom(null)
                .build();

        when(departementService.createDepartement(departementDTO)).thenThrow(new IllegalArgumentException("Le nom du département ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(departementDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerDepartementAvecNomExistant() throws Exception {
        DepartementDTO departementDTO = DepartementDTO.builder()
                .nom("ON")
                .build();

        when(departementService.createDepartement(any(DepartementDTO.class))).thenThrow(new IllegalArgumentException("Un département avec ce nom existe déjà"));

        mockMvc.perform(MockMvcRequestBuilders.post("/departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(departementDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllDepartementAvecListeVide() throws Exception {
        when(departementService.getAllDepartement()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/departement")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllDepartementAvecItems() throws Exception {
        List<DepartementDTO> departementList = Arrays.asList(
                DepartementDTO.builder().id(1L).nom("Programmation").build(),
                DepartementDTO.builder().id(2L).nom("Vie de camp").build()
        );

        when(departementService.getAllDepartement()).thenReturn(departementList);

        mockMvc.perform(MockMvcRequestBuilders.get("/departement")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'nom':'Programmation'},{'id':2,'nom':'Vie de camp'}]"));
    }

    @Test
    void modifierDepartementAvecSucces() throws Exception {
        DepartementDTO departementDTO = DepartementDTO.builder()
                .id(1L)
                .nom("Prog")
                .build();

        DepartementDTO departementModifieDTO = DepartementDTO.builder()
                .id(1L)
                .nom("Programmation")
                .build();

        when(departementService.modifierDepartement(any(DepartementDTO.class))).thenReturn(departementModifieDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(departementDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(departementModifieDTO)));
    }

    @Test
    void modifierDepartementAvecIdInexistant() throws Exception {
        DepartementDTO departementDTO = DepartementDTO.builder()
                .id(1L)
                .nom("OFF")
                .build();

        when(departementService.modifierDepartement(any(DepartementDTO.class))).thenThrow(new IllegalArgumentException("Le département n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.put("/departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(departementDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifierDepartementAvecNomNull() throws Exception {
        DepartementDTO departementDTO = DepartementDTO.builder()
                .id(1L)
                .nom(null)
                .build();

        when(departementService.modifierDepartement(any(DepartementDTO.class))).thenThrow(new IllegalArgumentException("Le nom du département ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.put("/departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(departementDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void supprimerDepartementAvecSucces() throws Exception {
        DepartementDTO departementDTO = DepartementDTO.builder()
                .id(1L)
                .nom("Programmation")
                .build();

        DepartementDTO departementSupprimeDTO = DepartementDTO.builder()
                .id(1L)
                .nom("Programmation")
                .deleted(true)
                .build();

        when(departementService.supprimerDepartement(any(DepartementDTO.class))).thenReturn(departementSupprimeDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(departementDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(departementSupprimeDTO)));
    }

    @Test
    void supprimerDepartementAvecIdInexistant() throws Exception {
        DepartementDTO departementDTO = DepartementDTO.builder()
                .id(1L)
                .nom("ON")
                .build();

        when(departementService.supprimerDepartement(any(DepartementDTO.class))).thenThrow(new IllegalArgumentException("Le département n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(departementDTO)))
                .andExpect(status().isBadRequest());
    }
}