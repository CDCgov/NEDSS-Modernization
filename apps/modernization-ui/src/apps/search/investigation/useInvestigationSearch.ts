import { UseFormReturn } from 'react-hook-form';
import { ResultRequest, SearchInteraction, useSearchResultsFormAdapter } from 'apps/search';
import { Investigation, InvestigationFilter, useFindInvestigationsByFilterLazyQuery } from 'generated/graphql/schema';
import { InvestigationFilterEntry } from './InvestigationFormTypes';

import { transformObject as transformer } from './transformer';
import { investigationTermsResolver as termResolver } from './investigationTermsResolver';

type Settings = {
    form: UseFormReturn<InvestigationFilterEntry>;
};

const useInvestigationSearch = ({ form }: Settings): SearchInteraction<Investigation> => {
    const [fetch] = useFindInvestigationsByFilterLazyQuery();

    const resultResolver = (request: ResultRequest<InvestigationFilter>) => {
        return fetch({
            variables: {
                filter: request.parameters,
                page: {
                    pageNumber: request.page.number - 1,
                    pageSize: request.page.size,
                    sort: request.sort
                }
            },
            notifyOnNetworkStatusChange: true
        }).then((response) => {
            if (response.error) {
                throw new Error(response.error.message);
            }
            return response.data?.findInvestigationsByFilter;
        });
    };

    return useSearchResultsFormAdapter({ form, transformer, resultResolver, termResolver });
};

export { useInvestigationSearch };
