package com.hat.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.maker.TestConfig;
import com.hat.maker.service.HoraireTypiqueService;
import com.hat.maker.service.dto.HoraireTypiqueDTO;
import com.hat.maker.service.dto.TimeSlotDTO;
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
@WebMvcTest(HoraireTypiqueController.class)
@ContextConfiguration(classes = {TestConfig.class, HoraireTypiqueController.class})
public class HoraireTypiqueControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private HoraireTypiqueService horaireTypiqueService;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void creerHoraireTypiqueAvecSucces() throws Exception {
        TimeSlotDTO timeSlotDTO = TimeSlotDTO.builder()
                .startTime("08:00")
                .endTime("10:00")
                .periode("AM")
                .build();

        HoraireTypiqueDTO horaireTypiqueDTO = HoraireTypiqueDTO.builder()
                .nom("Horaire 1")
                .timeSlots(List.of(timeSlotDTO))
                .build();

        HoraireTypiqueDTO horaireTypiqueRetour = HoraireTypiqueDTO.builder()
                .id(1L)
                .nom("Horaire 1")
                .timeSlots(List.of(timeSlotDTO))
                .build();

        when(horaireTypiqueService.createHoraireTypique(horaireTypiqueDTO)).thenReturn(horaireTypiqueRetour);

        mockMvc.perform(MockMvcRequestBuilders.post("/horaire-typique")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(horaireTypiqueRetour)))
                .andExpect(status().isOk());
    }

    @Test
    void supprimerHoraireTypiqueAvecSucces() throws Exception {
        TimeSlotDTO timeSlotDTO = TimeSlotDTO.builder()
                .startTime("08:00")
                .endTime("10:00")
                .periode("AM")
                .build();

        HoraireTypiqueDTO horaireTypiqueDTO = HoraireTypiqueDTO.builder()
                .nom("Horaire 1")
                .timeSlots(List.of(timeSlotDTO))
                .build();

        HoraireTypiqueDTO horaireTypiqueRetour = HoraireTypiqueDTO.builder()
                .id(1L)
                .nom("Horaire 1")
                .timeSlots(List.of(timeSlotDTO))
                .deleted(true)
                .build();

        when(horaireTypiqueService.supprimerHoraireTypique(horaireTypiqueDTO)).thenReturn(horaireTypiqueRetour);

        mockMvc.perform(MockMvcRequestBuilders.delete("/horaire-typique")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(horaireTypiqueRetour)))
                .andExpect(status().isOk());
    }

    @Test
    void supprimerHoraireTypiqueAvecIdInexistant() throws Exception {
        TimeSlotDTO timeSlotDTO = TimeSlotDTO.builder()
                .startTime("08:00")
                .endTime("10:00")
                .periode("AM")
                .build();

        HoraireTypiqueDTO horaireTypiqueDTO = HoraireTypiqueDTO.builder()
                .id(1L)
                .nom("Horaire 1")
                .timeSlots(List.of(timeSlotDTO))
                .deleted(true)
                .build();

        when(horaireTypiqueService.supprimerHoraireTypique(any(HoraireTypiqueDTO.class))).thenThrow(new IllegalArgumentException("L'horaire typique n'existe pas"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/horaire-typique")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(horaireTypiqueDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllHoraireTypiqueAvecListeVide() throws Exception {
        when(horaireTypiqueService.getAllHoraireTypique()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/horaire-typique")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllHoraireTypiqueAvecItems() throws Exception {
        TimeSlotDTO timeSlotDTO = TimeSlotDTO.builder()
                .startTime("08:00")
                .endTime("10:00")
                .periode("AM")
                .build();

        TimeSlotDTO timeSlotDTO2 = TimeSlotDTO.builder()
                .startTime("12:00")
                .endTime("14:00")
                .periode("PM")
                .build();

        List<HoraireTypiqueDTO> horaireTypiqueList = Arrays.asList(
                HoraireTypiqueDTO.builder().id(1L).nom("Horaire 1").timeSlots(List.of(timeSlotDTO)).build(),
                HoraireTypiqueDTO.builder().id(2L).nom("Horaire 1").timeSlots(List.of(timeSlotDTO2)).build()

        );

        when(horaireTypiqueService.getAllHoraireTypique()).thenReturn(horaireTypiqueList);

        mockMvc.perform(MockMvcRequestBuilders.get("/horaire-typique")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}