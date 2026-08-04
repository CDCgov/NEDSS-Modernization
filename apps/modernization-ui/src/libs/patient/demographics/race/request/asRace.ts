import { asValue } from 'options';
import { exists } from 'utils';
import { maybeMapAll } from 'utils/mapping';

import { RaceDemographic } from '../race';

import { RaceDemographicRequest } from './raceRequest';

const maybeAsValues = maybeMapAll(asValue);

const asRace = (demographic: Partial<RaceDemographic>): RaceDemographicRequest | undefined => {
    const { race, detailed, asOf } = demographic;

    if (asOf && exists(race)) {
        return {
            asOf,
            race: asValue(race),
            detailed: maybeAsValues(detailed),
        };
    }
};

export { asRace };
