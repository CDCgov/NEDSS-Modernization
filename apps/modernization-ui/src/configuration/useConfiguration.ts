import { Config } from 'config';

type Settings = {
    smarty?: {
        key: string;
    };
};

type Features = {
    address: {
        autocomplete: boolean;
        verification: boolean;
    };
};

type Configuration = {
    settings: Settings;
    features: Features;
};

const defaultFeatures = {
    ...{
        address: {
            autocomplete: false,
            verification: false
        }
    },
    ...Config.features
};

const initial: Configuration = {
    settings: {
        smarty: {
            key: '166215385741384990'
        }
    },
    features: defaultFeatures
};

const useConfiguration = (): Configuration => initial;

export { useConfiguration };

export type { Configuration, Settings };
