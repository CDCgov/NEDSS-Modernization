import { currentConfiguration } from 'configuration';
import { CurrentConfigurationResponse } from 'configuration/currentConfiguration';
import { currentUser, CurrentUserResponse } from 'user';

const initializationLoader = async () => {
    return { user: await currentUser(), configuration: await currentConfiguration() };
};

type InitializationLoaderResult = { user: CurrentUserResponse; configuration: CurrentConfigurationResponse };

export { initializationLoader };
export type { InitializationLoaderResult };
