type Settings = {
    smarty?: {
        key: string;
    };
};

type Configuration = {
    settings: Settings;
};

const initial: Configuration = {
    settings: {
        smarty: {
            key: '166215385741384990'
        }
    }
};

const useConfiguration = (): Configuration => initial;

export { useConfiguration };

export type { Configuration, Settings };
