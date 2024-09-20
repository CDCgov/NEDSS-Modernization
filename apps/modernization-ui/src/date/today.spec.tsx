import { internalizeDate } from './InternalizeDate';
import { today } from './today';

describe('today', () => {
    it('should return string of current date', () => {
        const expected = internalizeDate(new Date());
        expect(today()).toEqual(expected);
    });
});
