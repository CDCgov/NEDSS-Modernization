import { asValue, asValues } from 'options';
import { RaceEntry } from './entry';
import { Race } from './api';
import { exists } from 'utils';

const asRace = (entry: RaceEntry): Race | undefined => {
    const { race, detailed, asOf } = entry;

    if (exists(race)) {
        return {
            asOf,
            race: asValue(race),
            detailed: asValues(detailed)
        };
    }
};

export { asRace };
