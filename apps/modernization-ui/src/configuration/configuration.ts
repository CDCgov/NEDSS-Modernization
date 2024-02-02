type Settings = {
    smarty?: {
        key: string;
    };
    analytics?: {
        key: string;
        host: string;
    };
};

type Features = {
    address: {
        autocomplete: boolean;
        verification: boolean;
    };
    pageBuilder: {
        enabled: boolean;
        page: {
            library: {
                enabled: boolean;
            };
            management: {
                create: {
                    enabled: boolean;
                };
                edit: {
                    enabled: boolean;
                };
            };
        };
    };
};

type Properties = {
    hivProgramAreas: Array<string>;
    stdProgramAreas: Array<string>;
};

type Configuration = {
    settings: Settings;
    features: Features;
    properties: Properties;
};

export type { Settings, Features, Properties, Configuration };
