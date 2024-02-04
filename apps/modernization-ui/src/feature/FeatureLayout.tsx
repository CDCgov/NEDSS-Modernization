import { Outlet } from 'react-router-dom';
import { useConfiguration, Features } from 'configuration';
import { RedirectHome } from 'routes';

type Guard = (features: Features) => boolean;

type FeatureLayoutProps = {
    guard: Guard;
};

const FeatureLayout = ({ guard }: FeatureLayoutProps) => {
    const { features } = useConfiguration();

    return guard(features) ? <Outlet /> : <RedirectHome />;
};

export { FeatureLayout };
