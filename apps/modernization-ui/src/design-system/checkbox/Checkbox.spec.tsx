import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Checkbox } from './Checkbox';

const onChange = jest.fn();
const option = { value: 'value', label: 'label', name: 'name' };

describe('Checkbox testing', () => {
    it('should render unchecked', () => {
        const { getByLabelText } = render(<Checkbox onChange={onChange} option={option} selected={false} />);

        const checkbox = getByLabelText('label');
        expect(checkbox).not.toBeChecked();
    });

    it('should render checked', () => {
        const { getByLabelText } = render(<Checkbox onChange={onChange} option={option} selected={true} />);

        const checkbox = getByLabelText('label');
        expect(checkbox).toBeChecked();
    });

    it('should render enabled', () => {
        const { getByLabelText } = render(<Checkbox onChange={onChange} option={option} selected={true} />);

        const checkbox = getByLabelText('label');
        expect(checkbox).not.toBeDisabled();
    });

    it('should render disabled', () => {
        const { getByLabelText } = render(<Checkbox onChange={onChange} option={option} selected={true} disabled />);

        const checkbox = getByLabelText('label');
        expect(checkbox).toBeDisabled();
    });

    it('should render with provided className', () => {
        const { getByLabelText } = render(
            <Checkbox onChange={onChange} option={option} selected={true} className="testClass" />
        );

        const checkbox = getByLabelText('label');
        expect(checkbox.parentElement).toHaveClass('testClass');
    });

    it('should emit onChange event when checkbox clicked', () => {
        const { getByRole } = render(<Checkbox onChange={onChange} option={option} selected={false} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox.nodeName).toBe('INPUT');

        userEvent.click(checkbox);
        expect(onChange).toHaveBeenCalledTimes(1);

        const checked: boolean = onChange.mock.calls[0][0];
        expect(checked).toEqual(true);
    });

    it('should emit onChange event when label clicked', () => {
        const { getByText } = render(<Checkbox onChange={onChange} option={option} selected={false} />);

        const label = getByText('label');
        expect(label.nodeName).toBe('LABEL');
        expect(label).toHaveAttribute('for', 'checkbox-value');

        userEvent.click(label);
        expect(onChange).toHaveBeenCalledTimes(1);

        const checked: boolean = onChange.mock.calls[0][0];
        expect(checked).toEqual(true);
    });
});
