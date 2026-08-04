import { exists } from 'utils/exists';
import { maybeMap } from 'utils/mapping';

import { Provider } from './provider';

const displayProvider = maybeMap((provider: Provider) => {
    return [provider.prefix, provider.first, provider.last].filter(exists).join(' ');
});

export { displayProvider };
