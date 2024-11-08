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
        const SingleSelectTestWrapper = ({
            value,
            onChange
        }: {
            value: Selectable | null;
            onChange: (value?: Selectable) => void;
        }) => (
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
                value={value}
                onChange={onChange}
            />
        );
        let selectedValue = null;
        const handleChange = (value?: Selectable) => {
            selectedValue = value || null;
        };

        const { getByRole, rerender } = render(
            <SingleSelectTestWrapper value={selectedValue} onChange={handleChange} />
        );

        const select = getByRole('combobox', { name: 'Test Label' });

        userEvent.selectOptions(select, ['value-four']);

        rerender(<SingleSelectTestWrapper value={selectedValue} onChange={handleChange} />);

        const checked = getByRole('option', { name: 'name-four', selected: true });
        expect(checked).toHaveTextContent('name-four');
        expect(selectedValue).toEqual({ name: 'name-four', value: 'value-four', label: 'label-four' });
    });
});
