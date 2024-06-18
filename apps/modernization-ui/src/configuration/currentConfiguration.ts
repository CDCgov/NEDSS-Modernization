import { Configuration } from './configuration';
import { ConfigurationControllerService } from 'generated';

const currentConfiguration = () =>
    ConfigurationControllerService.getConfiguration().then((response) => response as Partial<Configuration>);

type CurrentConfigurationResponse = Awaited<ReturnType<typeof currentConfiguration>>;

export { currentConfiguration };
export type { CurrentConfigurationResponse };
