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
        const { getByText } = render(<Chip {...mockProps} />);

        const chipText = getByText(`${name}: ${value}`);
        expect(chipText).toBeInTheDocument();
    });

    it('calls handleClose when close icon is clicked', () => {
        const { handleClose } = mockProps;
        const { getByLabelText } = render(<Chip {...mockProps} />);

        const closeIcon = getByLabelText('Close chip');
        fireEvent.click(closeIcon);

        expect(handleClose).toHaveBeenCalled();
    });

    it('renders close icon', () => {
        const { getByLabelText } = render(<Chip {...mockProps} />);

        const closeIcon = getByLabelText('Close chip');
        expect(closeIcon).toBeInTheDocument();
    });
});
