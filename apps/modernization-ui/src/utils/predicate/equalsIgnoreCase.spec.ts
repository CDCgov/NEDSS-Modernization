import { equalsIgnoreCase } from './equalsIgnoreCase';

describe('equalsIgnoreCase', () => {
    it('should return true when the string values are equal', () => {
        const actual = equalsIgnoreCase('value')('value');

        expect(actual).toBe(true);
    });

    it('should return true when the string values are equal with differing cases', () => {
        const actual = equalsIgnoreCase('ValUe')('vAluE');

        expect(actual).toBe(true);
    });

    it('should return false when the string values are not equal', () => {
        const actual = equalsIgnoreCase('criteria')('value');

        expect(actual).toBe(false);
    });
});
