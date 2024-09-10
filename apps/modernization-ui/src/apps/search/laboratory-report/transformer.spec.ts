import {
    EntryMethod,
    EventStatus,
    LaboratoryEventIdType,
    LaboratoryReportStatus,
    ProviderType,
    UserType
} from 'generated/graphql/schema';
import { LabReportFilterEntry } from './labReportFormTypes';
import { transformObject } from './transformer';

describe('transformObject', () => {
    it('should include entered by when present', () => {
        const criteria = {
            enteredBy: [{ name: 'Internal', label: 'Intenral', value: 'INTERNAL' }]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                enteredBy: expect.arrayContaining([UserType.Internal])
            })
        );
    });

    it('should handle an empty object', () => {
        const input = {};

        const expected = {};

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });
});

describe('when the Laboratory Seach Criteria contains General search criteria', () => {
    it('should tranform with Program Areas', () => {
        const input: LabReportFilterEntry = {
            programAreas: [
                { name: 'Area One Name', label: 'Area One Label', value: 'area-one' },
                { name: 'Area Two Name', label: 'Area Two Label', value: 'area-two' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                programAreas: ['area-one', 'area-two']
            })
        );
    });

    it('should tranform with Jurisdictions', () => {
        const input: LabReportFilterEntry = {
            jurisdictions: [
                { name: 'Jurisdiction One Name', label: 'Jurisdiction One Label', value: '181' },
                { name: 'Jurisdiction Two Name', label: 'Jurisdiction Two Label', value: '239' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                jurisdictions: [181, 239]
            })
        );
    });

    it('should transform with Pregnancy status', () => {
        const input: LabReportFilterEntry = {
            pregnancyStatus: { name: 'Pregnancy Name', label: 'Pregnancy Label', value: 'pregnancy-value' }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                pregnancyStatus: 'pregnancy-value'
            })
        );
    });

    it('should transform with Event Id', () => {
        const input: LabReportFilterEntry = {
            identification: {
                type: { name: 'ID Type Name', label: 'ID Type Label', value: 'ACCESSION_NUMBER' },
                value: 'identification-value'
            }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                eventId: {
                    labEventId: 'identification-value',
                    labEventType: LaboratoryEventIdType.AccessionNumber
                }
            })
        );
    });

    it('should transform with Event date', () => {
        const input: LabReportFilterEntry = {
            eventDate: {
                type: { name: 'Date Type Name', label: 'Date Type Label', value: 'DATE_OF_REPORT' },
                from: 'from-date',
                to: 'to-date'
            }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                eventDate: expect.objectContaining({
                    type: 'DATE_OF_REPORT',
                    from: 'from-date',
                    to: 'to-date'
                })
            })
        );
    });

    it('should tranform with Entry Methods', () => {
        const input: LabReportFilterEntry = {
            entryMethods: [
                { name: 'Entry One Name', label: 'Entry One Label', value: 'ELECTRONIC' },
                { name: 'Entry Two Name', label: 'Entry Two Label', value: 'MANUAL' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                entryMethods: expect.arrayContaining([EntryMethod.Electronic, EntryMethod.Manual])
            })
        );
    });

    it('should tranform with Entered by', () => {
        const input: LabReportFilterEntry = {
            enteredBy: [
                { name: 'Entered One Name', label: 'Entered One Label', value: 'EXTERNAL' },
                { name: 'Entered Two Name', label: 'Entered Two Label', value: 'INTERNAL' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                enteredBy: expect.arrayContaining([UserType.External, UserType.Internal])
            })
        );
    });

    it('should tranform with Event Status', () => {
        const input: LabReportFilterEntry = {
            eventStatus: [
                { name: 'Event One Name', label: 'Event One Label', value: 'UPDATE' },
                { name: 'Event Two Name', label: 'Event Two Label', value: 'NEW' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                eventStatus: [EventStatus.Update, EventStatus.New]
            })
        );
    });

    it('should tranform with Processing status', () => {
        const input: LabReportFilterEntry = {
            processingStatus: [
                { name: 'Processing One Name', label: 'Processing One Label', value: 'PROCESSED' },
                { name: 'Processing Two Name', label: 'Processing Two Label', value: 'UNPROCESSED' }
            ]
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                processingStatus: [LaboratoryReportStatus.Processed, LaboratoryReportStatus.Unprocessed]
            })
        );
    });

    it('should transform with Created by', () => {
        const input: LabReportFilterEntry = {
            createdBy: { name: 'Created Name', label: 'Created Label', value: 'created-value' }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                createdBy: 'created-value'
            })
        );
    });

    it('should transform with Last updated by', () => {
        const input: LabReportFilterEntry = {
            updatedBy: { name: 'Updated Name', label: 'Updated Label', value: 'updated-value' }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                lastUpdatedBy: 'updated-value'
            })
        );
    });

    it('should transform with Ordering facility', () => {
        const input: LabReportFilterEntry = {
            orderingFacility: {
                name: 'Ordering facility Name',
                label: 'Ordering facility Label',
                value: 'ordering-facility-value'
            }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                providerSearch: {
                    providerId: 'ordering-facility-value',
                    providerType: ProviderType.OrderingFacility
                }
            })
        );
    });

    it('should transform with Ordering provider', () => {
        const input: LabReportFilterEntry = {
            orderingProvider: {
                name: 'Ordering provider Name',
                label: 'Ordering provider Label',
                value: 'ordering-provider-value'
            }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                providerSearch: {
                    providerId: 'ordering-provider-value',
                    providerType: ProviderType.OrderingProvider
                }
            })
        );
    });

    it('should transform with Reporting facility', () => {
        const input: LabReportFilterEntry = {
            reportingFacility: {
                name: 'Reporting facility Name',
                label: 'Reporting facility Label',
                value: 'reporting-facility-value'
            }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                providerSearch: {
                    providerId: 'reporting-facility-value',
                    providerType: ProviderType.ReportingFacility
                }
            })
        );
    });
});

describe('when the Laboratory Seach Criteria contains Lab Report Criteria', () => {
    it('should transform with a coded result', () => {
        const input: LabReportFilterEntry = {
            codedResult: 'coded-value'
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                codedResult: 'coded-value'
            })
        );
    });

    it('should transform with a resulted test', () => {
        const input: LabReportFilterEntry = {
            resultedTest: 'resulted-value'
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                resultedTest: 'resulted-value'
            })
        );
    });
});
