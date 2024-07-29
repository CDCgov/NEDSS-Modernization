import { Navigate } from 'react-router-dom';

import { useConfiguration } from 'configuration';

const RedirectHome = () => {
    const {
        features: { search }
    } = useConfiguration();

    const path = search.view.enabled ? '/search' : '/advanced-search';

    return <Navigate to={path} />;
};

export { RedirectHome };
