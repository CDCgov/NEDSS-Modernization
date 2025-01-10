type Toggle = { enabled: boolean };

type Settings = {
    session: {
        warning: number;
        expiration: number;
    };
    smarty?: {
        key: string;
    };
    analytics?: {
        key: string;
        host: string;
    };
};

type SearchView = Toggle & {
    table: Toggle;
};

type Search = {
    events: Toggle;
    investigations: Toggle;
    laboratoryReports: Toggle;
    view: SearchView;
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
    deduplication: {
        enabled: boolean;
    };
    search: Search;
    patient: {
        profile: {
            enabled: boolean;
        };
        add: {
            enabled: boolean;
            extended: {
                enabled: boolean;
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

export type { Settings, Features, Search, Properties, Configuration };
