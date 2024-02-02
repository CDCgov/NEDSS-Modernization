import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { ConfigurationControllerService } from 'generated';
import { authorization } from 'authorization';
import { Configuration } from './configuration';
import { defaultConfiguration } from './defaults';

type InternalState =
    | { status: 'default'; configuration: Configuration }
    | { status: 'loading'; configuration: Configuration }
    | { status: 'ready'; configuration: Configuration };

type Action = { type: 'load' } | { type: 'loaded'; configuration: Configuration };

const reducer = (state: InternalState, action: Action): InternalState => {
    switch (action.type) {
        case 'load':
            return { ...state, status: 'loading' };
        case 'loaded':
            return { status: 'ready', configuration: { ...state.configuration, ...action.configuration } };
        default:
            return initialize(defaultConfiguration);
    }
};

const initialize = (configuration: Configuration): InternalState => ({
    status: 'default',
    configuration
});

const initial: Interaction = {
    ready: false,
    loading: false,
    load: () => {},
    ...defaultConfiguration
};

type Interaction = Configuration & {
    ready: boolean;
    loading: boolean;
    load: () => void;
};

const ConfigurationContext = createContext<Interaction>(initial);

type ConfigurationProviderProps = {
    children: ReactNode;
};

const ConfigurationProvider = ({ children }: ConfigurationProviderProps) => {
    const [state, dispatch] = useReducer(reducer, defaultConfiguration, initialize);

    useEffect(() => {
        if (state.status === 'loading') {
            ConfigurationControllerService.getConfigurationUsingGet({
                authorization: authorization()
            }).then((response) => {
                dispatch({ type: 'loaded', configuration: response as Configuration });
            });
        }
    }, [state.status]);

    const value: Interaction = {
        ...state.configuration,
        loading: state.status === 'loading',
        ready: state.status === 'ready',
        load: () => dispatch({ type: 'load' })
    };

    return <ConfigurationContext.Provider value={value}>{children}</ConfigurationContext.Provider>;
};

const useConfiguration = (): Interaction => {
    const interaction = useContext(ConfigurationContext);

    if (interaction === undefined) {
        console.warn(
            'useConfiguration used without a ConfigurationProvider.   Only the default configuration  will be provided.'
        );
    }

    return interaction;
};

export { useConfiguration, ConfigurationProvider };
