import { ReactNode } from 'react';

import { FeatureToggle, FeatureToggleProps } from 'feature';
import { RedirectHome } from 'routes';

type FeatureGuardProps = {
    guard: FeatureToggleProps['guard'];
    children: ReactNode;
};

const FeatureGuard = ({ guard, children }: FeatureGuardProps) => (
    <FeatureToggle guard={guard} fallback={<RedirectHome />}>
        {children}
    </FeatureToggle>
);

export { FeatureGuard };
