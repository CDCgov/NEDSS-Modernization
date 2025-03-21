import { currentConfiguration } from 'configuration';
import { CurrentConfigurationResponse } from 'configuration/currentConfiguration';
import { currentUser, CurrentUserResponse } from 'user';

const initializationLoader = () => {
    return { user: currentUser(), configuration: currentConfiguration() };
};

type InitializationLoaderResult = { user: CurrentUserResponse; configuration: CurrentConfigurationResponse };

export { initializationLoader };
export type { InitializationLoaderResult };
