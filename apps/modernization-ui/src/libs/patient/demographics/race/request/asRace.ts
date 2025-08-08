import { asValue } from 'options';
import { exists } from 'utils';
import { RaceDemographicRequest } from './raceRequest';
import { RaceDemographic } from '../race';
import { maybeMapAll } from 'utils/mapping';

const maybeAsValues = maybeMapAll(asValue);

const asRace = (demographic: Partial<RaceDemographic>): RaceDemographicRequest | undefined => {
    const { race, detailed, asOf } = demographic;

    if (asOf && exists(race)) {
        return {
            asOf,
            race: asValue(race),
            detailed: maybeAsValues(detailed)
        };
    }
};

export { asRace };
