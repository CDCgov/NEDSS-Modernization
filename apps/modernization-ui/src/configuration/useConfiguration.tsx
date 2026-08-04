import { createContext, ReactNode, useContext } from 'react';
import merge from 'lodash.merge';
import { Configuration } from './configuration';
import { defaultConfiguration } from './defaults';
import { logWarnToUserConsole } from 'utils/logging';

const initial = defaultConfiguration;
const ConfigurationContext = createContext<Configuration>(initial);

type ConfigurationProviderProps = {
    children: ReactNode;
    configuration: Configuration;
};

const ConfigurationProvider = ({ configuration, children }: ConfigurationProviderProps) => {
    const value: Configuration = merge(defaultConfiguration, configuration);

    return <ConfigurationContext.Provider value={value}>{children}</ConfigurationContext.Provider>;
};

const useConfiguration = (): Configuration => {
    const config = useContext(ConfigurationContext);

    if (config === initial) {
        logWarnToUserConsole(
            'useConfiguration used without a ConfigurationProvider. Only the default configuration will be provided.'
        );
    }

    return config;
};

export { useConfiguration, ConfigurationProvider };
