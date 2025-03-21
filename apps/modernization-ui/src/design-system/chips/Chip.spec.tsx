import { render } from '@testing-library/react';
import { Chip } from './Chip';
import userEvent from '@testing-library/user-event';

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

    it('calls handleClose when close icon is clicked', async () => {
        const user = userEvent.setup();

        const { handleClose } = mockProps;
        const { getByLabelText } = render(<Chip {...mockProps} />);

        const closeIcon = getByLabelText('Close chip');
        await user.click(closeIcon);

        expect(handleClose).toHaveBeenCalled();
    });

    it('renders close icon', () => {
        const { getByLabelText } = render(<Chip {...mockProps} />);

        const closeIcon = getByLabelText('Close chip');
        expect(closeIcon).toBeInTheDocument();
    });
});
