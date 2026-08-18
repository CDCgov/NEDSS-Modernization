import { currentConfiguration } from 'configuration';
import { CurrentConfigurationResponse } from 'configuration/currentConfiguration';
import { currentUser, CurrentUserResponse } from 'user';

const initializationLoader = (): InitializationLoaderResult => {
    return { user: currentUser(), configuration: currentConfiguration() };
};

type InitializationLoaderResult = {
    user: Promise<CurrentUserResponse>;
    configuration: Promise<CurrentConfigurationResponse>;
};

export { initializationLoader };
export type { InitializationLoaderResult };
