import { ReactNode } from 'react';
import { RedirectHome } from 'routes';
import { FeatureToggle } from './FeatureToggle';
import { Guard } from './guard';

type FeatureGuardProps = {
    guard: Guard;
    children: ReactNode;
};

const FeatureGuard = ({ guard, children }: FeatureGuardProps) => (
    <FeatureToggle guard={guard} fallback={<RedirectHome />}>
        {children}
    </FeatureToggle>
);

export { FeatureGuard };
