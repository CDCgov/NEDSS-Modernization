import { Features, Properties, Configuration, Settings } from './configuration';

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
    }
};

const defaultProperties: Properties = {
    hivProgramAreas: [],
    stdProgramAreas: []
};

const defaultSettings: Settings = {};

const defaultConfiguration: Configuration = {
    settings: defaultSettings,
    features: defaultFeatures,
    properties: defaultProperties
};

export { defaultConfiguration };
