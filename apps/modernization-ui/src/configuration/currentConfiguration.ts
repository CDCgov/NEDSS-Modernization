import { authorization } from 'authorization';
import { Configuration } from './configuration';
import { ConfigurationControllerService } from 'generated';

const currentConfiguration = () =>
    ConfigurationControllerService.getConfigurationUsingGet({
        authorization: authorization()
    }).then((response) => response as Configuration);

type CurrentConfigurationResponse = Awaited<ReturnType<typeof currentConfiguration>>;

export { currentConfiguration };
export type { CurrentConfigurationResponse };
