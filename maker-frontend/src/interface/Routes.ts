const baseBE = import.meta.env.VITE_BACKEND_URL;

export enum RoutesFE {
    Accueil = "/accueil",
    Connexion = "/connexion",
    Inscription = "/inscription",
    InscriptionMoniteur = "/inscription/moniteur",
    HoraireTypique = "/horaire-typique",
    Horaire = "/horaire-journaliere",
    HoraireId = "/horaire-journaliere/:id",
    HoraireActivitesMoniteurs = "/horaire-activites-moniteurs",
    HoraireActivitesMoniteursId = "/horaire-activites-moniteurs/:id",
    HoraireActivitesCampeurs = "/horaire-activites-campeurs/:id",
    HoraireActivites = "/horaire-activites/:id",
}

export namespace RoutesBE {
    export const Connexion = `${baseBE}/connexion`;
    export const InscriptionMoniteur = `${baseBE}/moniteur/inscription`;
    export const Etats = `${baseBE}/etat`;
    export const Departements = `${baseBE}/departement`;
    export const Activites = `${baseBE}/activite`;
    export const Groupes = `${baseBE}/groupe`;
    export const Campeurs = `${baseBE}/campeur`;
    export const ModifierUtilisateur = `${baseBE}/modifier-utilisateur`;
    export const Utilisateurs = `${baseBE}/utilisateurs`;
    export const SupprimerUtilisateur = `${baseBE}/supprimer-utilisateur`;
    export const Tentes = `${baseBE}/tente`;
    export const Moniteurs = `${baseBE}/moniteurs`;
    export const HoraireTypique = `${baseBE}/horaire-typique`;
    export const HoraireJournaliere = `${baseBE}/horaire-journaliere`;
    export const Periodes = `${baseBE}/periode`;
    export const ActiviteMoniteur = `${baseBE}/activite-moniteur`;
}