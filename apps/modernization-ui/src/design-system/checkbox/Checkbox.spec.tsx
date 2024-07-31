import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Checkbox } from './Checkbox';

describe('Checkbox testing', () => {
    it('should render the selectable label as the checkbox label', () => {
        const { getByText } = render(<Checkbox id="test" label="Test Label" selected={false} />);

        const label = getByText('Test Label');

        expect(label).toBeInTheDocument();
    });

    it('should render unchecked', () => {
        const { getByRole } = render(<Checkbox id="test" label="Test Label" selected={false} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).not.toBeChecked();
    });

    it('should render checked', () => {
        const { getByRole } = render(<Checkbox id="test" label="Test Label" selected={true} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).toBeChecked();
    });

    it('should render enabled', () => {
        const { getByRole } = render(<Checkbox id="test" label="Test Label" selected={true} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).not.toBeDisabled();
    });

    it('should render disabled', () => {
        const { getByRole } = render(<Checkbox id="test" label="Test Label" selected={true} disabled />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).toBeDisabled();
    });

    it('should render with provided className', () => {
        const { container } = render(<Checkbox id="test" label="Test Label" selected={true} className="testClass" />);

        expect(container.firstChild).toHaveClass('testClass');
    });

    it('should emit onChange event when checkbox clicked', () => {
        const onChange = jest.fn();
        const { getByRole } = render(<Checkbox id="test" label="Test Label" onChange={onChange} selected={false} />);

        const checkbox = getByRole('checkbox');

        userEvent.click(checkbox);
        expect(onChange).toHaveBeenCalledWith(true);
    });

    it('should emit onChange event when label clicked', () => {
        const onChange = jest.fn();

        const { getByText } = render(<Checkbox id="test" label="Test Label" onChange={onChange} selected={false} />);

        const label = getByText('Test Label');

        userEvent.click(label);

        expect(onChange).toHaveBeenCalledWith(true);
    });
});
