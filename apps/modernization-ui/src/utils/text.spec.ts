import { pluralize, splitStringByCommonDelimiters, trimCommonDelimiters } from './text';

describe('splitStringByCommonDelimiters', () => {
    it('should split a string by commas', () => {
        const result = splitStringByCommonDelimiters('a,b,c');
        expect(result).toEqual(['a', 'b', 'c']);
    });

    it('should split a string by semicolons', () => {
        const result = splitStringByCommonDelimiters('a;b;c');
        expect(result).toEqual(['a', 'b', 'c']);
    });

    it('should split a string by whitespace', () => {
        const result = splitStringByCommonDelimiters('a b c');
        expect(result).toEqual(['a', 'b', 'c']);
    });

    it('should split a string by mixed delimiters', () => {
        const result = splitStringByCommonDelimiters('a, b; c');
        expect(result).toEqual(['a', 'b', 'c']);
    });

    it('should handle leading and trailing whitespace', () => {
        const result = splitStringByCommonDelimiters('  a, b; c  ');
        expect(result).toEqual(['a', 'b', 'c']);
    });

    it('should handle empty string', () => {
        const result = splitStringByCommonDelimiters('');
        expect(result).toHaveLength(0);
    });

    it('should handle string with only delimiters', () => {
        const result = splitStringByCommonDelimiters(', ; ');
        expect(result).toHaveLength(0);
    });
});

describe('trimCommonDelimiters', () => {
    it('should remove leading and trailing common delimiters', () => {
        const result = trimCommonDelimiters(',; a, b; c; ');
        expect(result).toEqual('a, b; c');
    });

    it('should handle empty string', () => {
        const result = trimCommonDelimiters('');
        expect(result).toEqual('');
    });

    it('should handle string with only delimiters', () => {
        const result = trimCommonDelimiters(', ; ');
        expect(result).toEqual('');
    });
});

describe('pluralize', () => {
    it('should return singular form for count of 1', () => {
        const result = pluralize('item', 1);
        expect(result).toEqual('item');
    });

    it('should return plural form for count greater than 1', () => {
        const result = pluralize('item', 2);
        expect(result).toEqual('items');
    });

    it('should return custom plural form', () => {
        const result = pluralize('person', 2, 'people');
        expect(result).toEqual('people');
    });
});
