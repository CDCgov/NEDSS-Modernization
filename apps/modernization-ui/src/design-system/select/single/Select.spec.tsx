import { render } from '@testing-library/react';
import Select from './Select';
import userEvent from '@testing-library/user-event';

describe('Select Component', () => {
    const options = [
        { name: 'name-one', value: 'value-one', label: 'label-one' },
        { name: 'name-two', value: 'value-two', label: 'label-two' },
        { name: 'name-three', value: 'value-three', label: 'label-three' },
        { name: 'name-four', value: 'value-four', label: 'label-four' }
    ];

    test('renders with placeholder', () => {
        const { getByText } = render(<Select id="test-select" options={options} placeholder="Select an option" />);
        expect(getByText('Select an option')).toBeInTheDocument();
    });

    test('renders options', () => {
        const { getByText } = render(<Select id="test-select" options={options} />);
        options.forEach((option) => {
            expect(getByText(option.name)).toBeInTheDocument();
        });
    });

    test('calls onChange with selected option', () => {
        const handleChange = jest.fn();
        const { getByRole } = render(
            <Select
                id="test-select"
                options={options}
                onChange={handleChange}
                value={{ name: 'name-four', value: 'value-four', label: 'label-four' }}
            />
        );
        const select = getByRole('combobox');
        userEvent.selectOptions(select, 'value-four');
        const checked = getByRole('option', { selected: true });

        expect(checked).toHaveTextContent('name-four');
    });

    test('applies className', () => {
        const { getByRole } = render(<Select id="test-select" options={options} className="custom-class" />);
        expect(getByRole('combobox')).toHaveClass('custom-class');
    });

    test('sets default value', () => {
        const { getByRole } = render(<Select id="test-select" options={options} value={options[1]} />);
        expect(getByRole('combobox')).toHaveValue('value-two');
    });
});
