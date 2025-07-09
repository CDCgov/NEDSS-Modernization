import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import userEvent from '@testing-library/user-event';
import { SingleSelect } from './SingleSelect';

describe('when selecting a single item from a specific set of items', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <SingleSelect
                id="test-id"
                label="Test Label"
                options={[
                    { name: 'name-one', value: 'value-one', label: 'label-one' },
                    { name: 'name-two', value: 'value-two', label: 'label-two' },
                    { name: 'name-three', value: 'value-three', label: 'label-three' },
                    { name: 'name-four', value: 'value-four', label: 'label-four' }
                ]}
            />
        );

        expect(await axe(container)).toHaveNoViolations();
    });

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
                    { name: 'name-one', value: 'value-one' },
                    { name: 'name-two', value: 'value-two' },
                    { name: 'name-three', value: 'value-three' },
                    { name: 'name-four', value: 'value-four' }
                ]}
                value={{ name: 'name-three', value: 'value-three', label: 'label-three' }}
            />
        );

        const checked = getByRole('option', { name: 'name-three', selected: true });

        expect(checked).toHaveTextContent('name-three');
    });
});

describe('when one of the options is clicked', () => {
    it('should mark the option as checked', () => {
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
                value={{ name: 'name-four', value: 'value-four', label: 'label-four' }}
                onChange={jest.fn()}
            />
        );
        const select = getByRole('combobox', { name: 'Test Label' });

        userEvent.selectOptions(select, 'value-four');
        const checked = getByRole('option', { selected: true });

        expect(checked).toHaveTextContent('name-four');
    });
});

describe('when required is true', () => {
    it('should set required attribute when required is true', () => {
        const { getByRole } = render(
            <SingleSelect
                id="test"
                label="Test Label"
                options={[
                    { name: 'name-one', value: 'value-one', label: 'label-one' },
                    { name: 'name-two', value: 'value-two', label: 'label-two' }
                ]}
                required
            />
        );
        const select = getByRole('combobox', { name: 'Test Label' });
        expect(select).toHaveAttribute('required');
    });

    it('should set aria-required attribute when required is true', () => {
        const { getByRole } = render(
            <SingleSelect
                id="test"
                label="Test Label"
                options={[
                    { name: 'name-one', value: 'value-one', label: 'label-one' },
                    { name: 'name-two', value: 'value-two', label: 'label-two' }
                ]}
                required
            />
        );
        const select = getByRole('combobox', { name: 'Test Label' });
        expect(select).toHaveAttribute('aria-required', 'true');
    });
});
