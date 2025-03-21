import { Navigate } from 'react-router';

import { useConfiguration } from 'configuration';

const RedirectHome = () => {
    const {
        features: { search }
    } = useConfiguration();

    const path = search.view.enabled ? '/search' : '/advanced-search';

    return <Navigate to={path} />;
};

export { RedirectHome };
