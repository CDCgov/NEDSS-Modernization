/* eslint-disable */
import {UseFormReturn} from 'react-hook-form';
import {SearchInteraction, useSearchResultsFormAdapter} from 'apps/search';
import {ActivityFilterEntry} from './ActivityLogFormTypes';

import {transformObject as transformer} from './transformer';
import {activityLogTermsResolver as termResolver} from './activityLogTermsResolver';
// import {ActivityLog} from "./result/elr-result/schema";

type Settings = {
    form: UseFormReturn<ActivityFilterEntry>;
};

const useActivityLogSearch = ({ form }: Settings): SearchInteraction<ActivityFilterEntry> => {

    console.log ("Inside useActivityLogSearch...");

    const resultResolver = async () => {
        console.log("Inside result resolver fetching data...");
        const response = await fetch('/db_results.json',
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            }
        );
        if (response) {
            console.log(response);
        }
        if(!response.ok) {
            console.error("Error in response");
        }
        const data = await response.json();
        const transformedData: ActivityFilterEntry[] = data.map((entry: any) => ({
            reportType: entry.imp_exp_ind_cd ? { id: entry.imp_exp_ind_cd, name: entry.imp_exp_ind_cd === "I" ? "Import" : "Export" } : undefined,
            eventDate: entry.record_status_time ? { startDate: new Date(entry.record_status_time) } : undefined,
            status: entry.record_status_cd ? [{ id: entry.record_status_cd, name: entry.record_status_cd }] : undefined,
            processedTime: entry.record_status_time || '',
            algorithmName: entry.algorithm_name || '',
            action: entry.algorithm_action || '',
            messageId: entry.Message_id || '',
            sourceNm: entry.source_nm ? { id: entry.source_uid, name: entry.source_nm } : undefined,
            entityNm: entry.Entity_nm || '',
            observationId: entry.business_obj_localId || '',
            accessionNumber: entry.Accession_nbr || '',
            exceptionText: entry.exception_txt ? { id: entry.exception_txt, name: entry.exception_txt } : undefined
        }));

        console.warn("Transformed Data:", transformedData);
        return {
            size: transformedData.length, // Number of records per page
            total: transformedData.length, // Assuming full dataset is returned
            page: 1, // Defaulting to first page
            content: transformedData
        };
    };

    return useSearchResultsFormAdapter({ form, transformer, resultResolver, termResolver });
};

export { useActivityLogSearch };
