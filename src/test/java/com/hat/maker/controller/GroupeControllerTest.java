package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.GroupeService;
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
@WebMvcTest(GroupeController.class)
@ContextConfiguration(classes = {TestConfig.class, GroupeController.class})
public class GroupeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private GroupeService groupeService;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerGroupeAvecSucces() throws Exception {
        GroupeDTO groupeDTO = GroupeDTO.builder()
                .nom("Junior")
                .build();

        GroupeDTO groupeRetourDTO = GroupeDTO.builder()
                .id(1L)
                .nom("Junior")
                .build();

        when(groupeService.createGroupe(groupeDTO)).thenReturn(groupeRetourDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/groupe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(groupeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerGroupeAvecNomNull() throws Exception {
        GroupeDTO groupeDTO = GroupeDTO.builder()
                .nom(null)
                .build();

        when(groupeService.createGroupe(groupeDTO)).thenThrow(new IllegalArgumentException("Le nom du groupe ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/groupe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(groupeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerGroupeAvecNomExistant() throws Exception {
        GroupeDTO groupeDTO = GroupeDTO.builder()
                .nom("Junior")
                .build();

        when(groupeService.createGroupe(any(GroupeDTO.class))).thenThrow(new IllegalArgumentException("Un groupe avec ce nom existe déjà"));

        mockMvc.perform(MockMvcRequestBuilders.post("/groupe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(groupeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllGroupeAvecListeVide() throws Exception {
        when(groupeService.getAllGroupe()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/groupe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllGroupeAvecItems() throws Exception {
        List<GroupeDTO> groupeList = Arrays.asList(
                GroupeDTO.builder().id(1L).nom("Junior").build(),
                GroupeDTO.builder().id(2L).nom("Inter").build()
        );

        when(groupeService.getAllGroupe()).thenReturn(groupeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/groupe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'nom':'Junior'},{'id':2,'nom':'Inter'}]"));
    }

    @Test
    void modifierGroupeAvecSucces() throws Exception {
        GroupeDTO groupeDTO = GroupeDTO.builder()
                .id(1L)
                .nom("Inter")
                .build();

        GroupeDTO groupeModifieDTO = GroupeDTO.builder()
                .id(1L)
                .nom("Junior")
                .build();

        when(groupeService.modifierGroupe(any(GroupeDTO.class))).thenReturn(groupeModifieDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/groupe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(groupeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(groupeModifieDTO)));
    }

    @Test
    void modifierGroupeAvecIdInexistant() throws Exception {
        GroupeDTO groupeDTO = GroupeDTO.builder()
                .id(1L)
                .nom("Inter")
                .build();

        when(groupeService.modifierGroupe(any(GroupeDTO.class))).thenThrow(new IllegalArgumentException("Le groupe n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.put("/groupe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(groupeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifierGroupeAvecNomNull() throws Exception {
        GroupeDTO groupeDTO = GroupeDTO.builder()
                .id(1L)
                .nom(null)
                .build();

        when(groupeService.modifierGroupe(any(GroupeDTO.class))).thenThrow(new IllegalArgumentException("Le nom du groupe ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.put("/groupe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(groupeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void supprimerGroupeAvecSucces() throws Exception {
        GroupeDTO groupeDTO = GroupeDTO.builder()
                .id(1L)
                .nom("Junior")
                .build();

        GroupeDTO groupeSupprimeDTO = GroupeDTO.builder()
                .id(1L)
                .nom("Junior")
                .deleted(true)
                .build();

        when(groupeService.supprimerGroupe(any(GroupeDTO.class))).thenReturn(groupeSupprimeDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/groupe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(groupeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(groupeSupprimeDTO)));
    }

    @Test
    void supprimerGroupeAvecIdInexistant() throws Exception {
        GroupeDTO groupeDTO = GroupeDTO.builder()
                .id(1L)
                .nom("Junior")
                .build();

        when(groupeService.supprimerGroupe(any(GroupeDTO.class))).thenThrow(new IllegalArgumentException("Le groupe n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/groupe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(groupeDTO)))
                .andExpect(status().isBadRequest());
    }
}