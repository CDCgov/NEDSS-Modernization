import { selectField, toClockString } from './util';

describe('selectField', () => {
    it('should return the value at the given path', () => {
        const obj = { a: { b: { c: 42 } } };
        const path = 'a.b.c';
        const result = selectField(obj, path);
        expect(result).toBe(42);
    });

    it('should return undefined for an invalid path', () => {
        const obj = { a: { b: { c: 42 } } };
        const path = 'a.b.d';
        const result = selectField(obj, path);
        expect(result).toBeUndefined();
    });

    it('should return the object itself for an empty path', () => {
        const obj = { a: { b: { c: 42 } } };
        const path = '';
        const result = selectField(obj, path);
        expect(result).toBe(obj);
    });

    it('should return the nested object for a non-terminal path', () => {
        const obj = { a: { b: { c: 42 } } };
        const path = 'a.b';
        const result = selectField(obj, path);
        expect(result).toStrictEqual({ c: 42 });
    });

    it('should return undefined for an undefined object', () => {
        const obj = undefined;
        const path = 'a.b.c';
        const result = selectField(obj!, path);
        expect(result).toBeUndefined();
    });

    it('should handle paths with array indices', () => {
        const obj = { a: [{ b: 42 }] };
        const path = 'a.0.b';
        const result = selectField(obj, path);
        expect(result).toBe(42);
    });

    it('should return undefined for out of bounds array indices', () => {
        const obj = { a: [{ b: 42 }] };
        const path = 'a.1.b';
        const result = selectField(obj, path);
        expect(result).toBeUndefined();
    });
});

describe('toClockString', () => {
    it('should convert milliseconds to clock string', () => {
        expect(toClockString(0)).toBe('0:00');
        expect(toClockString(1000)).toBe('0:01');
        expect(toClockString(60000)).toBe('1:00');
        expect(toClockString(61000)).toBe('1:01');
        expect(toClockString(3600000)).toBe('1:00:00');
        expect(toClockString(3661000)).toBe('1:01:01');
        expect(toClockString(86400000)).toBe('24:00:00');
        expect(toClockString(86461000)).toBe('24:01:01');
    });
});
