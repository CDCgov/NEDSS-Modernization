import { maybeMap } from './maybeMap';

describe('maybeMap', () => {
    it.each(['value', 0, true, false])('should call the mapping function when %s is given', (value) => {
        const mapping = jest.fn();

        maybeMap(mapping)(value);

        expect(mapping).toHaveBeenCalledWith(value);
    });

    it.each([undefined, [], null])('should not call the mapping function when %s is given', (value) => {
        const mapping = jest.fn();

        maybeMap(mapping)(value);

        expect(mapping).not.toHaveBeenCalled();
    });
});
