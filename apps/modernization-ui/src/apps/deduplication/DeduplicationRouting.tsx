import { DataElementConfig } from './data-elements/DataElementConfig';
import { MatchConfiguration } from './match-configuration/MatchConfiguration';

const routing = [
    {
        path: '/deduplication/match-configuration',
        element: <MatchConfiguration />
    },
    {
        path: '/deduplication/data-elements',
        element: <DataElementConfig />
    }
];

export { routing };
