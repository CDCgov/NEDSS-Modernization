import { render } from '@testing-library/react';
import { MatchingCriteria } from './MatchingCriteria';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';
import { BlockingAttribute, MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';

const onAddAttributes = jest.fn();
const Fixture = () => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [],
            matchingCriteria: [],
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <MatchingCriteria dataElements={{}} onAddAttributes={onAddAttributes} />
        </FormProvider>
    );
};

const WithContent = () => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [BlockingAttribute.FIRST_NAME],
            matchingCriteria: [{ attribute: MatchingAttribute.LAST_NAME, method: MatchMethod.NONE }],
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <MatchingCriteria dataElements={{}} onAddAttributes={onAddAttributes} />
        </FormProvider>
    );
};

describe('BlockingCriteria', () => {
    it('should display header', () => {
        const { getByRole } = render(<Fixture />);

        expect(getByRole('heading')).toHaveTextContent('2. Matching criteria');
    });

    it('should display description', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Include records that meet all these conditions')).toBeInTheDocument();
    });

    it('should display help text when no criteria are specified', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Please add matching criteria to continue.')).toBeInTheDocument();
    });

    it('should not display help text when criteria are specified', () => {
        const { queryByText } = render(<WithContent />);

        expect(queryByText('Please add matching criteria to continue.')).not.toBeInTheDocument();
        expect(queryByText('Last name')).toBeInTheDocument();
    });

    it('should have a button with proper label', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('button')).toHaveTextContent('Add matching attribute(s)');
    });

    it('should trigger onAddAttributes when button is clicked', () => {
        const { getAllByRole } = render(<WithContent />);
        const buttons = getAllByRole('button');
        userEvent.click(buttons[1]); // Add matching attribute(s) button

        expect(onAddAttributes).toHaveBeenCalledTimes(1);
    });

    it('should be disabled when no blocking attributes are selected', () => {
        const { getByRole } = render(<Fixture />);
        const button = getByRole('button');
        expect(button).toBeDisabled();
    });
});
