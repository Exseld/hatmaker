package com.hat.maker.controller;

import com.hat.maker.service.MoniteurService;
import com.hat.maker.service.dto.MoniteurCreeDTO;
import com.hat.maker.service.dto.MoniteurDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/moniteur")
@RequiredArgsConstructor
public class MoniteurController {

    private final MoniteurService moniteurService;

    @PostMapping("/inscription")
    public ResponseEntity<MoniteurDTO> createMoniteur(@RequestBody MoniteurCreeDTO moniteurCreeDTO) {
        MoniteurDTO moniteurSauvegarde;
        try {
            moniteurSauvegarde = moniteurService.createMoniteur(moniteurCreeDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        return ResponseEntity.ok(moniteurSauvegarde);
    }
}