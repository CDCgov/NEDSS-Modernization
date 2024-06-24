import { PatientSearchResult, PersonFilter, useFindPatientsByFilterLazyQuery } from 'generated/graphql/schema';
import { Interaction, PageRequest, useSearchAPI } from '../useSearchAPI';
import { transform } from './transformer';
import { PatientCriteriaEntry } from './criteria';
import { patientTermsResolver } from './patientTermsResovler';

const usePatientSearch = (): Interaction<PatientCriteriaEntry, PatientSearchResult> => {
    const [fetch] = useFindPatientsByFilterLazyQuery();

    const resultResolver = (parameters: PersonFilter, page: PageRequest) =>
        fetch({
            variables: {
                filter: parameters,
                page: {
                    pageNumber: page.number - 1,
                    pageSize: page.size
                }
            }
        }).then((response) => {
            if (response.error) {
                throw new Error(response.error.message);
            }
            return response.data?.findPatientsByFilter
                ? { ...response.data.findPatientsByFilter, page: page.number }
                : undefined;
        });

    return useSearchAPI({ transformer: transform, resultResolver, termResolver: patientTermsResolver });
};

export { usePatientSearch };
