import { Selectable } from 'options';
import { useDetailedRaceOptions, useRaceCategoryOptions } from 'options/race';
import { useCallback } from 'react';

type RaceOptions = {
    categories: Selectable[];
    selected: (category?: Selectable) => void;
    details: Selectable[];
};

const useRaceOptions = (): RaceOptions => {
    const categories = useRaceCategoryOptions();

    const { options: details, load } = useDetailedRaceOptions();

    const selected = useCallback((category?: Selectable) => load(category?.value), [load]);

    return {
        categories,
        details,
        selected
    };
};

export { useRaceOptions };
export type { RaceOptions };
