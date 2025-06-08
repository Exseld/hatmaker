package com.hat.maker.controller;

import com.hat.maker.TestConfig;
import com.hat.maker.service.HoraireJournaliereService;
import com.hat.maker.service.dto.HoraireJournaliereDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestConfig.class, HoraireJournaliereController.class})
class HoraireJournaliereControllerTest {

    @InjectMocks
    private HoraireJournaliereController horaireJournaliereController;

    @Mock
    private HoraireJournaliereService horaireJournaliereService;

    private HoraireJournaliereDTO horaireJournaliereDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        horaireJournaliereDTO = HoraireJournaliereDTO.builder()
                .id(1L)
                .name("Test Horaire")
                .startDate("2023-01-01")
                .endDate("2023-01-02")
                .build();
    }

    @Test
    void testCreateHoraireJournaliere() {
        when(horaireJournaliereService.createHoraireJournaliere(any(HoraireJournaliereDTO.class)))
                .thenReturn(horaireJournaliereDTO);

        ResponseEntity<HoraireJournaliereDTO> response = horaireJournaliereController.createHoraireJournaliere(horaireJournaliereDTO);

        assertNotNull(response);
        assertEquals(horaireJournaliereDTO.getName(), Objects.requireNonNull(response.getBody()).getName());
        verify(horaireJournaliereService, times(1)).createHoraireJournaliere(any(HoraireJournaliereDTO.class));
    }

    @Test
    void testGetAllHoraireJournaliere() {
        when(horaireJournaliereService.getAllHoraireJournaliere()).thenReturn(List.of(horaireJournaliereDTO));

        ResponseEntity<List<HoraireJournaliereDTO>> response = horaireJournaliereController.getAllHoraireJournaliere();

        assertNotNull(response);
        assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
        verify(horaireJournaliereService, times(1)).getAllHoraireJournaliere();
    }

    @Test
    void testModifierHoraireJournaliere() {
        when(horaireJournaliereService.modifierHoraireJournaliere(any(HoraireJournaliereDTO.class)))
                .thenReturn(horaireJournaliereDTO);

        ResponseEntity<HoraireJournaliereDTO> response = horaireJournaliereController.modifierHoraireJournaliere(horaireJournaliereDTO);

        assertNotNull(response);
        assertEquals(horaireJournaliereDTO.getName(), Objects.requireNonNull(response.getBody()).getName());
        verify(horaireJournaliereService, times(1)).modifierHoraireJournaliere(any(HoraireJournaliereDTO.class));
    }

    @Test
    void testSupprimerHoraireJournaliere() {
        when(horaireJournaliereService.supprimerHoraireJournaliere(any(HoraireJournaliereDTO.class)))
                .thenReturn(horaireJournaliereDTO);

        ResponseEntity<HoraireJournaliereDTO> response = horaireJournaliereController.supprimerHoraireJournaliere(horaireJournaliereDTO);

        assertNotNull(response);
        assertEquals(horaireJournaliereDTO.getName(), Objects.requireNonNull(response.getBody()).getName());
        verify(horaireJournaliereService, times(1)).supprimerHoraireJournaliere(any(HoraireJournaliereDTO.class));
    }
}