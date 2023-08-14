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

const initial: Configuration = {
    settings: {
        smarty: {
            key: '166215385741384990'
        }
    },
    features: {
        address: {
            autocomplete: true,
            verification: true
        }
    }
};

const useConfiguration = (): Configuration => initial;

export { useConfiguration };

export type { Configuration, Settings };
