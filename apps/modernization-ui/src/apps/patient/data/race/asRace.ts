import { asValue, asValues } from 'options';
import { exists } from 'utils';

import { Race } from './api';
import { RaceEntry } from './entry';

const asRace = (entry: RaceEntry): Race | undefined => {
    const { race, detailed, asOf } = entry;

    if (exists(race)) {
        return {
            asOf,
            race: asValue(race),
            detailed: asValues(detailed),
        };
    }
};

export { asRace };
