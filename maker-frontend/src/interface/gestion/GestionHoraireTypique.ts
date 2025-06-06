import {getToken} from "../Connexion.ts";
import {RoutesBE} from "../Routes.ts";
import {HoraireTypique} from "../Interface.ts";

export const addHoraireTypique = async (horaireTypique: HoraireTypique): Promise<HoraireTypique> => {
    const response = await fetch(RoutesBE.HoraireTypique, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${getToken()}`
        },
        body: JSON.stringify(horaireTypique)
    });

    return await response.json();
}

export const getHorairesTypiques = async (): Promise<HoraireTypique[]> => {
    const response = await fetch(RoutesBE.HoraireTypique, {
        headers: {
            method: 'GET',
            'Authorization': `Bearer ${getToken()}`
        }
    });

    return await response.json();
}

export const supprimerHoraireTypique = async (horaireTypique: HoraireTypique): Promise<void> => {
    const response = await fetch(RoutesBE.HoraireTypique, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify(horaireTypique),
    });

    return await response.json();
}