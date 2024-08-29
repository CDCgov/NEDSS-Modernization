import { UseFormReturn } from 'react-hook-form';
import { SearchInteraction, ResultRequest, useSearch } from 'apps/search';
import { LabReport, LabReportFilter, useFindLabReportsByFilterLazyQuery } from 'generated/graphql/schema';
import { LabReportFilterEntry, initial as defaultValues } from './labReportFormTypes';
import { transformObject as transformer } from './transformer';
import { laboratoryReportTermsResolver as termResolver } from './laboratoryReportTermsResolver';

type Settings = {
    form: UseFormReturn<LabReportFilterEntry>;
};

const useLaboratoryReportSearch = ({ form }: Settings): SearchInteraction<LabReport> => {
    const [fetch] = useFindLabReportsByFilterLazyQuery();

    const resultResolver = (request: ResultRequest<LabReportFilter>) =>
        fetch({
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
            return response.data?.findLabReportsByFilter;
        });

    return useSearch({ form, defaultValues, transformer, resultResolver, termResolver });
};

export { useLaboratoryReportSearch };
