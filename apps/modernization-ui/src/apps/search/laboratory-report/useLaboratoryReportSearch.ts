import { LabReport, LabReportFilter, useFindLabReportsByFilterLazyQuery } from 'generated/graphql/schema';
import { Interaction, ResultRequest, useSearch } from 'apps/search';
import { LabReportFilterEntry } from './labReportFormTypes';
import { transformObject } from './transformer';
import { laboratoryReportTermsResolver } from './laboratoryReportTermsResolver';

const useLaboratoryReportSearch = (): Interaction<LabReportFilterEntry, LabReport> => {
    const [fetch] = useFindLabReportsByFilterLazyQuery();

    const resultResolver = (request: ResultRequest<LabReportFilter>) =>
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
            return response.data?.findLabReportsByFilter
                ? { ...response.data.findLabReportsByFilter, page: request.page.number }
                : undefined;
        });

    return useSearch({ transformer: transformObject, resultResolver, termResolver: laboratoryReportTermsResolver });
};

export { useLaboratoryReportSearch };
