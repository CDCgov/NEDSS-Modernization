import { indicators, Indicators } from 'options/indicator';

type MoralityOptions = {
    deceased: Indicators;
};

const useMortalityOptions = (): MoralityOptions => {
    return {
        deceased: indicators
    };
};

export { useMortalityOptions };
export type { MoralityOptions };
