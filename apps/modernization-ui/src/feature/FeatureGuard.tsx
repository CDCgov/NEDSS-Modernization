import { ReactNode } from 'react';
import { useConfiguration, Features } from 'configuration';
import { RedirectHome } from 'routes';

type Guard = (features: Features) => boolean;

type FeatureGuardProps = {
    guard: Guard;
    children: ReactNode;
};

const FeatureGuard = ({ guard, children }: FeatureGuardProps) => {
    const { features } = useConfiguration();

    return guard(features) ? <>{children}</> : <RedirectHome />;
};

export { FeatureGuard };
