import { CaseStatus, InvestigationEventIdType, InvestigationStatus, PregnancyStatus } from 'generated/graphql/schema';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { transformObject } from './transformer';

describe('transformObject', () => {
    it('should transform an object with Selectable arrays correctly', () => {
        const input: InvestigationFilterEntry = {
            createdBy: { value: 'result-values', name: 'result-name', label: 'result-label' },
            conditions: [{ value: 'status1', name: 'Status 1', label: 'Status 1' }]
        };

        const expected = {
            createdBy: 'result-values',
            conditions: ['status1']
        };

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });

    it('should transform an object with string values correctly', () => {
        const input = {
            eventId: {
                id: 'test1',
                investigationEventType: InvestigationEventIdType.InvestigationId
            }
        };

        const expected = {
            eventId: {
                id: 'test1',
                investigationEventType: InvestigationEventIdType.InvestigationId
            }
        };

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });

    it('should transform an object with nested objects correctly', () => {
        const input = {
            eventId: {
                id: 'test1',
                investigationEventType: InvestigationEventIdType.CityCountyCaseId
            }
        };

        const result = transformObject(input);

        expect(result).toEqual(
            expect.objectContaining({
                eventId: {
                    id: 'test1',
                    investigationEventType: InvestigationEventIdType.CityCountyCaseId
                }
            })
        );
    });

    it('should include investigation status when present', () => {
        const criteria = {
            investigationStatus: 'OPEN' as InvestigationStatus
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                investigationStatus: InvestigationStatus.Open
            })
        );
    });

    it('should include case statuses when present', () => {
        const criteria = {
            caseStatuses: [{ name: 'Confirmed', label: 'Confirmed', value: 'CONFIRMED' }]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                caseStatuses: expect.arrayContaining([CaseStatus.Confirmed])
            })
        );
    });

    it('should include pregnancy status when present', () => {
        const criteria = {
            pregnancyStatus: { name: 'Unknown', label: 'Unknown', value: 'UNKNOWN' }
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                pregnancyStatus: PregnancyStatus.Unknown
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
