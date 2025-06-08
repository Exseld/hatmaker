package com.hat.maker.controller;

import com.hat.maker.service.CampeurService;
import com.hat.maker.service.dto.CampeurDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/campeur")
@RequiredArgsConstructor
public class CampeurController {
    private final CampeurService campeurService;

    @PostMapping
    public ResponseEntity<CampeurDTO> createCampeur(@RequestBody CampeurDTO campeurDTO) {
        CampeurDTO campeurSauvegarde;
        try {
            campeurSauvegarde = campeurService.createCampeur(campeurDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(campeurSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<CampeurDTO>> getAllCampeur() {
        return ResponseEntity.ok(campeurService.getAllCampeur());
    }

    @PutMapping
    public ResponseEntity<CampeurDTO> modifierCampeur(@RequestBody CampeurDTO campeurDTO) {
        CampeurDTO campeurModifie;
        try {
            campeurModifie = campeurService.modifierCampeur(campeurDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(campeurModifie);
    }

    @DeleteMapping
    public ResponseEntity<CampeurDTO> supprimerCampeur(@RequestBody CampeurDTO campeurDTO) {
        CampeurDTO campeurSupprime;
        try {
            campeurSupprime = campeurService.supprimerCampeur(campeurDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(campeurSupprime);
    }
}