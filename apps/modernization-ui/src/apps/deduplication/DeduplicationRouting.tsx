import { ConfigurationSetup } from './configuration/ConfigurationSetup';
import { DataElementConfig } from './data-elements/DataElementConfig';

const routing = [
    {
        path: '/deduplication/configuration',
        element: <ConfigurationSetup />
    },
    {
        path: '/deduplication/data_elements',
        element: <DataElementConfig />
    }
];

export { routing };
