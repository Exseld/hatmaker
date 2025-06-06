package com.hat.maker.service;

import com.hat.maker.model.Campeur;
import com.hat.maker.repository.CampeurRepository;
import com.hat.maker.service.dto.CampeurDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampeurService {
    private final CampeurRepository campeurRepository;
    private final GroupeService groupeService;

    @Transactional
    public CampeurDTO createCampeur(CampeurDTO campeurDTO) {
        ValidationService.validerCampeurFields(campeurDTO);

        Campeur campeur = Campeur.builder()
                .nom(campeurDTO.getNom())
                .prenom(campeurDTO.getPrenom())
                .genre(campeurDTO.getGenre())
                .information(campeurDTO.getInformation())
                .deleted(false)
                .groupe(groupeService.getGroupeById(campeurDTO.getGroupe().getId()))
                .build();
        Campeur campeurRetour = campeurRepository.save(campeur);
        return CampeurDTO.toCampeurDTO(campeurRetour);
    }

    @Transactional
    public CampeurDTO modifierCampeur(CampeurDTO campeurDTO) {
        ValidationService.validerCampeurFields(campeurDTO);

        Campeur campeur = getCampeurById(campeurDTO.getId());
        campeur.setNom(campeurDTO.getNom());
        campeur.setPrenom(campeurDTO.getPrenom());
        campeur.setGenre(campeurDTO.getGenre());
        campeur.setInformation(campeurDTO.getInformation());
        campeur.setGroupe(groupeService.getGroupeById(campeurDTO.getGroupe().getId()));
        return CampeurDTO.toCampeurDTO(campeurRepository.save(campeur));
    }

    public CampeurDTO supprimerCampeur(CampeurDTO campeurDTO) {
        Campeur campeur = getCampeurById(campeurDTO.getId());
        campeur.setDeleted(true);
        return CampeurDTO.toCampeurDTO(campeurRepository.save(campeur));
    }

    public List<CampeurDTO> getAllCampeur() {
        return campeurRepository.findAll().stream()
                .map(CampeurDTO::toCampeurDTO)
                .toList();
    }

    private Campeur getCampeurById(Long id) {
        return campeurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Le campeur n'existe pas"));
    }
}
