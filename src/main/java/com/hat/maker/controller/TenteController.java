package com.hat.maker.controller;

import com.hat.maker.service.TenteService;
import com.hat.maker.service.dto.TenteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/tente")
@RequiredArgsConstructor
public class TenteController {

    private final TenteService tenteService;

    @PostMapping
    public ResponseEntity<TenteDTO> createTente(@RequestBody TenteDTO tenteDTO) {
        TenteDTO tenteSauvegarde;
        try {
            tenteSauvegarde = tenteService.createTente(tenteDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        return ResponseEntity.ok(tenteSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<TenteDTO>> getAllTente() {
        return ResponseEntity.ok(tenteService.getAllTente());
    }

    @PutMapping
    public ResponseEntity<TenteDTO> modifierTente(@RequestBody TenteDTO tenteDTO) {
        TenteDTO tenteModifie;
        try {
            tenteModifie = tenteService.modifierTente(tenteDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(tenteModifie);
    }

    @DeleteMapping
    public ResponseEntity<TenteDTO> supprimerTente(@RequestBody TenteDTO tenteDTO) {
        TenteDTO tenteSupprime;
        try {
            tenteSupprime = tenteService.supprimerTente(tenteDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(tenteSupprime);
    }

    @GetMapping("/moniteur/{id}")
    public ResponseEntity<TenteDTO> getTenteByMoniteurId(@PathVariable Long id) {
        TenteDTO tenteDTO;
        try {
            tenteDTO = tenteService.getTenteByMoniteurId(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(tenteDTO);
    }
}