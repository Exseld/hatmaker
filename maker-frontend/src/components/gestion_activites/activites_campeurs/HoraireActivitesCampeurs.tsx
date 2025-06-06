import {useNavigate, useParams} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import {getActiviteMoniteurById, sauvegarderAssignement} from "../../../interface/gestion/GestionActiviteMoniteur.ts";
import {
    Activite,
    ActiviteMoniteur,
    Campeur,
    Moniteur,
    VueResponsable,
    Assignement
} from "../../../interface/Interface.ts";
import HoraireActiviteCampeurTable from "./HoraireActiviteCampeurTable.tsx";
import {getMoniteurs} from "../../../interface/gestion/GestionUtilisateur.ts";
import {getActivites} from "../../../interface/gestion/GestionActivite.ts";
import {getCampeurs} from "../../../interface/gestion/GestionCampeurs.ts";
import {useViewResponsable} from "../../../context/ResponsableViewContext.tsx";
import {AuthentificatedContext} from "../../../context/AuthentificationContext.tsx";

const HoraireActivitesCampeurs = () => {
    const {id} = useParams();
    const [activiteMoniteur, setActiviteMoniteur] = useState<ActiviteMoniteur>();
    const [moniteurs, setMoniteurs] = useState<Moniteur[]>([]);
    const [activites, setActivites] = useState<Activite[]>([]);
    const [campeurs, setCampeurs] = useState<Campeur[]>([]);
    const [assignments, setAssignments] = useState<Record<string, string[]>>({});
    const [limits, setLimits] = useState<Record<string, number>>({});
    const [selectedActivites, setSelectedActivites] = useState<Record<string, string>>({});
    const {isAuthentificated} = useContext(AuthentificatedContext)
    const {setVue} = useViewResponsable();
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAuthentificated) {
            navigate("/");
        }

        if (id) {
            getActiviteMoniteurById(Number(id)).then(
                data => {
                    setActiviteMoniteur(data)
                    if (data.assignements) {
                        const newAssignments: Record<string, string[]> = {};
                        const newSelectedActivites: Record<string, string> = {};

                        data.assignements.forEach((assignement) => {
                            const key = `${assignement.periode}-${assignement.activite}`;
                            newAssignments[key] = assignement.campeurs;
                            setLimits((prev) => ({...prev, [key]: assignement.limite || 0}));

                            assignement.campeurs.forEach(campeur => {
                                newSelectedActivites[`${campeur}-${assignement.periode}`] = assignement.activite;
                            });
                        });

                        setAssignments(newAssignments);
                        setSelectedActivites(newSelectedActivites);
                    }
                }
            ).catch(
                error => console.error('Error fetching horaires:', error)
            );
        }

        getMoniteurs().then(
            data => setMoniteurs(data.filter(moniteur => moniteur.deleted !== true))
        );

        getActivites().then(
            data => setActivites(data.filter(activite => activite.deleted !== true))
        )

        getCampeurs().then(
            data => setCampeurs(data.filter(campeur => campeur.deleted !== true))
        )
    }, []);

    const handleAssignActivity = (campeur: string, periode: string, activity: string) => {
        const key = `${periode}-${activity}`;
        setAssignments((prev) => {
            const updated = { ...prev };

            Object.keys(updated).forEach((existingKey) => {
                if (existingKey.startsWith(`${periode}-`)) {
                    updated[existingKey] = updated[existingKey].filter((c) => c !== campeur);
                }
            });

            if (activity) {
                if (!updated[key]) {
                    updated[key] = [];
                }
                updated[key].push(campeur);
            }

            return updated;
        });

        setSelectedActivites((prev) => ({
            ...prev,
            [`${campeur}-${periode}`]: activity,
        }));
    };

    const handleBack = () => {
        setVue(VueResponsable.GESTION_ACTIVITES);
        navigate("/accueil");
    }

    const handleLimitChange = (periode: string, activite: string, limit: number) => {
        const key = `${periode}-${activite}`;
        setLimits((prev) => ({...prev, [key]: limit}));
    };

    const handleSauvegarderAssignement = () => {
        const updatedAssignments : Assignement[] = Object.entries(assignments).map(([key, campeurs]) : Assignement => {
            const [periode, activite] = key.split("-");
            return {
                id: activiteMoniteur?.assignements?.find(a => a.periode === periode && a.activite === activite)?.id,
                activite: activite,
                periode: periode,
                campeurs: campeurs,
                limite: limits[key] || 0,
            };
        });

        console.log(updatedAssignments);

        if (!activiteMoniteur) {
            return;
        } else {
            const updatedActiviteMoniteur : ActiviteMoniteur = {
                ...activiteMoniteur,
                assignements: updatedAssignments,
            };
            sauvegarderAssignement(updatedActiviteMoniteur).then(
                (data) => {
                    setVue(VueResponsable.GESTION_ACTIVITES);
                    navigate("/accueil");
                    console.log(data);
                }
            ).catch(
                error => console.error('Error saving assignments:', error)
            )
        }
    }

    const filteredCells = activiteMoniteur?.cells?.filter(cell => {
        const cellActivities = cell.cellData?.split(", ") || [];
        return cellActivities.some(activite => activites.some(a => a.nom === activite));
    })

    return (
        <>
            <button onClick={handleBack}
                    className="absolute border top-24 right-4 rounded pl-4 pr-4 p-2 bg-gray-200 hover:bg-gray-300 text-black">
                Retour
            </button>
            <HoraireActiviteCampeurTable
                cells={filteredCells}
                selectedPeriodes={activiteMoniteur?.selectedPeriodes}
                moniteurs={moniteurs}
                campeurs={campeurs}
                assignments={assignments}
                limits={limits}
                onAssignActivity={handleAssignActivity}
                onLimitChange={handleLimitChange}
                selectedActivites={selectedActivites}
            />
            <button
                onClick={handleSauvegarderAssignement}
                className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded m-4">
                Sauvegarder Horaire
            </button>
        </>
    );
}

export default HoraireActivitesCampeurs;