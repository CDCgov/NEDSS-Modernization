import { useState } from 'react';
import { DataElements } from '../data-elements/DataElement';

export const useDataElements = () => {
    const [dataElements, setDataElements] = useState<DataElements | undefined>();
    const fetch = () => {
        // call API to retrieve values
        setDataElements({
            belongingnessRatio: 0.9,
            firstName: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            lastName: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            suffix: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            birthDate: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            mrn: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            ssn: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            sex: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            gender: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            race: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            address: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            city: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            state: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            zip: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            county: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            },
            telephone: {
                active: true,
                m: 0.2,
                u: 0.4,
                logOdds: 0.2 / 0.4,
                threshold: Math.log(0.2 / 0.4)
            }
        });
    };

    const save = (elements: DataElements): Promise<boolean> => {
        // call API persist values
        return Promise.resolve(true);
    };

    return { fetch, save, dataElements };
};
