package com.hat.maker;

import com.hat.maker.service.*;
import com.hat.maker.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class MakerApplication implements CommandLineRunner {
    private final ResponsableService responsableService;
    private final DepartementService departementService;
    private final MoniteurService moniteurService;
    private final CampeurService campeurService;
    private final GroupeService groupeService;
    private final HoraireTypiqueService horaireTypiqueService;
    private final EtatService etatService;
    private final ActiviteService activiteService;

    @Value("${cors.origin}")
    public String crossOrigin;

     @Bean
     @Profile("!test")
     public WebMvcConfigurer corsConfiguration(){
         return new WebMvcConfigurer() {
             @Override
             public void addCorsMappings(CorsRegistry registry) {
                 registry.addMapping("/**")
                         .allowedOrigins(crossOrigin)
                         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                         .allowedHeaders("*")
                         .allowCredentials(true)
                         .maxAge(3600);
             }
         };
     }
    public static void main(String[] args) {
        SpringApplication.run(MakerApplication.class, args);
    }

    public void run(String... args) {
        System.out.println("Allowed origin: " + crossOrigin);
//        List<DepartementDTO> departementDTOs = Arrays.asList(
//                DepartementDTO.builder().id(1L).nom("Animateur").build(),
//                DepartementDTO.builder().id(2L).nom("Gestion").build(),
//                DepartementDTO.builder().id(3L).nom("Direction").build(),
//                DepartementDTO.builder().id(4L).nom("Moniteur").build(),
//                DepartementDTO.builder().id(5L).nom("Cuisine").build()
//        );
//        departementDTOs.forEach(departementService::createDepartement);
//
//        List<GroupeDTO> groupeDTOs = Arrays.asList(
//                GroupeDTO.builder().id(1L).nom("Benjamin").build(),
//                GroupeDTO.builder().id(2L).nom("Junior").build(),
//                GroupeDTO.builder().id(3L).nom("Inter").build(),
//                GroupeDTO.builder().id(4L).nom("Senior").build()
//        );
//        groupeDTOs.forEach(groupeService::createGroupe);
//
//        List<EtatDTO> etatDTOs = Arrays.asList(
//                EtatDTO.builder().id(1L).nom("ON").build(),
//                EtatDTO.builder().id(2L).nom("OFF").build(),
//                EtatDTO.builder().id(3L).nom("PREP").build(),
//                EtatDTO.builder().id(3L).nom("24h Off").build(),
//                EtatDTO.builder().id(3L).nom("SURVEIL").build()
//        );
//        etatDTOs.forEach(etatService::createEtat);
//
//        CampeurDTO campeurDTO = CampeurDTO.builder()
//                .nom("Paige")
//                .prenom("Emanuel")
//                .genre("Lune")
//                .groupe(groupeDTOs.getFirst())
//                .information("Pipi au lit")
//                .build();
//        campeurService.createCampeur(campeurDTO);
//        CampeurDTO campeurDTO2 = CampeurDTO.builder()
//                .nom("Perles")
//                .prenom("Lili")
//                .genre("Soleil")
//                .groupe(groupeDTOs.getFirst())
//                .information("")
//                .build();
//        campeurService.createCampeur(campeurDTO2);
//        CampeurDTO campeurDTO3 = CampeurDTO.builder()
//                .nom("Guimond")
//                .prenom("Antoine")
//                .genre("Lune")
//                .groupe(groupeDTOs.get(1))
//                .information("Trouble d'opposition")
//                .build();
//        campeurService.createCampeur(campeurDTO3);
//        CampeurDTO campeurDTO4 = CampeurDTO.builder()
//                .nom("Adriane-Cassidy")
//                .prenom("Lou")
//                .genre("Soleil")
//                .groupe(groupeDTOs.get(1))
//                .information("Aime chanter")
//                .build();
//        campeurService.createCampeur(campeurDTO4);
//        CampeurDTO campeurDTO5 = CampeurDTO.builder()
//                .nom("Bouchard")
//                .prenom("Gabriel")
//                .genre("Lune")
//                .groupe(groupeDTOs.get(2))
//                .information("Apporte toujours sa guitare")
//                .build();
//        campeurService.createCampeur(campeurDTO5);
//        CampeurDTO campeurDTO6 = CampeurDTO.builder()
//                .nom("Roy")
//                .prenom("Ariane")
//                .genre("Soleil")
//                .groupe(groupeDTOs.get(2))
//                .information("Enfant reine")
//                .build();
//        campeurService.createCampeur(campeurDTO6);
//        CampeurDTO campeurDTO7 = CampeurDTO.builder()
//                .nom("Bridges")
//                .prenom("Cassidy")
//                .genre("Soleil")
//                .groupe(groupeDTOs.getLast())
//                .information("Campeur exemplaire")
//                .build();
//        campeurService.createCampeur(campeurDTO7);
//        CampeurDTO campeurDTO8 = CampeurDTO.builder()
//                .nom("Guy")
//                .prenom("Guy")
//                .genre("Lune")
//                .groupe(groupeDTOs.getLast())
//                .information("Petit coquin")
//                .build();
//        campeurService.createCampeur(campeurDTO8);
//
//
//        MoniteurCreeDTO moniteurDTO = MoniteurCreeDTO.builder()
//                .nom("Indigo")
//                .courriel("indigo@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.getFirst())
//                .build();
//        moniteurService.createMoniteur(moniteurDTO);
//        MoniteurCreeDTO moniteurDTO2 = MoniteurCreeDTO.builder()
//                .nom("Smash")
//                .courriel("smash@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.getFirst())
//                .build();
//        moniteurService.createMoniteur(moniteurDTO2);
//        MoniteurCreeDTO moniteurDTO4 = MoniteurCreeDTO.builder()
//                .nom("Rocket")
//                .courriel("rocket@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.get(3))
//                .build();
//        moniteurService.createMoniteur(moniteurDTO4);
//        MoniteurCreeDTO moniteurDTO5 = MoniteurCreeDTO.builder()
//                .nom("Libellule")
//                .courriel("libellule@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.get(3))
//                .build();
//        moniteurService.createMoniteur(moniteurDTO5);
//        MoniteurCreeDTO moniteurDTO6 = MoniteurCreeDTO.builder()
//                .nom("Caféine")
//                .courriel("cafeine@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.getLast())
//                .build();
//        moniteurService.createMoniteur(moniteurDTO6);
//        MoniteurCreeDTO moniteurDTO7 = MoniteurCreeDTO.builder()
//                .nom("Thé des bois")
//                .courriel("thedesbois@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.getLast())
//                .build();
//        moniteurService.createMoniteur(moniteurDTO7);
//
//
//        ResponsableCreeDTO responsableDTO = ResponsableCreeDTO.builder()
//                .nom("Le Impact")
//                .courriel("leimpact@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.get(1))
//                .build();
//        responsableService.createResponsable(responsableDTO);
//        ResponsableCreeDTO responsableDTO2 = ResponsableCreeDTO.builder()
//                .nom("Nemo")
//                .courriel("nemo@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.get(1))
//                .build();
//        responsableService.createResponsable(responsableDTO2);
//        ResponsableCreeDTO responsableDTO3 = ResponsableCreeDTO.builder()
//                .nom("Sooby-Doo")
//                .courriel("scoobydoo@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.get(2))
//                .build();
//        responsableService.createResponsable(responsableDTO3);
//        ResponsableCreeDTO responsableDTO4 = ResponsableCreeDTO.builder()
//                .nom("Mango")
//                .courriel("mango@cc.com")
//                .motDePasse("1")
//                .departement(departementDTOs.get(2))
//                .build();
//        responsableService.createResponsable(responsableDTO4);
//
//        List<TimeSlotDTO> timeSlotDTOs = Arrays.asList(
//                TimeSlotDTO.builder().startTime("7:15").periode("Réveil").build(),
//                TimeSlotDTO.builder().startTime("7:45").endTime("8:45").periode("Déjeuner").build(),
//                TimeSlotDTO.builder().startTime("8:45").periode("Rassemblement").build(),
//                TimeSlotDTO.builder().startTime("9:30").endTime("10:30").periode("Activité 1").build(),
//                TimeSlotDTO.builder().startTime("10:45").endTime("11:45").periode("Découverte").build(),
//                TimeSlotDTO.builder().startTime("12:00").endTime("13:00").periode("Dîner").build(),
//                TimeSlotDTO.builder().startTime("13:30").endTime("14:30").periode("Jeux d'équipes").build(),
//                TimeSlotDTO.builder().startTime("14:30").endTime("16:00").periode("Collation").build(),
//                TimeSlotDTO.builder().startTime("16:00").endTime("17:00").periode("Activité 2").build(),
//                TimeSlotDTO.builder().startTime("17:30").endTime("18:30").periode("Souper").build(),
//                TimeSlotDTO.builder().startTime("18:30").endTime("19:00").periode("Temps libre").build(),
//                TimeSlotDTO.builder().startTime("19:00").endTime("20:00").periode("Grand Jeu").build(),
//                TimeSlotDTO.builder().startTime("20:00").periode("Collation BJ / Jeu IS").build(),
//                TimeSlotDTO.builder().startTime("20:50").periode("Collation IS").build(),
//                TimeSlotDTO.builder().startTime("21:30").periode("Curfew Campeur").build(),
//                TimeSlotDTO.builder().startTime("24:00").periode("Curfew Staff").build()
//        );
//
//        HoraireTypiqueDTO horaireTypiqueDTO = HoraireTypiqueDTO.builder()
//                .nom("Horaire Typique")
//                .timeSlots(timeSlotDTOs)
//                .build();
//        horaireTypiqueService.createHoraireTypique(horaireTypiqueDTO);
//
//        List<ActiviteDTO> activiteDTOs = Arrays.asList(
//                ActiviteDTO.builder().nom("Campcraft").build(),
//                ActiviteDTO.builder().nom("Artisanat").build(),
//                ActiviteDTO.builder().nom("Soccer").build(),
//                ActiviteDTO.builder().nom("Kayak").build(),
//                ActiviteDTO.builder().nom("Tir à l'arc").build()
//        );
//        activiteDTOs.forEach(activiteService::createActivite);
    }
}
