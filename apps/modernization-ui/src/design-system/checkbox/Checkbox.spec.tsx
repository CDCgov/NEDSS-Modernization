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
        const { container } = render(<Checkbox id="test" label="Test Label" selected={false} />);

        const checkbox = container.firstChild;
        expect(checkbox).toHaveAttribute('aria-checked', 'false');
    });

    it('should render checked', () => {
        const { container } = render(<Checkbox id="test" label="Test Label" selected={true} />);

        const checkbox = container.firstChild;
        expect(checkbox).toHaveAttribute('aria-checked', 'true');
    });

    it('should render enabled', () => {
        const { getByText } = render(<Checkbox id="test" label="Test Label" selected={true} />);

        const checkbox = getByText('Test Label');
        expect(checkbox).not.toBeDisabled();
    });

    it('should render with provided className', () => {
        const { container } = render(<Checkbox id="test" label="Test Label" selected={true} className="testClass" />);

        expect(container.firstChild).toHaveClass('testClass');
    });

    it('should emit onChange event when label clicked', async () => {
        const user = userEvent.setup();
        const onChange = jest.fn();

        const { getByText } = render(<Checkbox id="test" label="Test Label" onChange={onChange} selected={false} />);

        const label = getByText('Test Label');

        await user.click(label);

        expect(onChange).toHaveBeenCalledWith(true);
    });
});
