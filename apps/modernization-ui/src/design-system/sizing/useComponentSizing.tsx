import { createContext, ReactNode, useContext } from 'react';
import { Sizing } from 'design-system/field';
// import { useConfiguration } from 'configuration';

const FALLBACK_SIZING = 'medium';

const ComponentSizingContext = createContext<Sizing>(FALLBACK_SIZING);

type ComponentSizingProviderProps = { children: ReactNode };

const ComponentSizingProvider = ({ children }: ComponentSizingProviderProps) => {
    // const { settings } = useConfiguration();

    const sizing = 'small';

    return <ComponentSizingContext.Provider value={sizing}>{children}</ComponentSizingContext.Provider>;
};

const useComponentSizing = () => {
    const context = useContext(ComponentSizingContext);

    if (context === undefined) {
        throw new Error('useComponentSizing must be used within a ComponentSizingProvider');
    }

    return context;
};

export { ComponentSizingProvider, useComponentSizing };
