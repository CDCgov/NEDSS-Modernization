import { useEffect, useState } from 'react';
import { DataElements } from '../data-elements/DataElement';

export const initial = {
    belongingnessRatio: 0.9,
    firstName: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    lastName: {
        active: true,
        m: 0.4,
        u: 0.2,
        logOdds: Math.log(0.4) - Math.log(0.2),
        threshold: 0.7
    },
    suffix: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    birthDate: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    mrn: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    ssn: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    sex: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    gender: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    race: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    address: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    city: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    state: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    zip: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    county: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    },
    telephone: {
        active: true,
        m: 0.2,
        u: 0.4,
        logOdds: 0.2,
        threshold: 0.7
    }
};

export const useDataElements = () => {
    const [dataElements, setDataElements] = useState<DataElements | undefined>();
    const fetch = () => {
        // call API to retrieve values
        setDataElements(initial);
    };

    const save = (elements: DataElements): Promise<boolean> => {
        console.log('saving elements', elements);
        // call API persist values
        return Promise.resolve(true);
    };

    useEffect(() => {
        fetch();
    }, []);

    return { fetch, save, dataElements };
};
