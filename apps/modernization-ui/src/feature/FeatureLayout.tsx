import { Outlet } from 'react-router';
import { RedirectHome } from 'routes';
import { Guard } from './guard';
import { FeatureToggle } from './FeatureToggle';

type FeatureLayoutProps = {
    guard: Guard;
};

const FeatureLayout = ({ guard }: FeatureLayoutProps) => (
    <FeatureToggle guard={guard} fallback={<RedirectHome />}>
        <Outlet />
    </FeatureToggle>
);

export { FeatureLayout };
