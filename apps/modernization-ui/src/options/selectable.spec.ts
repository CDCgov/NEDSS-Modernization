import { asValue, asValues } from './selectable';

describe('when getting the value of a Selectable', () => {
    it('should return the value of the selectable', () => {
        const actual = asValue({ name: 'name', value: 'value', label: 'label' });

        expect(actual).toEqual('value');
    });

    it('should return undefined when given null', () => {
        const actual = asValue(null);

        expect(actual).toBeUndefined();
    });

    it('should return undefined when given undefined', () => {
        const actual = asValue(undefined);

        expect(actual).toBeUndefined();
    });
});

describe('when getting the value of multiple Selectables', () => {
    it('should return the values of each Selectable', () => {
        const actual = asValues([
            { name: 'name-one', value: 'value-one', label: 'label-one' },
            { name: 'name-two', value: 'value-two', label: 'label-two' },
            { name: 'name-three', value: 'value-three', label: 'label-three' },
            { name: 'name-four', value: 'value-four', label: 'label-four' }
        ]);

        expect(actual).toEqual(expect.arrayContaining(['value-one', 'value-two', 'value-three', 'value-four']));
    });
});
