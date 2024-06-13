import { transformObject } from './transformer';

describe('transformObject', () => {
    it('should transform an object with Selectable arrays correctly', () => {
        const input = {
            codedResult: [{ value: 'result-values', name: 'result-name', label: 'result-label' }],
            eventStatus: [{ value: 'status1', name: 'Status 1', label: 'Status label' }]
        };

        const expected = {
            codedResult: ['result-values'],
            eventStatus: ['status1']
        };

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });

    it('should transform an object with string values correctly', () => {
        const input = {
            eventId: {
                labEventType: 'type1',
                labEventId: 'id1'
            }
        };

        const expected = {
            eventId: {
                labEventType: 'type1',
                labEventId: 'id1'
            }
        };

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });

    it('should transform an object with nested objects correctly', () => {
        const input = {
            eventDate: {
                type: 'type1',
                from: '2023-05-01',
                to: '2023-05-31'
            }
        };

        const result = transformObject(input);

        expect(result).toEqual(
            expect.objectContaining({
                eventDate: {
                    type: 'type1',
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
