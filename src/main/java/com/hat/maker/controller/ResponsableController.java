package com.hat.maker.controller;

import com.hat.maker.service.ResponsableService;
import com.hat.maker.service.dto.ResponsableCreeDTO;
import com.hat.maker.service.dto.ResponsableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/responsable")
@RequiredArgsConstructor
public class ResponsableController {

    private final ResponsableService responsableService;

    @PostMapping("/inscription")
    public ResponseEntity<ResponsableDTO> createResponsable(@RequestBody ResponsableCreeDTO responsableCreeDTO) {
        ResponsableDTO responsableSauvegarde;
        try {
            responsableSauvegarde = responsableService.createResponsable(responsableCreeDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        return ResponseEntity.ok(responsableSauvegarde);
    }
}