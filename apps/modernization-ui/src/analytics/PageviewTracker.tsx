import { useEffect } from 'react';
import { useLocation } from 'react-router';
import { usePostHog } from 'posthog-js/react';

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
