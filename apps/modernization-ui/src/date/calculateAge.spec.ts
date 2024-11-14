import { calculateAge } from './calculateAge';

describe('given a value', () => {
    it('should calculate the age when given a string', () => {
        const actual = calculateAge('1984-09-01', new Date('2023-01-01'));

        expect(actual).toEqual(38);
    });

    it('should calculate the age when given a date', () => {
        const actual = calculateAge(new Date('1984-09-01'), new Date('2023-01-01'));

        expect(actual).toEqual(38);
    });
});

describe('given a value', () => {
    it('should not calculate the age when given a null value', () => {
        const actual = calculateAge(null);

        expect(actual).toBeUndefined();
    });

    it('should not calculate the age when given an undefined value', () => {
        const actual = calculateAge(undefined);

        expect(actual).toBeUndefined();
    });
});
