/* eslint-disable */

import {ActivityFilterEntry, ElrCriteriaFilter} from "./ActivityLogFormTypes";
import { ActivityFilter } from "./result/elr-result/schema";
import {asValue, asValues} from "../../../options";
import {RecordStatus} from "../../../generated/graphql/schema";

export const transformObject = (data: ActivityFilterEntry): ElrCriteriaFilter => {
    const {
        // eventDate,
        processedTime,
        // status,
        algorithmName,
        action,
        messageId,
        sourceNm,
        entityNm,
        observationId,
        accessionNumber,
        exceptionText,
    } = data;

    console.log ("Inside transform data..." + data);

    return {
        // reportType: undefined,
        // eventDateFrom: "", eventDateTo: "", //reportType: undefined,
        // eventDate,
        processedTime,
        // status: asValue(status),
        algorithmName,
        action,
        messageId,
        sourceNm: asValue(sourceNm),
        entityNm,
        observationId,
        accessionNumber,
        exceptionText: asValue(exceptionText),
        // status: asValues(status) as Status[]
    };
};
