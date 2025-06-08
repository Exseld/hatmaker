package com.hat.maker.controller;

import com.hat.maker.service.ActiviteMoniteurService;
import com.hat.maker.service.dto.ActiviteMoniteurDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/activite-moniteur")
@RequiredArgsConstructor
public class ActiviteMoniteurController {

    private final ActiviteMoniteurService activiteMoniteurService;

    @PostMapping
    public ResponseEntity<ActiviteMoniteurDTO> createActiviteMoniteur(@RequestBody ActiviteMoniteurDTO activiteMoniteurDTO) {
        ActiviteMoniteurDTO activiteMoniteurSauvegarde;
        try {
            activiteMoniteurSauvegarde = activiteMoniteurService.createActiviteMoniteur(activiteMoniteurDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(activiteMoniteurSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<ActiviteMoniteurDTO>> getAllActiviteMoniteurs() {
        return ResponseEntity.ok(activiteMoniteurService.getAllActiviteMoniteur());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiviteMoniteurDTO> getActiviteMoniteurById(@PathVariable Long id) {
        ActiviteMoniteurDTO activiteMoniteurDTO;
        try {
            activiteMoniteurDTO = ActiviteMoniteurDTO.toActiviteMoniteurDTO(activiteMoniteurService.getActiviteMoniteurById(id));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(activiteMoniteurDTO);
    }

    @PutMapping
    public ResponseEntity<ActiviteMoniteurDTO> modifierActiviteMoniteur(@RequestBody ActiviteMoniteurDTO activiteMoniteurDTO) {
        ActiviteMoniteurDTO activiteMoniteurModifie;
        try {
            activiteMoniteurModifie = activiteMoniteurService.modifierActiviteMoniteur(activiteMoniteurDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(activiteMoniteurModifie);
    }

    @DeleteMapping
    public ResponseEntity<ActiviteMoniteurDTO> supprimerActiviteMoniteur(@RequestBody ActiviteMoniteurDTO activiteMoniteurDTO) {
        ActiviteMoniteurDTO activiteMoniteurSupprime;
        try {
            activiteMoniteurSupprime = activiteMoniteurService.supprimerActiviteMoniteur(activiteMoniteurDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(activiteMoniteurSupprime);
    }

    @PutMapping("/assignement")
    public ResponseEntity<ActiviteMoniteurDTO> sauvegarderAssignement(@RequestBody ActiviteMoniteurDTO activiteMoniteurDTO) {
        ActiviteMoniteurDTO activiteMoniteurModifie;
        try {
            activiteMoniteurModifie = activiteMoniteurService.sauvegarderAssignement(activiteMoniteurDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(activiteMoniteurModifie);
    }
}