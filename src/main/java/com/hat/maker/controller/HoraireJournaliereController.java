package com.hat.maker.controller;

import com.hat.maker.service.HoraireJournaliereService;
import com.hat.maker.service.dto.HoraireJournaliereDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/horaire-journaliere")
@RequiredArgsConstructor
public class HoraireJournaliereController {

    private final HoraireJournaliereService horaireJournaliereService;

    @PostMapping
    public ResponseEntity<HoraireJournaliereDTO> createHoraireJournaliere(@RequestBody HoraireJournaliereDTO horaireJournaliereDTO) {
        HoraireJournaliereDTO horaireJournaliereSauvegarde;
        try {
            horaireJournaliereSauvegarde = horaireJournaliereService.createHoraireJournaliere(horaireJournaliereDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(horaireJournaliereSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<HoraireJournaliereDTO>> getAllHoraireJournaliere() {
        return ResponseEntity.ok(horaireJournaliereService.getAllHoraireJournaliere());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoraireJournaliereDTO> getHoraireJournaliereById(@PathVariable Long id) {
        HoraireJournaliereDTO horaireJournaliereDTO;
        try {
            horaireJournaliereDTO = HoraireJournaliereDTO.toHoraireJournaliereDTO(horaireJournaliereService.getHoraireJournaliereById(id));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(horaireJournaliereDTO);
    }

    @PutMapping
    public ResponseEntity<HoraireJournaliereDTO> modifierHoraireJournaliere(@RequestBody HoraireJournaliereDTO horaireJournaliereDTO) {
        HoraireJournaliereDTO horaireJournaliereModifie;
        try {
            horaireJournaliereModifie = horaireJournaliereService.modifierHoraireJournaliere(horaireJournaliereDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(horaireJournaliereModifie);
    }

    @DeleteMapping
    public ResponseEntity<HoraireJournaliereDTO> supprimerHoraireJournaliere(@RequestBody HoraireJournaliereDTO horaireJournaliereDTO) {
        HoraireJournaliereDTO horaireJournaliereSupprime;
        try {
            horaireJournaliereSupprime = horaireJournaliereService.supprimerHoraireJournaliere(horaireJournaliereDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(horaireJournaliereSupprime);
    }
}