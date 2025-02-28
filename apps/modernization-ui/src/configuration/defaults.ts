import { Features, Properties, Configuration, Settings, Search } from './configuration';

const search: Search = {
    events: {
        enabled: true
    },
    investigations: {
        enabled: true
    },
    laboratoryReports: {
        enabled: true
    },
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
    deduplication: {
        enabled: false
    },
    search,
    patient: {
        search: {
            filters: {
                enabled: false
            }
        },
        profile: {
            enabled: false
        },
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
        warning: 1000 * 60 * 18,
        expiration: 1000 * 60 * 20
    },
    defaults: {
        sizing: 'large'
    }
};

const defaultConfiguration: Configuration = {
    settings: defaultSettings,
    features: defaultFeatures,
    properties: defaultProperties
};

export { defaultConfiguration };
