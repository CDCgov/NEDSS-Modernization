import { PatientSearchResult, PersonFilter, useFindPatientsByFilterLazyQuery } from 'generated/graphql/schema';
import { Interaction, ResultRequest, useSearch } from 'apps/search';
import { transform } from './transformer';
import { PatientCriteriaEntry } from './criteria';
import { patientTermsResolver } from './patientTermsResovler';

const usePatientSearch = (): Interaction<PatientCriteriaEntry, PatientSearchResult> => {
    const [fetch] = useFindPatientsByFilterLazyQuery();

    const resultResolver = (request: ResultRequest<PersonFilter>) =>
        fetch({
            variables: {
                filter: request.parameters,
                page: {
                    pageNumber: request.page.number - 1,
                    pageSize: request.page.size,
                    sortField: request.sortField,
                    sortDirection: request.sortDirection
                }
            },
            notifyOnNetworkStatusChange: true
        }).then((response) => {
            if (response.error) {
                throw new Error(response.error.message);
            }
            return response.data?.findPatientsByFilter
                ? { ...response.data.findPatientsByFilter, page: request.page.number }
                : undefined;
        });

    return useSearch({ transformer: transform, resultResolver, termResolver: patientTermsResolver });
};

export { usePatientSearch };
