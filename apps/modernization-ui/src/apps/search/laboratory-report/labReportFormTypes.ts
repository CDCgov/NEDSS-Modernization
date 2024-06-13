import { Selectable } from 'components/FormInputs/SelectInput';

export type FormLabReportFilter = {
    eventDate?: {
        type?: string;
        from?: string | null | undefined;
        to?: string | null | undefined;
    };
    eventId?: {
        labEventType?: string;
        labEventId?: string;
    };
    patientId?: number;
    providerSearch?: {
        providerType?: string;
        providerId?: string;
    };
    eventStatus?: Selectable[];
    jurisdictions?: Selectable[];
    lastUpdatedBy?: Selectable[];
    pregnancyStatus?: Selectable[];
    processingStatus?: Selectable[];
    programAreas?: Selectable[];
    resultedTest?: Selectable[];
    codedResult?: Selectable[];
    createdBy?: Selectable[];
    enteredBy?: Selectable[];
    entryMethods?: Selectable[];
};
