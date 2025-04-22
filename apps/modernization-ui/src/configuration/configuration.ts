import { Sizing } from 'design-system/field';

type Toggle = { enabled: boolean };

type Defaults = {
    sizing: Sizing;
};

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
    defaults: Defaults;
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

type PatientProfileFeatures = {
    enabled: boolean;
};

type PatientFileFeatures = {
    enabled: boolean;
};

type PatientSearchFeatures = {
    filters: {
        enabled: boolean;
    };
};

type PatientFeatures = {
    search: PatientSearchFeatures;
    profile: PatientProfileFeatures;
    file: PatientFileFeatures;
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
    patient: PatientFeatures;
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
