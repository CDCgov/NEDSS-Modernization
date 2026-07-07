import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { RadioGroup } from './RadioGroup';
import { axe } from 'jest-axe';

const mockOnChange = vi.fn();

const options = [
    { name: 'name-one', value: '1' },
    { name: 'name-two', value: '2' },
    { name: 'name-three', value: '3' },
];

describe('RadioGroup Component', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<RadioGroup id="testing" options={options} label="Group" />);
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the radio button with the options', () => {
        const { getByText } = render(<RadioGroup id="testing" options={options} label="Group" />);
        expect(getByText('Group')).toBeInTheDocument();
        expect(getByText('name-one')).toBeInTheDocument();
        expect(getByText('name-two')).toBeInTheDocument();
        expect(getByText('name-three')).toBeInTheDocument();
    });

    it('should call onChange handler when clicked', async () => {
        const { getByRole } = render(
            <RadioGroup id="testing" options={options} label="Group" onChange={mockOnChange} />
        );
        const user = userEvent.setup();

        const radioElement = getByRole('radio', { name: 'name-one' });
        expect(radioElement).not.toBeChecked();
        await user.click(radioElement);
        expect(mockOnChange).toHaveBeenCalledTimes(1);
        expect(mockOnChange).toHaveBeenCalledWith({ name: 'name-one', value: '1' });
    });
});
