import { LaboratoryEventIdType, LaboratoryReportEventDateType } from 'generated/graphql/schema';
import { FormLabReportFilter } from './labReportFormTypes';
import { transformObject } from './transformer';

describe('transformObject', () => {
    it('should transform an object with Selectable arrays correctly', () => {
        const input: FormLabReportFilter = {
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

    it('should handle an empty object', () => {
        const input = {};

        const expected = {};

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });
});
