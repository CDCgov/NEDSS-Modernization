import { transformObject } from './transformer';

describe('transformObject', () => {
    it('should transform an object with Selectable arrays correctly', () => {
        const input = {
            codedResult: [{ value: 'result1', name: 'Result 1' }],
            eventStatus: [{ value: 'status1', name: 'Status 1' }]
        };

        const expected = {
            codedResult: ['result1'],
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

        const expected = {
            eventDate: {
                type: 'type1',
                from: '2023-05-01',
                to: '2023-05-31'
            }
        };

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });

    it('should handle an empty object', () => {
        const input = {};

        const expected = {};

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });
});
