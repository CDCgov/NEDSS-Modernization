import { ConfigurationControllerService } from 'generated';
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
    };
};

type Configuration = {
    settings: Settings;
    features: Features;
};

const defaultFeatures: Features = {
    address: {
        autocomplete: false,
        verification: false
    },
    pageBuilder: {
        enabled: false
    }
};

const defaultSettings: Settings = {
    smarty: {
        key: '166215385741384990'
    }
};

const initial: Configuration = {
    settings: defaultSettings,
    features: defaultFeatures
};

const useConfiguration = (): { settings: Settings; features: Features; loading: boolean } => {
    const [config, setConfig] = useState<Configuration>(initial);
    const [loading, setLoading] = useState(false);
    const { state } = useContext(UserContext);

    useEffect(() => {
        if (state.isLoggedIn) {
            setLoading(true);
            ConfigurationControllerService.getConfigurationUsingGet({
                authorization: `Bearer ${state.getToken()}`
            }).then((response) => {
                setConfig((existing) => ({ ...existing, ...(response as Configuration) }));
                setLoading(false);
            });
        }
    }, [state.isLoggedIn]);

    return { ...config, loading };
};

export { useConfiguration };
