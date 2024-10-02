import { UseFormReturn } from 'react-hook-form';
import { SearchInteraction, ResultRequest, useSearchResultsFormAdapter } from 'apps/search';
import { LabReport, LabReportFilter, useFindLabReportsByFilterLazyQuery } from 'generated/graphql/schema';
import {
    LabReportFilterEntry,
    initial as defaultValues,
    initialForEventId as defaultValuesForIdentification
} from './labReportFormTypes';
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
                    sort: request.sort
                }
            },
            notifyOnNetworkStatusChange: true
        }).then((response) => {
            if (response.error) {
                throw new Error(response.error.message);
            }
            return response.data?.findLabReportsByFilter;
        });

    const defaultValuesResolver = (vals: LabReportFilterEntry) =>
        vals?.identification ? defaultValuesForIdentification : defaultValues;

    return useSearchResultsFormAdapter({
        form,
        defaultValues: defaultValuesResolver,
        transformer,
        resultResolver,
        termResolver
    });
};

export { useLaboratoryReportSearch };
