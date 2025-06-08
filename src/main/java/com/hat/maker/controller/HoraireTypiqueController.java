package com.hat.maker.controller;

import com.hat.maker.service.HoraireTypiqueService;
import com.hat.maker.service.dto.HoraireTypiqueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/horaire-typique")
@RequiredArgsConstructor
public class HoraireTypiqueController {

    private final HoraireTypiqueService horaireTypiqueService;

    @PostMapping
    public ResponseEntity<HoraireTypiqueDTO> createHoraireTypique(@RequestBody HoraireTypiqueDTO horaireTypiqueDTO) {
        HoraireTypiqueDTO horaireTypiqueSauvegarde;
        try {
            horaireTypiqueSauvegarde = horaireTypiqueService.createHoraireTypique(horaireTypiqueDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(horaireTypiqueDTO);
        }
        return ResponseEntity.ok(horaireTypiqueSauvegarde);
    }

    @DeleteMapping
    public ResponseEntity<HoraireTypiqueDTO> supprimerHoraireTypique(@RequestBody HoraireTypiqueDTO horaireTypiqueDTO) {
        HoraireTypiqueDTO horaireTypiqueSupprime;
        try {
            horaireTypiqueSupprime = horaireTypiqueService.supprimerHoraireTypique(horaireTypiqueDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(horaireTypiqueDTO);
        }
        return ResponseEntity.ok(horaireTypiqueSupprime);
    }

    @GetMapping
    public ResponseEntity<List<HoraireTypiqueDTO>> getAllHoraireTypique() {
        return ResponseEntity.ok(horaireTypiqueService.getAllHoraireTypique());
    }
}
