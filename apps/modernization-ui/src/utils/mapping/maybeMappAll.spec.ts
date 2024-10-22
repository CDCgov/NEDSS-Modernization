import { maybeMapAll } from './maybeMapAll';

type Testing = {
    value?: string;
};

describe('when mapping over an array that could result in undefined elements', () => {
    it('should map undefined to an empty array', () => {
        const mapping = jest.fn();

        const actual = maybeMapAll(mapping)(undefined);

        expect(actual).toEqual([]);

        expect(mapping).not.toBeCalled();
    });

    it('should map an empty array to an empty array', () => {
        const mapping = jest.fn();

        const actual = maybeMapAll(mapping)([]);

        expect(actual).toEqual([]);
        expect(mapping).not.toBeCalled();
    });

    it('should only include element that maps to defined values', () => {
        const items: Testing[] = [{ value: 'g' }, { value: undefined }, { value: 'h' }];

        const mapping = (item: Testing) => item.value;

        const actual = maybeMapAll(mapping)(items);

        expect(actual).toEqual(['g', 'h']);
    });
});
