package com.hat.maker.controller;

import com.hat.maker.MakerApplication;
import com.hat.maker.service.ActiviteService;
import com.hat.maker.service.dto.ActiviteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/activite")
@RequiredArgsConstructor
public class ActiviteController {

    private final ActiviteService activiteService;

    @PostMapping
    public ResponseEntity<ActiviteDTO> createActivite(@RequestBody ActiviteDTO activiteDTO) {
        ActiviteDTO activiteSauvegarde;
        try {
            activiteSauvegarde = activiteService.createActivite(activiteDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        return ResponseEntity.ok(activiteSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<ActiviteDTO>> getAllActivite() {
        return ResponseEntity.ok(activiteService.getAllActivite());
    }

    @PutMapping
    public ResponseEntity<ActiviteDTO> modifierActivite(@RequestBody ActiviteDTO activiteDTO) {
        ActiviteDTO activiteModifie;
        try {
            activiteModifie = activiteService.modifierActivite(activiteDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(activiteModifie);
    }

    @DeleteMapping
    public ResponseEntity<ActiviteDTO> supprimerActivite(@RequestBody ActiviteDTO activiteDTO) {
        ActiviteDTO activiteSupprime;
        try {
            activiteSupprime = activiteService.supprimerActivite(activiteDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(activiteSupprime);
    }
}