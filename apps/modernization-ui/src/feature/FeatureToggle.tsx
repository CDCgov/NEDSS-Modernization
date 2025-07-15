import { Shown } from 'conditional-render';
import { useConfiguration } from 'configuration';

import { ReactNode } from 'react';
import { Guard } from './guard';

type FeatureToggleProps = {
    guard: Guard;
    children: ReactNode;
    fallback?: ReactNode;
};

const FeatureToggle = ({ guard, children, fallback }: FeatureToggleProps) => {
    const { features } = useConfiguration();

    return (
        <Shown when={guard(features)} fallback={fallback}>
            {children}
        </Shown>
    );
};

export { FeatureToggle };
export type { FeatureToggleProps };
