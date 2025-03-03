/* eslint-disable */
import { Term, fromSelectable, fromValue } from 'apps/search/terms';
import {ActivityFilterEntry} from "./ActivityLogFormTypes";

const activityLogTermsResolver = (entry: ActivityFilterEntry): Term[] => {
    console.log ("Inside termsresolver...");

    const terms: Term[] = [];

    console.log("entry is..." + JSON.stringify(entry, null, 2));

    // if (entry.eventDate && entry.eventDate?.from && entry.eventDate?.to) {
    //     if (entry.eventDate?.from) {
    //         terms.push(fromValue('eventDate.from', 'FROM')(entry.eventDate.from));
    //     }
    //     if (entry.eventDate?.to) {
    //         terms.push(fromValue('eventDate.to', 'TO')(entry.eventDate.to));
    //     }
    // }

    if (entry.status) {
        terms.push(fromValue('status', 'STATUS')(entry.status.toString()));
    }

    if (entry.processedTime) {
        terms.push(fromValue('processedTime', 'PROCESSED TIME')(entry.processedTime));
    }

    if (entry.algorithmName) {
        terms.push(fromValue('algorithmName', 'ALGORITHM NAME')(entry.algorithmName));
    }

    // if (entry.action) {
    //     terms.push(fromValue('action', 'ACTION')(entry.action));
    // }

    if (entry.messageId) {
        terms.push(fromValue('messageId', 'MESSAGE ID')(entry.messageId));
    }

    if (entry.sourceNm) {
        terms.push(fromSelectable('sourceNm', 'SOURCE NAME')(entry.sourceNm));
    }

    if (entry.entityNm) {
        terms.push(fromValue('entityNm', 'PATIENT NAME')(entry.entityNm));
    }

    if (entry.observationId) {
        terms.push(fromValue('observationId', 'OBSERVATION ID')(entry.observationId));
    }

    if (entry.accessionNumber) {
        terms.push(fromValue('accessionNumber', 'ACCESSION NUMBER')(entry.accessionNumber));
    }

    if (entry.exceptionText) {
        terms.push(fromSelectable('exceptionText', 'EXCEPTION TEXT')(entry.exceptionText));
    }

    console.log ("Inside terms resolver terms is..." + terms);
    console.log("Terms Array:", JSON.stringify(terms, null, 2));


    return terms;
};

export { activityLogTermsResolver };

