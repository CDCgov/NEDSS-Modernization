import { useEffect, useState } from 'react';

import { PostHogConfig } from 'posthog-js';

import { useConfiguration } from 'configuration';

type AnalyticsSettings = {
    enabled: boolean;
    key?: string;
    options: Partial<PostHogConfig>;
};

const useAnalyticsSettings = (): AnalyticsSettings => {
    const [analyticsSettings, setAnalyticsSettings] = useState<AnalyticsSettings>({ enabled: false, options: {} });

    const { settings } = useConfiguration();

    useEffect(() => {
        const key = settings.analytics?.key;
        const host = settings.analytics?.host;

        const options = {
            api_host: host,
            capture_pageview: false,
        };

        setAnalyticsSettings({
            enabled: Boolean(key && host),
            key,
            options,
        });
    }, [settings]);

    return analyticsSettings;
};

export { useAnalyticsSettings };
export type { AnalyticsSettings };
