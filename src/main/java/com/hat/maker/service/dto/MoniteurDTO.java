package com.hat.maker.service.dto;

import com.hat.maker.model.Moniteur;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MoniteurDTO extends UtilisateurDTO {

    public static MoniteurDTO toMoniteurDTO(Moniteur moniteur) {
        if(moniteur == null) {throw new IllegalArgumentException("Moniteur est null !");}
        return MoniteurDTO.builder()
                .id(moniteur.getId())
                .nom(moniteur.getNom())
                .courriel(moniteur.getCourriel())
                .role(moniteur.getCredentials().getRole())
                .deleted(moniteur.isDeleted())
                .departement(moniteur.getDepartement() == null
                        ? null : DepartementDTO.toDepartementDTO(moniteur.getDepartement()))
                .build();
    }

    public static List<MoniteurDTO> toMoniteurDTO(List<Moniteur> moniteurs) {
        return moniteurs.stream()
                .map(MoniteurDTO::toMoniteurDTO)
                .toList();
    }
}
