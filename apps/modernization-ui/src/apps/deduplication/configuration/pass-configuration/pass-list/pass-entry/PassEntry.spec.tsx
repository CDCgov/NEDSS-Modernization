import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Pass } from 'apps/deduplication/api/model/Pass';
import { PassEntry } from './PassEntry';

const selectPass = jest.fn();
const editName = jest.fn();
const pass: Pass = {
    name: 'Pass name',
    description: 'Pass description',
    blockingCriteria: [],
    matchingCriteria: [],
    lowerBound: 0.25,
    upperBound: 1,
    active: true
};
const Fixture = ({ status = true }) => {
    return (
        <PassEntry
            pass={{ ...pass, active: status }}
            isSelected={false}
            onSelectPass={selectPass}
            onEditName={editName}
        />
    );
};

describe('PassEntry', () => {
    // Display
    it('should display pass name', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Pass name')).toBeInTheDocument();
    });

    it('should display pass description', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Pass description')).toBeInTheDocument();
    });

    it('should display edit name button', () => {
        const { getAllByRole } = render(<Fixture />);
        const editButton = getAllByRole('button')[1];
        expect(editButton).toBeInTheDocument();
        expect(editButton.childNodes[0]).toHaveClass('icon');
    });

    it('should Active status', () => {
        const { getByText } = render(<Fixture />);
        const statusBadge = getByText('Active');

        expect(statusBadge).toBeInTheDocument();
        expect(statusBadge).toHaveClass('active');
    });

    it('should Inactive status', () => {
        const { getByText } = render(<Fixture status={false} />);
        const statusBadge = getByText('Inactive');

        expect(statusBadge).toBeInTheDocument();
        expect(statusBadge).toHaveClass('inactive');
    });

    // Actions

    it('should select pass when name is clicked', async () => {
        const { getByText } = render(<Fixture />);

        const user = userEvent.setup();

        await user.click(getByText('Pass name'));

        expect(selectPass).toHaveBeenCalledWith(pass);
    });

    it('should call edit name when edit button is clicked', async () => {
        const { getByRole } = render(<Fixture />);

        const user = userEvent.setup();

        const editButton = getByRole('button', { name: 'Edit Pass name' });

        await user.click(editButton);

        expect(editName).toHaveBeenCalledWith(pass);
    });
});
