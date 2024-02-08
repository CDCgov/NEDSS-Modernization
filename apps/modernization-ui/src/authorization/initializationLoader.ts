import { currentConfiguration } from 'configuration';
import { CurrentConfigurationResponse } from 'configuration/currentConfiguration';
import { defer } from 'react-router-dom';
import { currentUser, CurrentUserResponse } from 'user';

const initializationLoader = () => {
    return defer({ user: currentUser(), configuration: currentConfiguration() });
};

type InitializationLoaderResult = { user: CurrentUserResponse; configuration: CurrentConfigurationResponse };

export { initializationLoader };
export type { InitializationLoaderResult };
