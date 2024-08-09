import { render, fireEvent } from '@testing-library/react';
import Chip from './Chip';

describe('Chip', () => {
    const mockProps = {
        name: 'Test',
        value: 'Value',
        handleClose: jest.fn()
    };

    it('renders with correct name and value', () => {
        const { name, value } = mockProps;
        const { getByTestId } = render(<Chip {...mockProps} />);

        const chipElement = getByTestId(`${name}-chip`);
        expect(chipElement).toBeInTheDocument();
        expect(chipElement).toHaveTextContent(`${name}: ${value}`);
    });

    it('calls handleClose when close icon is clicked', () => {
        const { handleClose } = mockProps;
        const { getByTestId } = render(<Chip {...mockProps} />);

        const closeIcon = getByTestId('close-icon');
        fireEvent.click(closeIcon);

        expect(handleClose).toHaveBeenCalled();
    });

    it('applies correct CSS classes', () => {
        const { name } = mockProps;
        const { container, getByTestId } = render(<Chip {...mockProps} />);

        const chipContainer = getByTestId(`${name}-chip`);
        expect(chipContainer).toHaveClass('chip-container');

        const nameSpan = container.querySelector('span');
        expect(nameSpan).toHaveClass('name');

        const closeIcon = getByTestId('close-icon');
        expect(closeIcon).toHaveClass('closeIcon');
    });

    it('renders close icon', () => {
        const { getByTestId } = render(<Chip {...mockProps} />);

        const closeIcon = getByTestId('close-icon');
        expect(closeIcon).toBeInTheDocument();
    });
});
