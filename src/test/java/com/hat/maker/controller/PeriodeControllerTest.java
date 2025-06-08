package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.PeriodeService;
import com.hat.maker.service.dto.PeriodeDTO;
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
@WebMvcTest(PeriodeController.class)
@ContextConfiguration(classes = {TestConfig.class, PeriodeController.class})
public class PeriodeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private PeriodeService periodeService;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    PeriodeDTO periodeDTO;

    @BeforeEach
    void setup() {
        periodeDTO = PeriodeDTO.builder()
                .id(1L)
                .periode("Activité 1")
                .startTime("10:00")
                .endTime("12:00")
                .build();
    }

    @Test
    void creerPeriodeAvecSucces() throws Exception {
        when(periodeService.createPeriode(periodeDTO)).thenReturn(periodeDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void creerPeriodeAvecPeriodeNull() throws Exception {
        periodeDTO.setPeriode(null);
        when(periodeService.createPeriode(periodeDTO)).thenThrow(new IllegalArgumentException("Le nom de la période ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creerPeriodeAvecStartTimeNull() throws Exception {
        periodeDTO.setStartTime(null);
        when(periodeService.createPeriode(periodeDTO)).thenThrow(new IllegalArgumentException("L'heure de début de la période ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.post("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPeriodeAvecListeVide() throws Exception {
        when(periodeService.getAllPeriode()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/periode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllPeriodeAvecItems() throws Exception {
        List<PeriodeDTO> periodeListe = Arrays.asList(
                PeriodeDTO.builder().id(1L).periode("Activité 1").startTime("10:00").endTime("12:00").build(),
                PeriodeDTO.builder().id(1L).periode("Activité 2").startTime("12:00").endTime("14:00").build()
        );

        when(periodeService.getAllPeriode()).thenReturn(periodeListe);

        mockMvc.perform(MockMvcRequestBuilders.get("/periode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void modifierPeriodeAvecSucces() throws Exception {
        PeriodeDTO periodeModifierDTO = PeriodeDTO.builder()
                .id(1L)
                .periode("Activité 2")
                .startTime("12:00")
                .endTime("14:00")
                .build();
        when(periodeService.modifierPeriode(any(PeriodeDTO.class))).thenReturn(periodeModifierDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(periodeModifierDTO)));
    }

    @Test
    void modifierPeriodeAvecIdInexistant() throws Exception {
        periodeDTO.setId(null);
        when(periodeService.modifierPeriode(any(PeriodeDTO.class))).thenThrow(new IllegalArgumentException("La période n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.put("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifierPeriodeAvecPeriodeNull() throws Exception {
        periodeDTO.setPeriode(null);
        when(periodeService.modifierPeriode(any(PeriodeDTO.class))).thenThrow(new IllegalArgumentException("Le nom de la période ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.put("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifierPeriodeAvecStartTimeNull() throws Exception {
        periodeDTO.setStartTime(null);
        when(periodeService.modifierPeriode(any(PeriodeDTO.class))).thenThrow(new IllegalArgumentException("L'heure de début de la période ne peut pas être vide"));

        mockMvc.perform(MockMvcRequestBuilders.put("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void supprimerPeriodeAvecSucces() throws Exception {
        PeriodeDTO periodeSupprimeDTO = periodeDTO;
        periodeSupprimeDTO.setDeleted(true);
        when(periodeService.supprimerPeriode(any(PeriodeDTO.class))).thenReturn(periodeDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(periodeSupprimeDTO)));
    }

    @Test
    void supprimerPeriodeAvecIdInexistant() throws Exception {
        periodeDTO.setId(null);
        when(periodeService.supprimerPeriode(any(PeriodeDTO.class))).thenThrow(new IllegalArgumentException("La période n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/periode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(periodeDTO)))
                .andExpect(status().isBadRequest());
    }
}