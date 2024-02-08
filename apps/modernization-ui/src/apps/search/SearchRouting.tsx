import { AdvancedSearch } from './advancedSearch/AdvancedSearch';

const routing = [
    {
        path: '/advanced-search/:searchType?',
        element: <AdvancedSearch />
    }
];

export { routing };
