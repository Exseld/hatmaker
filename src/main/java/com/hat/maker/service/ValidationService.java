package com.hat.maker.service;

import com.hat.maker.service.dto.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidationService {
    private enum UtilisateurFields {
        NOM, COURRIEL, MOT_DE_PASSE
    }

    public static void validerActiviteMoniteurFields(ActiviteMoniteurDTO activiteMoniteurDTO) {
        if (activiteMoniteurDTO.getName() == null || activiteMoniteurDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (activiteMoniteurDTO.getSelectedPeriodes() == null || activiteMoniteurDTO.getSelectedPeriodes().isEmpty()) {
            throw new IllegalArgumentException("Les périodes sont obligatoires.");
        }
        if (activiteMoniteurDTO.getDate() == null || activiteMoniteurDTO.getDate().isEmpty()) {
            throw new IllegalArgumentException("La date est obligatoire.");
        }
    }

    public static void validerHoraireJournaliereFields(HoraireJournaliereDTO horaireJournaliereDTO) {
        if (horaireJournaliereDTO.getName() == null || horaireJournaliereDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (horaireJournaliereDTO.getStartDate() == null || horaireJournaliereDTO.getStartDate().isEmpty()) {
            throw new IllegalArgumentException("La date de début est obligatoire.");
        }
        if (horaireJournaliereDTO.getEndDate() == null || horaireJournaliereDTO.getEndDate().isEmpty()) {
            throw new IllegalArgumentException("La date de fin est obligatoire.");
        }
        if (horaireJournaliereDTO.getSelectedType() == null || horaireJournaliereDTO.getSelectedType().isEmpty()) {
            throw new IllegalArgumentException("Le type d'horaire est obligatoire.");
        }
        if (horaireJournaliereDTO.getSelectedPeriodes() == null || horaireJournaliereDTO.getSelectedPeriodes().isEmpty()) {
            throw new IllegalArgumentException("Les périodes sont obligatoires.");
        }
    }

    public static void validerPeriodeFields(PeriodeDTO periodeDTO) {
        if (periodeDTO.getPeriode() == null || periodeDTO.getPeriode().isBlank()) {
            throw new IllegalArgumentException("Le nom de la période ne peut pas être vide");
        }
        if (periodeDTO.getStartTime() == null || periodeDTO.getStartTime().isBlank()) {
            throw new IllegalArgumentException("L'heure de début de la période ne peut pas être vide");
        }
    }

    public static void validerHoraireTypiqueFields(HoraireTypiqueDTO horaireTypiqueDTO) {
        if (horaireTypiqueDTO.getNom() == null || horaireTypiqueDTO.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'horaire typique ne peut pas être vide");
        }
    }

    public static void validerGroupeFields(GroupeDTO groupeDTO) {
        if (groupeDTO.getNom() == null || groupeDTO.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du groupe ne peut pas être vide");
        }
    }

    public static void validerEtatFields(EtatDTO etatDTO) {
        if (etatDTO.getNom() == null || etatDTO.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'état ne peut pas être vide");
        }
    }

    public static void validerTenteFields(TenteDTO tenteDTO) {
        if (tenteDTO.getNomTente() == null || tenteDTO.getNomTente().isBlank()) {
            throw new IllegalArgumentException("Le nom de la tente ne peut pas être vide");
        }
    }

    public static void validerDepartementFields(DepartementDTO departementDTO) {
        if (departementDTO.getNom() == null || departementDTO.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du département ne peut pas être vide");
        }
    }

    public static void validerActiviteFields(ActiviteDTO activiteDTO) {
        if (activiteDTO.getNom() == null || activiteDTO.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'activité ne peut pas être vide");
        }
    }

    public static void validerCampeurFields(CampeurDTO campeurDTO) {
        if (campeurDTO.getNom() == null || campeurDTO.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du campeur ne peut pas être vide");
        }
        if (campeurDTO.getPrenom() == null || campeurDTO.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom du campeur ne peut pas être vide");
        }
        if (campeurDTO.getGenre() == null || campeurDTO.getGenre().isBlank()) {
            throw new IllegalArgumentException("Le genre du campeur ne peut pas être vide");
        }
        if (campeurDTO.getGroupe() == null) {
            throw new IllegalArgumentException("Le groupe du campeur ne peut pas être vide");
        }

        validerGroupeFields(campeurDTO.getGroupe());
    }

    public static void validerResponsableFields(ResponsableCreeDTO responsableCreeDTO) {
        if (responsableCreeDTO.getMotDePasse() == null) {
            throwFieldValidationException(UtilisateurFields.MOT_DE_PASSE);
        }
        validerResponsable(responsableCreeDTO);
    }

    public static void validerSpecialisteFields(SpecialisteCreeDTO specialisteCreeDTO) {
        if (specialisteCreeDTO.getMotDePasse() == null) {
            throwFieldValidationException(UtilisateurFields.MOT_DE_PASSE);
        }
        validerSpecialiste(specialisteCreeDTO);
    }

    public static void validerMoniteurFields(MoniteurCreeDTO moniteurCreeDTO) {
        if (moniteurCreeDTO.getMotDePasse() == null) {
            throwFieldValidationException(UtilisateurFields.MOT_DE_PASSE);
        }
        validerMoniteur(moniteurCreeDTO);
    }

    public static void validerUtilisateurFields(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO.getNom() == null) {
            throwFieldValidationException(UtilisateurFields.NOM);
        }
        if (utilisateurDTO.getCourriel() == null) {
            throwFieldValidationException(UtilisateurFields.COURRIEL);
        }
    }

    private static void validerResponsable(ResponsableDTO responsableDTO) {
        validerUtilisateurFields(responsableDTO);
    }

    private static void validerSpecialiste(SpecialisteDTO specialisteDTO) {
        validerUtilisateurFields(specialisteDTO);
    }


    private static void validerMoniteur(MoniteurDTO moniteurDTO) {
        validerUtilisateurFields(moniteurDTO);
    }

    private static void throwFieldValidationException(UtilisateurFields field) {
        switch (field) {
            case NOM:
                throw new IllegalArgumentException("Nom NULL");
            case COURRIEL:
                throw new IllegalArgumentException("Courriel NULL");
            case MOT_DE_PASSE:
                throw new IllegalArgumentException("Mot de pass NULL");
            default:
                throw new IllegalArgumentException("Champ NULL");
        }
    }

    public static void throwCourrielDejaUtilise() {
        throw new IllegalArgumentException("Courriel déjà utilisé");
    }
}
