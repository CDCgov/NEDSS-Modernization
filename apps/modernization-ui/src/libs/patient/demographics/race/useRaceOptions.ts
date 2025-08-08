import { Selectable } from 'options';
import { useRaceCategoryOptions } from 'options/race';

type RaceOptions = {
    race: Selectable[];
};

const useRaceOptions = (): RaceOptions => {
    const races = useRaceCategoryOptions();

    return {
        race: races
    };
};

export { useRaceOptions };
export type { RaceOptions };
