import { asValue, asValues } from 'options';
import { RaceEntry } from '../entry';
import { Race } from '../api';

const asRace = (entry: RaceEntry): Race => {
    const { race, detailed, ...remaining } = entry;

    return {
        race: asValue(race),
        detailed: asValues(detailed),
        ...remaining
    };
};

export { asRace };
