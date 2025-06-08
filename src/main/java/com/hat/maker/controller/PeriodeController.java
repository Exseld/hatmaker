package com.hat.maker.controller;

import com.hat.maker.service.PeriodeService;
import com.hat.maker.service.dto.PeriodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/periode")
@RequiredArgsConstructor
public class PeriodeController {

    private final PeriodeService periodeService;

    @PostMapping
    public ResponseEntity<PeriodeDTO> createPeriode(@RequestBody PeriodeDTO periodeDTO) {
        PeriodeDTO periodeSauvegarde;
        try {
            periodeSauvegarde = periodeService.createPeriode(periodeDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        return ResponseEntity.ok(periodeSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<PeriodeDTO>> getAllPeriode() {
        return ResponseEntity.ok(periodeService.getAllPeriode());
    }

    @PutMapping
    public ResponseEntity<PeriodeDTO> modifierPeriode(@RequestBody PeriodeDTO periodeDTO) {
        PeriodeDTO periodeModifie;
        try {
            periodeModifie = periodeService.modifierPeriode(periodeDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(periodeModifie);
    }

    @DeleteMapping
    public ResponseEntity<PeriodeDTO> supprimerPeriode(@RequestBody PeriodeDTO periodeDTO) {
        PeriodeDTO periodeSupprime;
        try {
            periodeSupprime = periodeService.supprimerPeriode(periodeDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(periodeSupprime);
    }
}