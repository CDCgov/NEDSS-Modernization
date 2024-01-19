import { authorization } from 'authorization';
import { ConfigurationControllerService, Properties } from 'generated';
import { useContext, useEffect, useState } from 'react';
import { UserContext } from 'user';

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

type Configuration = {
    settings: Settings;
    features: Features;
    properties: Properties;
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
    }
};

const defaultSettings: Settings = {
    smarty: {
        key: '166215385741384990'
    }
};

const defaultProperties: Properties = {
    entries: {},
    hivProgramAreas: [],
    stdProgramAreas: []
};

const initial: Configuration = {
    settings: defaultSettings,
    features: defaultFeatures,
    properties: defaultProperties
};

const useConfiguration = (): { settings: Settings; features: Features; loading: boolean; properties: Properties } => {
    const [config, setConfig] = useState<Configuration>(initial);
    const [loading, setLoading] = useState(false);
    const { state } = useContext(UserContext);

    useEffect(() => {
        if (state.isLoggedIn) {
            setLoading(true);
            ConfigurationControllerService.getConfigurationUsingGet({
                authorization: authorization()
            }).then((response) => {
                setConfig((existing) => ({ ...existing, ...(response as Configuration) }));
                setLoading(false);
            });
        }
    }, [state.isLoggedIn]);

    return { ...config, loading };
};

export { useConfiguration };
