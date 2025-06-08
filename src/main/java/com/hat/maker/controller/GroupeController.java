package com.hat.maker.controller;

import com.hat.maker.service.GroupeService;
import com.hat.maker.service.dto.GroupeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/groupe")
@RequiredArgsConstructor
public class GroupeController {

    private final GroupeService groupeService;

    @PostMapping
    public ResponseEntity<GroupeDTO> createGroupe(@RequestBody GroupeDTO groupeDTO) {
        GroupeDTO groupeSauvegarde;
        try {
            groupeSauvegarde = groupeService.createGroupe(groupeDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(groupeSauvegarde);
    }

    @GetMapping
    public ResponseEntity<List<GroupeDTO>> getAllGroupe() {
        return ResponseEntity.ok(groupeService.getAllGroupe());
    }

    @PutMapping
    public ResponseEntity<GroupeDTO> modifierGroupe(@RequestBody GroupeDTO groupeDTO) {
        GroupeDTO groupeModifie;
        try {
            groupeModifie = groupeService.modifierGroupe(groupeDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(groupeModifie);
    }

    @DeleteMapping
    public ResponseEntity<GroupeDTO> supprimerGroupe(@RequestBody GroupeDTO groupeDTO) {
        GroupeDTO groupeSupprime;
        try {
            groupeSupprime = groupeService.supprimerGroupe(groupeDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(groupeSupprime);
    }
}