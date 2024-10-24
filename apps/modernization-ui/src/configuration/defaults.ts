import { Features, Properties, Configuration, Settings, Search } from './configuration';

const search: Search = {
    view: {
        table: {
            enabled: false
        },
        enabled: false
    }
};

const defaultFeatures: Features = {
    address: {
        autocomplete: false,
        verification: false
    },
    pageBuilder: {
        enabled: false,
        page: {
            library: {
                enabled: false
            },
            management: {
                create: {
                    enabled: false
                },
                edit: {
                    enabled: false
                }
            }
        }
    },
    search,
    patient: {
        add: {
            enabled: false,
            extended: {
                enabled: false
            }
        }
    }
};

const defaultProperties: Properties = {
    hivProgramAreas: [],
    stdProgramAreas: []
};

const defaultSettings: Settings = {
    session: {
        warning: 1000 * 60 * 15,
        expiration: 1000 * 60 * 20
    }
};

const defaultConfiguration: Configuration = {
    settings: defaultSettings,
    features: defaultFeatures,
    properties: defaultProperties
};

export { defaultConfiguration };
