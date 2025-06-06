import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus, faChevronRight} from "@fortawesome/free-solid-svg-icons";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {getActiviteMoniteur} from "../../interface/gestion/GestionActiviteMoniteur.ts";
import {ActiviteMoniteur} from "../../interface/Interface.ts";

const GestionActivites = () => {
    const navigate = useNavigate();
    const [horaires, setHoraires] = useState<ActiviteMoniteur[]>([]);

    useEffect(() => {
        getActiviteMoniteur().then(
            data => {
                setHoraires(data.filter(horaire => horaire.deleted !== true))
            }
        ).catch(
            error => console.error('Error fetching horaires:', error)
        )
    }, []);

    return (
        <div className="p-4">
            <div className="flex justify-between items-center mb-4">
                <h1 className="text-2xl font-bold">Horaire Activités</h1>
            </div>
            <div className="relative overflow-x-auto shadow-md border rounded">
                <table className="w-full text-sm text-left text-gray-500">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-300">
                    <tr>
                        <th scope="col" className="px-6 py-3">Nom</th>
                        <th scope="col" className="px-6 py-3">Date</th>
                        <th scope="col" className="px-6 py-3">Horaire des activités avec campeurs</th>
                        <th scope="col" className="px-6 py-3 text-right">
                            <button
                                className="w-10 h-10 bg-gray-100 rounded shadow-md"
                                onClick={() => navigate("/horaire-activites-moniteurs")}>
                                <FontAwesomeIcon icon={faPlus} size="lg"/>
                            </button>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {horaires.map((horaire) => (
                        <tr key={horaire.id} className="bg-white hover:bg-gray-50 border-b">
                            <td className="px-6 py-4">{horaire.name}</td>
                            <td className="px-6 py-4">{horaire.date}</td>
                            <td className="px-6 py-4">
                                <button
                                    className="p-2 bg-gray-100 rounded shadow-md"
                                    onClick={() => navigate(`/horaire-activites-campeurs/${horaire.id}`)}>
                                    Horaire des campeurs
                                </button>
                            </td>
                            <td className="px-6 py-4 text-right">
                                <button
                                    className="w-10 h-10"
                                    onClick={() => navigate(`/horaire-activites-moniteurs/${horaire.id}`)}>
                                    <FontAwesomeIcon icon={faChevronRight}/>
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default GestionActivites;