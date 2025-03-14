import { request } from 'generated/core/request';
import { Configuration } from './configuration';
import { CancelablePromise, OpenAPI } from 'generated';

const fetchConfig = (): CancelablePromise<Configuration> => {
    return request(OpenAPI, {
        method: 'GET',
        url: '/nbs/api/configuration'
    });
};

const currentConfiguration = () => fetchConfig().then((response) => response as Configuration);

type CurrentConfigurationResponse = Awaited<ReturnType<typeof currentConfiguration>>;

export { currentConfiguration };
export type { CurrentConfigurationResponse };
