import { useConfiguration } from 'configuration';
import { Navigate } from 'react-router';

const RedirectHome = () => {
    const {
        features: { search },
    } = useConfiguration();

    const path = search.view.enabled ? '/search' : '/advanced-search';

    return <Navigate to={path} replace={true} />;
};

export { RedirectHome };
