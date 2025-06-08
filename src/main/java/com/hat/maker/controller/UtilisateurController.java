package com.hat.maker.controller;

import com.hat.maker.service.UtilisateurService;
import com.hat.maker.service.dto.JWTAuthResponse;
import com.hat.maker.service.dto.LoginDTO;
import com.hat.maker.service.dto.MoniteurDTO;
import com.hat.maker.service.dto.UtilisateurDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/connexion")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO login) {
        try {
            String token = utilisateurService.connexionUtilisateur(login);
            final JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);
            return ResponseEntity.accepted()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jwtAuthResponse);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/modifier-utilisateur")
    public ResponseEntity<UtilisateurDTO> modifierUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        try {
            return ResponseEntity.ok(utilisateurService.modifierUtilisateur(utilisateurDTO));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/supprimer-utilisateur")
    public ResponseEntity<UtilisateurDTO> supprimerUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        try {
            return ResponseEntity.ok(utilisateurService.supprimerUtilisateur(utilisateurDTO));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/utilisateurs")
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        try {
            return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/moniteurs")
    public ResponseEntity<List<MoniteurDTO>> getAllMoniteurs() {
        try {
            return ResponseEntity.ok(utilisateurService.getMoniteurs());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
