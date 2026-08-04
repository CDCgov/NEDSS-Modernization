import { ReactNode } from 'react';

import { PostHogProvider } from 'posthog-js/react';

import { PageviewTracker } from './PageviewTracker';
import { AnalyticsSettings, useAnalyticsSettings } from './useAnalytics';

type AnalyticsProviderProps = {
    children: ReactNode;
};

const AnalyticsProvider = ({ children }: AnalyticsProviderProps) => {
    const settings = useAnalyticsSettings();
    return settings.enabled ? withAnalytics(settings, children) : <>{children}</>;
};

const withAnalytics = (settings: AnalyticsSettings, children: ReactNode) => (
    <PostHogProvider apiKey={settings.key!} options={settings.options}>
        <PageviewTracker />
        {children}
    </PostHogProvider>
);

export { AnalyticsProvider };
