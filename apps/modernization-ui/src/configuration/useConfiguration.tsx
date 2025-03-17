import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import merge from 'lodash/merge';
import { Configuration } from './configuration';
import { defaultConfiguration } from './defaults';
import { currentConfiguration } from './currentConfiguration';

type InternalState =
    | { status: 'default'; configuration: Configuration }
    | { status: 'loading'; configuration: Configuration }
    | { status: 'ready'; configuration: Configuration };

type Action = { type: 'load' } | { type: 'loaded'; configuration: Partial<Configuration> };

const reducer = (state: InternalState, action: Action): InternalState => {
    switch (action.type) {
        case 'load':
            return { ...state, status: 'loading' };
        case 'loaded':
            return { status: 'ready', configuration: merge(state.configuration, action.configuration) };
        default:
            return initialize(defaultConfiguration);
    }
};

const initialize = (configuration?: Configuration): InternalState => {
    if (configuration) {
        return { status: 'ready', configuration: merge(defaultConfiguration, configuration) };
    } else {
        return {
            status: 'default',
            configuration: defaultConfiguration
        };
    }
};

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
    initial?: Configuration;
};

const ConfigurationProvider = ({ initial, children }: ConfigurationProviderProps) => {
    const [state, dispatch] = useReducer(reducer, initial, initialize);

    const ready = state.status === 'ready';
    const load = () => dispatch({ type: 'load' });

    useEffect(() => {
        if (state.status === 'loading') {
            currentConfiguration().then((configuration) => {
                dispatch({ type: 'loaded', configuration });
            });
        }
    }, [state.status]);

    const value: Interaction = {
        ...state.configuration,
        loading: state.status === 'loading',
        ready,
        load
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
