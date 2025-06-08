package com.hat.maker.controller;

import com.hat.maker.service.DepartementService;
import com.hat.maker.service.dto.DepartementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/departement")
@RequiredArgsConstructor
public class DepartementController {

    private final DepartementService departementService;

    @PostMapping
    public ResponseEntity<DepartementDTO> createDepartement(@RequestBody DepartementDTO departementDTO) {
        DepartementDTO departementSauvegarde;
        try {
            departementSauvegarde = departementService.createDepartement(departementDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        return ResponseEntity.ok(departementSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<DepartementDTO>> getAllDepartement() {
        return ResponseEntity.ok(departementService.getAllDepartement());
    }

    @PutMapping
    public ResponseEntity<DepartementDTO> modifierDepartement(@RequestBody DepartementDTO departementDTO) {
        DepartementDTO departementModifie;
        try {
            departementModifie = departementService.modifierDepartement(departementDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(departementModifie);
    }

    @DeleteMapping
    public ResponseEntity<DepartementDTO> supprimerDepartement(@RequestBody DepartementDTO departementDTO) {
        DepartementDTO departementSupprime;
        try {
            departementSupprime = departementService.supprimerDepartement(departementDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(departementSupprime);
    }
}