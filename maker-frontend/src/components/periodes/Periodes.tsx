import {useEffect, useState} from "react";
import {Periode} from "../../interface/Interface.ts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus, faChevronRight} from "@fortawesome/free-solid-svg-icons";
import AddPeriodeModal from "./AddPeriodeModal";
import {getPeriodes} from "../../interface/gestion/GestionPeriodes.tsx";

const Periodes = () => {
    const [periodes, setPeriodes] = useState<Periode[]>([]);
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);

    useEffect(() => {
        getPeriodes().then(
            data => setPeriodes(data.filter(periode => periode.deleted !== true))
        ).catch(
            error => console.error('Error fetching periodes:', error)
        );
    }, []);

    const handleSave = (newPeriode: Periode) => {
        setPeriodes([...periodes, newPeriode]);
    };

    return (
        <div className="p-4">
            <div className="flex justify-between items-center mb-4">
                <h1 className="text-2xl font-bold">Périodes</h1>
            </div>
            <div className="relative overflow-x-auto shadow-md border rounded">
                <table className="w-full text-sm text-left text-gray-500">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-300">
                    <tr>
                        <th scope="col" className="px-6 py-3">Nom</th>
                        <th scope="col" className="px-6 py-3">Temps</th>
                        <th scope="col" className="px-6 py-3 text-right">
                            <button
                                className="w-10 h-10 bg-gray-100 rounded shadow-md"
                                onClick={() => setIsAddModalOpen(true)}>
                                <FontAwesomeIcon icon={faPlus} size="lg"/>
                            </button>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {periodes.map((periode) => (
                        <tr key={periode.id} className="bg-white hover:bg-gray-50 border-b">
                            <td className="px-6 py-4">{periode.periode}</td>
                            <td className="px-6 py-4">{periode.startTime} {periode.endTime ? "- " + periode.endTime : ""}</td>
                            <td className="px-6 py-4 text-right">
                                <button
                                    className="w-10 h-10"
                                    onClick={() => console.log()}>
                                    <FontAwesomeIcon icon={faChevronRight}/>
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <AddPeriodeModal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} onSave={handleSave}/>
        </div>
    );
};

export default Periodes;