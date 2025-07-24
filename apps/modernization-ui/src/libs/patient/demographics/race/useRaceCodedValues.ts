import { Selectable } from 'options';
import { useRaceCategoryOptions } from 'options/race';

type RaceCodedValues = {
    race: Selectable[];
};

const useRaceCodedValues = (): RaceCodedValues => {
    const races = useRaceCategoryOptions();

    return {
        race: races
    };
};

export { useRaceCodedValues };
export type { RaceCodedValues };
