import { ConfigurationSetup } from './configuration/ConfigurationSetup';
import { DataElementConfig } from './data-elements/DataElementsConfig';

const routing = [
    {
        path: '/deduplication/configuration',
        element: <ConfigurationSetup />
    },
    {
        path: '/deduplication/data-elements',
        element: <DataElementConfig />
    }
];

export { routing };
