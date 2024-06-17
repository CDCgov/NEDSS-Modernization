import {
    EntryMethod,
    EventStatus,
    LaboratoryEventIdType,
    LaboratoryReportEventDateType,
    LaboratoryReportStatus,
    PregnancyStatus,
    UserType
} from 'generated/graphql/schema';
import { LabReportFilterEntry } from './labReportFormTypes';
import { transformObject } from './transformer';

describe('transformObject', () => {
    it('should transform an object with Selectable arrays correctly', () => {
        const input: LabReportFilterEntry = {
            codedResult: { value: 'result-values', name: 'result-name', label: 'result-name' },
            eventStatus: [{ value: 'status1', name: 'Status 1', label: 'Status 1' }]
        };

        const expected = {
            codedResult: 'result-values',
            eventStatus: ['status1']
        };

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });

    it('should transform an object with string values correctly', () => {
        const input = {
            eventId: {
                labEventId: 'type1',
                labEventType: LaboratoryEventIdType.AccessionNumber
            }
        };

        const expected = {
            eventId: {
                labEventId: 'type1',
                labEventType: LaboratoryEventIdType.AccessionNumber
            }
        };

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });

    it('should transform an object with nested objects correctly', () => {
        const input = {
            eventDate: {
                type: LaboratoryReportEventDateType.DateOfReport,
                from: '2023-05-01',
                to: '2023-05-31'
            }
        };

        const result = transformObject(input);

        expect(result).toEqual(
            expect.objectContaining({
                eventDate: {
                    type: LaboratoryReportEventDateType.DateOfReport,
                    from: '2023-05-01',
                    to: '2023-05-31'
                }
            })
        );
    });

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

    it('should include entry method when present', () => {
        const criteria = {
            enteredBy: [{ name: 'Manual', label: 'Manual', value: 'ELECTRONIC' }]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                enteredBy: expect.arrayContaining([EntryMethod.Electronic])
            })
        );
    });

    it('should include event status when present', () => {
        const criteria = {
            enteredBy: [{ name: 'Update', label: 'Update', value: 'UPDATE' }]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                enteredBy: expect.arrayContaining([EventStatus.Update])
            })
        );
    });

    it('should include pregnancy status when present', () => {
        const criteria = {
            enteredBy: [{ name: 'Unknown', label: 'Unknown', value: 'UNKNOWN' }]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                enteredBy: expect.arrayContaining([PregnancyStatus.Unknown])
            })
        );
    });

    it('should include processing status when present', () => {
        const criteria = {
            enteredBy: [{ name: 'Unprocessed', label: 'Unprocessed', value: 'UNPROCESSED' }]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                enteredBy: expect.arrayContaining([LaboratoryReportStatus.Unprocessed])
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
