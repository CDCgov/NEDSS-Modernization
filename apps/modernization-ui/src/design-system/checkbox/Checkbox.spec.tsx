import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Checkbox } from './Checkbox';
import { Selectable } from 'options';

const onChange = jest.fn();
const option: Selectable = { value: 'value', label: 'label', name: 'name' };

describe('Checkbox testing', () => {
    it('should render the selectable label as the checkbox label', () => {
        const { getByText } = render(<Checkbox selectable={option} selected={false} />);

        const label = getByText('label');

        expect(label).toBeInTheDocument();
    });

    it('should render unchecked', () => {
        const { getByRole } = render(<Checkbox onChange={onChange} selectable={option} selected={false} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).not.toBeChecked();
    });

    it('should render checked', () => {
        const { getByRole } = render(<Checkbox onChange={onChange} selectable={option} selected={true} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).toBeChecked();
    });

    it('should render enabled', () => {
        const { getByRole } = render(<Checkbox onChange={onChange} selectable={option} selected={true} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).not.toBeDisabled();
    });

    it('should render disabled', () => {
        const { getByRole } = render(<Checkbox onChange={onChange} selectable={option} selected={true} disabled />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).toBeDisabled();
    });

    it('should render with provided className', () => {
        const { container } = render(
            <Checkbox onChange={onChange} selectable={option} selected={true} className="testClass" />
        );

        expect(container.firstChild).toHaveClass('testClass');
    });

    it('should emit onChange event when checkbox clicked', () => {
        const onChange = jest.fn();
        const { getByRole } = render(<Checkbox onChange={onChange} selectable={option} selected={false} />);

        const checkbox = getByRole('checkbox');

        userEvent.click(checkbox);
        expect(onChange).toHaveBeenCalledWith(option);
    });

    it('should emit onChange event when label clicked', () => {
        const onChange = jest.fn();

        const { getByText } = render(<Checkbox onChange={onChange} selectable={option} selected={false} />);

        const label = getByText('label');

        userEvent.click(label);

        expect(onChange).toHaveBeenCalledWith(option);
    });
});
