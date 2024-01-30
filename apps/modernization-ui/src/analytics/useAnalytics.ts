import { useConfiguration } from 'configuration';
import { PostHogConfig } from 'posthog-js';
import { useEffect, useState } from 'react';

type AnalyticsSettings = {
    enabled: boolean;
    key?: string;
    options: Partial<PostHogConfig>;
};

const useAnalyticsSettings = (): AnalyticsSettings => {
    const [analyticsSettings, setAnalyticsSettings] = useState<AnalyticsSettings>({ enabled: false, options: {} });

    const { ready, settings } = useConfiguration();

    useEffect(() => {
        if (ready) {
            const key = settings.analytics?.key;
            const host = settings.analytics?.host;

            const options = {
                api_host: host
            };

            setAnalyticsSettings({
                enabled: Boolean(key && host),
                key,
                options
            });
        }
    }, [ready, settings]);

    useEffect(() => {
        console.log('ready', ready);
    }, [ready]);

    return analyticsSettings;
};

export { useAnalyticsSettings };
export type { AnalyticsSettings };
