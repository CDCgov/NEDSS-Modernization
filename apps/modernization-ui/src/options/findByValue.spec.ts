import { findByValue } from './findByValue';

describe('when searching for selectables by value', () => {
    it('should  return selectable when searching by known value', () => {
        const selectables = [
            { name: 'name-one', value: 'value-one', label: 'label-one' },
            { name: 'name-two', value: 'value-two', label: 'label-two' },
            { name: 'name-three', value: 'value-three', label: 'label-three' },
            { name: 'name-four', value: 'value-four', label: 'label-four' }
        ];

        const actual = findByValue(selectables)('value-three');

        expect(actual).toEqual(expect.objectContaining({ value: 'value-three' }));
    });

    it('should not return selectable when searching by unknown value', () => {
        const selectables = [
            { name: 'name-one', value: 'value-one', label: 'label-one' },
            { name: 'name-two', value: 'value-two', label: 'label-two' },
            { name: 'name-three', value: 'value-three', label: 'label-three' },
            { name: 'name-four', value: 'value-four', label: 'label-four' }
        ];

        const actual = findByValue(selectables)('value-unknown');

        expect(actual).toBeUndefined();
    });
});
