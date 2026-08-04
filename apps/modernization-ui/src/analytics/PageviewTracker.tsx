import { useEffect } from 'react';

import { usePostHog } from 'posthog-js/react';
import { useLocation } from 'react-router';

const PageviewTracker = () => {
    const { pathname, search } = useLocation();
    const posthog = usePostHog();

    useEffect(() => {
        if (posthog) {
            posthog.capture('$pageview');
        }
    }, [pathname, search, posthog]);

    return null;
};

export { PageviewTracker };
