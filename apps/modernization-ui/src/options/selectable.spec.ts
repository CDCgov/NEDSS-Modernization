import { asValue, asValues } from './selectable';

describe('when getting the value of a Selectable', () => {
    it('should return the value of the selectable', () => {
        const actual = asValue({ name: 'name', value: 'value' });

        expect(actual).toEqual('value');
    });

    it('should return null when given null', () => {
        const actual = asValue(null);

        expect(actual).toBeNull();
    });

    it('should return null when given undefined', () => {
        const actual = asValue(undefined);

        expect(actual).toBeNull();
    });
});

describe('when getting the value of multiple Selectables', () => {
    it('should return the values of each Selectable', () => {
        const actual = asValues([
            { name: 'name-one', value: 'value-one' },
            { name: 'name-two', value: 'value-two' },
            { name: 'name-three', value: 'value-three' },
            { name: 'name-four', value: 'value-four' }
        ]);

        expect(actual).toEqual(expect.arrayContaining(['value-one', 'value-two', 'value-three', 'value-four']));
    });
});
