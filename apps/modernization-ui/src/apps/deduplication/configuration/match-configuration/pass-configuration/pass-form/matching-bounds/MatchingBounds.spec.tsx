import { render } from '@testing-library/react';
import { Pass } from 'apps/deduplication/api/model/Pass';
import { FormProvider, useForm } from 'react-hook-form';
import { MatchingBounds } from './MatchingBounds';

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
            <MatchingBounds dataElements={{}} />
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
});
