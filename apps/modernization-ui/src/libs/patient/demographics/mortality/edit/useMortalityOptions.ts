import { indicators, Indicators } from 'options/indicator';
import { LocationOptions, useLocationOptions } from 'options/location';

type MoralityOptions = {
    deceased: Indicators;
    location: LocationOptions;
};

const useMortalityOptions = (): MoralityOptions => {
    const location = useLocationOptions();

    return {
        deceased: indicators,
        location
    };
};

export { useMortalityOptions };
export type { MoralityOptions };
