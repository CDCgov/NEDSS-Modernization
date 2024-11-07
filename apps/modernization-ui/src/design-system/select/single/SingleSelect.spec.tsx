import React, { useState } from 'react';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { SingleSelect } from './SingleSelect';
import { Selectable } from 'options/selectable';

describe('when selecting a single item from a specific set of items', () => {
    it('should display the SingleSelect without a value checked', () => {
        const { queryByRole } = render(
            <SingleSelect
                id="test-id"
                label="Test Label"
                options={[
                    { name: 'name-one', value: 'value-one', label: 'label-one' },
                    { name: 'name-two', value: 'value-two', label: 'label-two' },
                    { name: 'name-three', value: 'value-three', label: 'label-three' },
                    { name: 'name-four', value: 'value-four', label: 'label-four' }
                ]}
                name="test-name"
                placeholder="place-holder-value"
            />
        );

        const checked = queryByRole('option', { selected: true });

        expect(checked).toHaveTextContent('place-holder-value');
    });

    it('should display the SingleSelect with the value checked', () => {
        const { getByRole } = render(
            <SingleSelect
                id="test-id"
                label="Test Label"
                options={[
                    { name: 'name-one', value: 'value-one', label: 'label-one' },
                    { name: 'name-two', value: 'value-two', label: 'label-two' },
                    { name: 'name-three', value: 'value-three', label: 'label-three' },
                    { name: 'name-four', value: 'value-four', label: 'label-four' }
                ]}
                name="test-name"
                value={{ name: 'name-three', value: 'value-three', label: 'label-three' }}
            />
        );

        const checked = getByRole('option', { name: 'name-three', selected: true });

        expect(checked).toHaveTextContent('name-three');
    });
});

describe('when one of the options is clicked', () => {
    it('should mark the option as checked', () => {
        const TestComponent = () => {
            const [selectedValue, setSelectedValue] = useState<Selectable | null>(null);

            return (
                <SingleSelect
                    id="test-id"
                    label="Test Label"
                    options={[
                        { name: 'name-one', value: 'value-one', label: 'label-one' },
                        { name: 'name-two', value: 'value-two', label: 'label-two' },
                        { name: 'name-three', value: 'value-three', label: 'label-three' },
                        { name: 'name-four', value: 'value-four', label: 'label-four' }
                    ]}
                    name="test-name"
                    value={selectedValue}
                    onChange={(value?: Selectable) => setSelectedValue(value || null)}
                />
            );
        };

        const { getByRole } = render(<TestComponent />);

        const select = getByRole('combobox', { name: 'Test Label' });

        userEvent.selectOptions(select, ['value-four']);

        expect(select).toHaveValue('value-four');
    });
});
