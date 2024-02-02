import { ReactNode } from 'react';
import { AnalyticsSettings, useAnalyticsSettings } from './useAnalytics';
import { PostHogProvider } from 'posthog-js/react';

type AnalyticsProviderProps = {
    children: ReactNode;
};

const AnalyticsProvider = ({ children }: AnalyticsProviderProps) => {
    const settings = useAnalyticsSettings();
    return settings.enabled ? withAnalytics(settings, children) : <>{children}</>;
};

const withAnalytics = (settings: AnalyticsSettings, children: ReactNode) => (
    <PostHogProvider apiKey={settings.key} options={settings.options}>
        {children}
    </PostHogProvider>
);

export { AnalyticsProvider };
