import formattedName from 'formattedName';

describe('formattedName', () => {
    it('should format the name correctly with both last name and first name', () => {
        const formatted = formattedName('Doe', 'John');
        expect(formatted).toBe('Doe, John');
    });

    it('should format the name correctly with only last name', () => {
        const formatted = formattedName('Smith', '');
        expect(formatted).toBe('Smith');
    });

    it('should format the name correctly with only first name', () => {
        const formatted = formattedName('', 'Alice');
        expect(formatted).toBe('Alice');
    });

    it('should return "--" when both last name and first name are empty', () => {
        const formatted = formattedName('', '');
        expect(formatted).toBe('--');
    });
});
