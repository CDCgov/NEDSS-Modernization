import { PatientSearchResult, PersonFilter, useFindPatientsByFilterLazyQuery } from 'generated/graphql/schema';
import { Page } from 'page';
import { transform } from './transformer';
import { PatientCriteriaEntry } from './criteria';
import { Interaction, useSearchAPI } from '../useSearchAPI';

const usePatientSearch = (): Interaction<PatientCriteriaEntry, PatientSearchResult> => {
    const [fetch] = useFindPatientsByFilterLazyQuery();

    const resolver = (parameters: PersonFilter, page: Page) =>
        fetch({
            variables: {
                filter: parameters,
                page: {
                    pageNumber: page.current,
                    pageSize: page.pageSize
                }
            }
        }).then((response) => {
            if (response.error) {
                throw new Error(response.error.message);
            }
            return response.data?.findPatientsByFilter;
        });

    const api = useSearchAPI({ transformer: transform, resolver });

    return api;
};

export { usePatientSearch };
