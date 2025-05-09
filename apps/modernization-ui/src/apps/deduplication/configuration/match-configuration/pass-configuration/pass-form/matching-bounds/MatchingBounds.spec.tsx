import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BlockingAttribute, MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { FormProvider, useForm } from 'react-hook-form';
import { MatchingBounds } from './MatchingBounds';

const Fixture = () => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [BlockingAttribute.ADDRESS],
            matchingCriteria: [
                {
                    attribute: MatchingAttribute.FIRST_NAME,
                    method: MatchMethod.EXACT
                }
            ],
            lowerBound: undefined,
            upperBound: undefined,
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <MatchingBounds dataElements={{ firstName: { active: true, logOdds: 3 } }} />
        </FormProvider>
    );
};
describe('MatchingBounds', () => {
    it('should display the proper header', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('3. Matching bounds');
    });

    it('should display the subtext', () => {
        const { getByText } = render(<Fixture />);
        expect(
            getByText(
                'Records with log odds scores between the lower and upper bounds will present for review and resolution in the potential match queue'
            )
        ).toBeInTheDocument();
    });

    it('should display an input for Lower bound', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Lower bound')).toBeInTheDocument();
    });

    it('should display an input for Upper bound', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Upper bound')).toBeInTheDocument();
    });

    it('should require lower bound to be less than total log odds', async () => {
        const user = userEvent.setup();
        const { getByLabelText, findByText } = render(<Fixture />);

        const lowerBoundInput = getByLabelText('Lower bound');

        await user.type(lowerBoundInput, '5').then(() => user.tab());
        user.tab();

        expect(await findByText('Cannot be greater than total log odds.')).toBeInTheDocument();
    });

    it('should require lower bound to be less than upper bound', async () => {
        const user = userEvent.setup();
        const { getByLabelText, findByText } = render(<Fixture />);

        const lowerBoundInput = getByLabelText('Lower bound');
        const upperBoundInput = getByLabelText('Upper bound');

        await user.type(lowerBoundInput, '5').then(() => user.tab());
        await user.type(upperBoundInput, '4').then(() => user.tab());
        user.tab();
        user.tab();

        expect(await findByText('Cannot be greater than upper bound.')).toBeInTheDocument();
    });

    it('should require upper bound to be more than lower bound', async () => {
        const user = userEvent.setup();
        const { getByLabelText, findByText } = render(<Fixture />);

        const lowerBoundInput = getByLabelText('Lower bound');
        const upperBoundInput = getByLabelText('Upper bound');

        await user.type(lowerBoundInput, '2').then(() => user.tab());
        await user.type(upperBoundInput, '1').then(() => user.tab());
        user.tab();
        user.tab();

        expect(await findByText('Must be between lower bound and total log odds.')).toBeInTheDocument();
    });

    it('should require upper bound to be less than total log odds', async () => {
        const user = userEvent.setup();
        const { getByLabelText, findByText } = render(<Fixture />);

        const lowerBoundInput = getByLabelText('Lower bound');
        const upperBoundInput = getByLabelText('Upper bound');

        await user.type(lowerBoundInput, '1').then(() => user.tab());
        await user.type(upperBoundInput, '4').then(() => user.tab());
        user.tab();
        user.tab();

        expect(await findByText('Cannot be greater than total log odds.')).toBeInTheDocument();
    });

    it('should not show validation error when only one bound is present', async () => {
        const user = userEvent.setup();
        const { getByLabelText, queryByText } = render(<Fixture />);

        const lowerBoundInput = getByLabelText('Lower bound');

        await user.type(lowerBoundInput, '2').then(() => user.tab());

        // Should not trigger error about being greater than upper or total log odds
        expect(queryByText('Cannot be greater than upper bound.')).not.toBeInTheDocument();
        expect(queryByText('Cannot be greater than total log odds.')).not.toBeInTheDocument();
    });

    it('should calculate and display total log odds when matching criteria exists', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText(/Total log odds:/)).toHaveTextContent('Total log odds: 3');
    });
});
