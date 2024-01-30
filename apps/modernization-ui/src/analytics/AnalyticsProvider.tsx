import { FC, ReactNode } from 'react';
import { PostHogProvider } from 'posthog-js/react';
import { useConfiguration } from 'configuration';

type AnalyticsProviderProps = {
    children: ReactNode;
};

export const AnalyticsProvider: FC<AnalyticsProviderProps> = ({ children }) => {
    const defaultConfig = useConfiguration();
    const options = {
        api_host: defaultConfig.settings.analytics?.host
    };

    return (
        <PostHogProvider apiKey={defaultConfig.settings.analytics?.apiKey} options={options}>
            {children}
        </PostHogProvider>
    );
};
