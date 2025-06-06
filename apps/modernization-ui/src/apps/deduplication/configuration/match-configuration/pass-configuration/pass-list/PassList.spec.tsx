import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Pass } from 'apps/deduplication/api/model/Pass';
import { PassList } from './PassList';
import { AlertProvider } from 'alert';

const selectPass = jest.fn();
const addPass = jest.fn();
const onRenamePass = jest.fn();
const passes: Pass[] = [
    {
        name: 'Pass name 1',
        description: 'Pass description 1',
        blockingCriteria: [],
        matchingCriteria: [],
        lowerBound: 0.25,
        upperBound: 1,
        active: true
    },
    {
        name: 'Pass name 2',
        description: 'Pass description 2',
        blockingCriteria: [],
        matchingCriteria: [],
        lowerBound: 0.25,
        upperBound: 1,
        active: false
    }
];
const Fixture = ({ passList = passes }) => {
    return (
        <AlertProvider>
            <PassList
                passes={passList}
                onRenamePass={onRenamePass}
                onSetSelectedPass={selectPass}
                onAddPass={addPass}
            />
        </AlertProvider>
    );
};

describe('PassList', () => {
    // Display
    it('should display heading', () => {
        const { getByRole } = render(<Fixture />);

        expect(getByRole('heading')).toHaveTextContent('Pass configurations');
    });

    it('should display add pass button', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Add pass configuration')).toBeInTheDocument();
    });

    it('should display list of passes', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Pass name 1')).toBeInTheDocument();
        expect(getByText('Pass description 1')).toBeInTheDocument();
        expect(getByText('Active')).toBeInTheDocument();

        expect(getByText('Pass name 2')).toBeInTheDocument();
        expect(getByText('Pass description 2')).toBeInTheDocument();
        expect(getByText('Inactive')).toBeInTheDocument();
    });

    it('should display no pass message if no passes exist ', () => {
        const { getByText } = render(<Fixture passList={[]} />);

        expect(getByText('No pass configurations have been created.')).toBeInTheDocument();
    });

    // Actions
    it('should call Select pass when pass name is clicked', async () => {
        const { getByRole } = render(<Fixture />);

        const user = userEvent.setup();

        const selectPassButton = getByRole('button', { name: 'Select Pass name 1' });

        await user.click(selectPassButton);

        expect(selectPass).toHaveBeenCalledWith(passes[0]);
    });

    it('should call Add pass when Add pass button is clicked', async () => {
        const { getByText } = render(<Fixture />);

        const user = userEvent.setup();

        const addPassButton = getByText('Add pass configuration');

        await user.click(addPassButton);

        expect(addPass).toHaveBeenCalledTimes(1);
    });
});
