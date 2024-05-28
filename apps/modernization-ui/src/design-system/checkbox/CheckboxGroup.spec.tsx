import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CheckboxGroup } from './CheckboxGroup';

const options = [
    { value: 'value1', label: 'label1', name: 'name1' },
    { value: 'value2', label: 'label2', name: 'name2' }
];

const initialSelection = ['value1'];

const onChange = jest.fn();

describe('CheckboxGroup', () => {
    it('should render with options', () => {
        const { getByLabelText } = render(<CheckboxGroup label="checkbox group label" options={options} />);

        const checkbox1 = getByLabelText('label1');
        expect(checkbox1).toBeInTheDocument();
        expect(checkbox1).not.toBeChecked();
        expect(checkbox1).not.toBeDisabled();

        const checkbox2 = getByLabelText('label2');
        expect(checkbox2).toBeInTheDocument();
        expect(checkbox2).not.toBeChecked();
        expect(checkbox1).not.toBeDisabled();
    });

    it('should render with label', () => {
        const { getByText } = render(<CheckboxGroup label="checkbox group label" options={options} />);
        const label = getByText('checkbox group label');
        expect(label).toBeInTheDocument();
    });

    it('should render options with initial selection', () => {
        const { getByLabelText } = render(<CheckboxGroup options={options} initialSelection={initialSelection} />);

        const checkbox1 = getByLabelText('label1');
        expect(checkbox1).toBeInTheDocument();
        expect(checkbox1).toBeChecked();

        const checkbox2 = getByLabelText('label2');
        expect(checkbox2).toBeInTheDocument();
        expect(checkbox2).not.toBeChecked();
    });

    it('should check option when clicked', () => {
        const { getByLabelText } = render(<CheckboxGroup options={options} />);

        const checkbox1 = getByLabelText('label1');
        expect(checkbox1).not.toBeChecked();

        const checkbox2 = getByLabelText('label2');
        expect(checkbox2).not.toBeChecked();

        userEvent.click(checkbox1);
        expect(checkbox1).toBeChecked();
        expect(checkbox2).not.toBeChecked();

        userEvent.click(checkbox2);
        expect(checkbox1).toBeChecked();
        expect(checkbox2).toBeChecked();
    });

    it('should emit onChange when clicked', () => {
        const { getByLabelText } = render(<CheckboxGroup options={options} onChange={onChange} />);

        const checkbox1 = getByLabelText('label1');
        const checkbox2 = getByLabelText('label2');

        userEvent.click(checkbox1);
        expect(onChange).toHaveBeenCalledTimes(1);
        let emittedValue = onChange.mock.calls[0][0];
        expect(emittedValue).toHaveLength(1);
        expect(emittedValue[0]).toEqual('value1');

        userEvent.click(checkbox2);
        expect(onChange).toHaveBeenCalledTimes(2);
        emittedValue = onChange.mock.calls[1][0];
        expect(emittedValue).toHaveLength(2);
        expect(emittedValue[0]).toEqual('value1');
        expect(emittedValue[1]).toEqual('value2');
    });

    it('should disable checkboxes', () => {
        const { getByLabelText } = render(<CheckboxGroup options={options} disabled />);

        const checkbox1 = getByLabelText('label1');
        expect(checkbox1).toBeDisabled();

        const checkbox2 = getByLabelText('label2');
        expect(checkbox2).toBeDisabled();
    });

    it('should render with specified className', () => {
        const { getByText } = render(
            <CheckboxGroup className="customClass" label="checkbox group label" options={options} disabled />
        );

        const label = getByText('checkbox group label');
        expect(label.parentElement).toHaveClass('customClass');
    });
});
