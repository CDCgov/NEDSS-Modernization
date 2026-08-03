import { ResultRequest, SearchInteraction, useSearchResultsFormAdapter } from 'apps/search';
import { PatientSearchResult, PersonFilter, useFindPatientsByFilterLazyQuery } from 'generated/graphql/schema';
import { UseFormReturn } from 'react-hook-form';
import { maybeMap } from 'utils/mapping';

import { PatientCriteriaEntry, initial as defaultValues } from './criteria';
import { filterResolver } from './filterResolver';
import { patientTermsResolver as termResolver } from './patientTermsResolver';
import { transform as transformer } from './transformer';

const maybeFilter = maybeMap(filterResolver);

const asSearchRequest = (request: ResultRequest<PersonFilter>) => {
    const filter = maybeFilter(request.filter);

    return filter ? { ...request.parameters, filter } : request.parameters;
};

const asSortablePage = <A>(request: ResultRequest<A>) => ({
    pageNumber: request.page.number - 1,
    pageSize: request.page.size,
    sort: request.sort,
});

type Settings = {
    form: UseFormReturn<PatientCriteriaEntry>;
};

const usePatientSearch = ({ form }: Settings): SearchInteraction<PatientSearchResult> => {
    const [fetch] = useFindPatientsByFilterLazyQuery();

    const resultResolver = (request: ResultRequest<PersonFilter>) =>
        fetch({
            variables: {
                filter: asSearchRequest(request),
                page: asSortablePage(request),
            },
            notifyOnNetworkStatusChange: true,
        }).then((response) => {
            if (response.error) {
                throw new Error(response.error.message);
            }
            return response.data?.findPatientsByFilter;
        });

    return useSearchResultsFormAdapter({ form, defaultValues, transformer, resultResolver, termResolver });
};

export { usePatientSearch };
