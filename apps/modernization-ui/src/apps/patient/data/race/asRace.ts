import { asValue, asValues } from 'options';
import { RaceEntry } from './entry';
import { Race } from './api';

const asRace = (entry: RaceEntry): Race => {
    const { race, detailed, asOf } = entry;

    return {
        asOf,
        race: asValue(race) ?? '',
        detailed: asValues(detailed)
    };
};

export { asRace };
