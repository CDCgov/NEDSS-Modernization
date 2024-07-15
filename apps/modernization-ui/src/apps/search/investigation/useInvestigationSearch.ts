import { Investigation, InvestigationFilter, useFindInvestigationsByFilterLazyQuery } from 'generated/graphql/schema';
import { Interaction, ResultRequest, useSearch } from 'apps/search';
import { InvestigationFilterEntry } from './InvestigationFormTypes';

import { transformObject } from './transformer';
import { investigationTermsResolver } from './investigationTermsResolver';

const useInvestigationSearch = (): Interaction<InvestigationFilterEntry, Investigation> => {
    const [fetch] = useFindInvestigationsByFilterLazyQuery();

    const resultResolver = (request: ResultRequest<InvestigationFilter>) => {
        console.log('test', request);
        return fetch({
            variables: {
                filter: request.parameters,
                page: {
                    pageNumber: request.page.number - 1,
                    pageSize: request.page.size,
                    sortField: request.sort?.property,
                    sortDirection: request.sort?.direction
                }
            },
            notifyOnNetworkStatusChange: true
        }).then((response) => {
            if (response.error) {
                throw new Error(response.error.message);
            }
            return response.data?.findInvestigationsByFilter
                ? { ...response.data.findInvestigationsByFilter, page: request.page.number }
                : undefined;
        });
    };

    return useSearch({ transformer: transformObject, resultResolver, termResolver: investigationTermsResolver });
};

export { useInvestigationSearch };
