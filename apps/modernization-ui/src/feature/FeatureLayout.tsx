import { Outlet } from 'react-router';

import { RedirectHome } from 'routes';

import { FeatureToggle } from './FeatureToggle';
import { Guard } from './guard';

type FeatureLayoutProps = {
    guard: Guard;
};

const FeatureLayout = ({ guard }: FeatureLayoutProps) => (
    <FeatureToggle guard={guard} fallback={<RedirectHome />}>
        <Outlet />
    </FeatureToggle>
);

export { FeatureLayout };
