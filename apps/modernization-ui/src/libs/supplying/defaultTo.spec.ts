import { defaultTo } from './defaultTo';

describe('defaultTo', () => {
    it('should return the provided value when present', () => {
        const actual = defaultTo('fallback-value')('actual-value');

        expect(actual).toBe('actual-value');
    });

    it('should return the provided value when present without resolving the fallback', () => {
        const fallback = jest.fn();

        const actual = defaultTo(fallback)('actual-value');

        expect(actual).toBe('actual-value');

        expect(fallback).not.toHaveBeenCalled();
    });

    it('should return the fallback value when no value is provided', () => {
        const actual = defaultTo('fallback-value')();

        expect(actual).toBe('fallback-value');
    });

    it('should return the fallback value when an undefined value is provided', () => {
        const actual = defaultTo('fallback-value')(undefined);

        expect(actual).toBe('fallback-value');
    });

    it('should return the resolved fallback value when no value is provided', () => {
        const actual = defaultTo(() => 'fallback-value')();

        expect(actual).toBe('fallback-value');
    });

    it('should return the resolved fallback value when an undefined value is provided', () => {
        const actual = defaultTo(() => 'fallback-value')(undefined);

        expect(actual).toBe('fallback-value');
    });
});
