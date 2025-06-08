package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.CampeurService;
import com.hat.maker.service.GroupeService;
import com.hat.maker.service.dto.CampeurDTO;
import com.hat.maker.service.dto.GroupeDTO;
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
@WebMvcTest(CampeurController.class)
@ContextConfiguration(classes = {TestConfig.class, CampeurController.class})
public class CampeurControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private CampeurService campeurService;
    @MockitoBean
    private GroupeService groupeService;
    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerCampeurAvecSucces() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .nom("Doe")
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        CampeurDTO campeurRetourDTO = CampeurDTO.builder()
                .id(1L)
                .nom("Doe")
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        when(campeurService.createCampeur(campeurDTO)).thenReturn(campeurRetourDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerCampeurAvecNomNull() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .nom(null)
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        when(campeurService.createCampeur(campeurDTO)).thenThrow(new IllegalArgumentException("Le nom du campeur ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerCampeurAvecPrenomNull() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .nom("Doe")
                .prenom(null)
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        when(campeurService.createCampeur(campeurDTO)).thenThrow(new IllegalArgumentException("Le prénom du campeur ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerCampeurAvecGenreNull() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .nom("Doe")
                .prenom("John")
                .genre(null)
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        when(campeurService.createCampeur(campeurDTO)).thenThrow(new IllegalArgumentException("Le genre du campeur ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerCampeurAvecGroupeNull() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .nom("Doe")
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(null)
                .build();

        when(campeurService.createCampeur(campeurDTO)).thenThrow(new IllegalArgumentException("Le groupe du campeur ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCampeurAvecListeVide() throws Exception {
        when(campeurService.getAllCampeur()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/campeur")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllCampeurAvecItems() throws Exception {
        List<CampeurDTO> campeurList = Arrays.asList(
                CampeurDTO.builder().id(1L).nom("Doe").prenom("John").genre("Lune").information("Diabetique").groupe(GroupeDTO.builder().id(1L).nom("Inter").build()).build(),
                CampeurDTO.builder().id(2L).nom("Doe").prenom("John").genre("Lune").information("Diabetique").groupe(GroupeDTO.builder().id(1L).nom("Inter").build()).build()
        );

        when(campeurService.getAllCampeur()).thenReturn(campeurList);

        mockMvc.perform(MockMvcRequestBuilders.get("/campeur")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + om.writeValueAsString(campeurList.get(0)) + "," + om.writeValueAsString(campeurList.get(1)) + "]"));
    }

    @Test
    void modifierCampeurAvecSucces() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .id(1L)
                .nom("Doe")
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        CampeurDTO campeurModifieDTO = CampeurDTO.builder()
                .id(1L)
                .nom("Johnson")
                .prenom("Jack")
                .genre("Soleil")
                .information("Allergique")
                .groupe(GroupeDTO.builder().id(1L).nom("Benjamin").build())
                .build();

        when(campeurService.modifierCampeur(any(CampeurDTO.class))).thenReturn(campeurModifieDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(campeurModifieDTO)));
    }

    @Test
    void modifierCampeurAvecIdInexistant() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .id(1L)
                .nom("Doe")
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        when(campeurService.modifierCampeur(any(CampeurDTO.class))).thenThrow(new IllegalArgumentException("Le campeur n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.put("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void supprimerCampeurAvecSucces() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .id(1L)
                .nom("Doe")
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        CampeurDTO campeurSupprimeDTO = CampeurDTO.builder()
                .id(1L)
                .nom("Doe")
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .deleted(true)
                .build();

        when(campeurService.supprimerCampeur(any(CampeurDTO.class))).thenReturn(campeurSupprimeDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(campeurSupprimeDTO)));
    }

    @Test
    void supprimerEtatAvecIdInexistant() throws Exception {
        CampeurDTO campeurDTO = CampeurDTO.builder()
                .id(1L)
                .nom("Doe")
                .prenom("John")
                .genre("Lune")
                .information("Diabetique")
                .groupe(GroupeDTO.builder().id(1L).nom("Inter").build())
                .build();

        when(campeurService.supprimerCampeur(any(CampeurDTO.class))).thenThrow(new IllegalArgumentException("Le campeur n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/campeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(campeurDTO)))
                .andExpect(status().isBadRequest());
    }
}