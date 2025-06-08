package com.hat.maker.controller;

import com.hat.maker.service.EtatService;
import com.hat.maker.service.dto.EtatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/etat")
@RequiredArgsConstructor
public class EtatController {

    private final EtatService etatService;

    @PostMapping
    public ResponseEntity<EtatDTO> createEtat(@RequestBody EtatDTO etatDTO) {
        EtatDTO etatSauvegarde;
        try {
            etatSauvegarde = etatService.createEtat(etatDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        return ResponseEntity.ok(etatSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<EtatDTO>> getAllEtat() {
        return ResponseEntity.ok(etatService.getAllEtat());
    }

    @PutMapping
    public ResponseEntity<EtatDTO> modifierEtat(@RequestBody EtatDTO etatDTO) {
        EtatDTO etatModifie;
        try {
            etatModifie = etatService.modifierEtat(etatDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(etatModifie);
    }

    @DeleteMapping
    public ResponseEntity<EtatDTO> supprimerEtat(@RequestBody EtatDTO etatDTO) {
        EtatDTO etatSupprime;
        try {
            etatSupprime = etatService.supprimerEtat(etatDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(etatSupprime);
    }
}