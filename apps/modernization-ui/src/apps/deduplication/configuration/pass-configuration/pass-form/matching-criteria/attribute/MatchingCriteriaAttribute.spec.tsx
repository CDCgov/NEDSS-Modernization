import { BlockingAttribute, MatchingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { MatchingCriteriaAttribute } from './MatchingCriteriaAttribute';
import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';

const onRemove = jest.fn();
const Fixture = () => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [BlockingAttribute.LAST_NAME],
            matchingCriteria: [{ attribute: MatchingAttribute.FIRST_NAME }],
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <MatchingCriteriaAttribute
                label="First name"
                attribute={MatchingAttribute.FIRST_NAME}
                logOdds={2.3938}
                onRemove={onRemove}
            />
            <MatchingCriteriaAttribute
                label="Last name"
                attribute={MatchingAttribute.LAST_NAME}
                logOdds={2.3938}
                onRemove={onRemove}
            />
        </FormProvider>
    );
};
describe('MatchingCriteriaAttribute', () => {
    it('should display the proper label', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('First name')).toBeInTheDocument();
    });

    it('should not display criteria that is not selected', () => {
        const { queryByText } = render(<Fixture />);

        expect(queryByText('Last name')).not.toBeInTheDocument();
    });

    it('should display the proper log odds', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Log odds:')).toBeInTheDocument();
        expect(getByText('2.3938')).toBeInTheDocument();
    });

    it('should remove when delete button clicked', () => {
        const { getByRole } = render(<Fixture />);

        const deleteButton = getByRole('button');
        expect(deleteButton).toBeInTheDocument();
        userEvent.click(deleteButton);
        expect(onRemove).toHaveBeenCalledWith(MatchingAttribute.FIRST_NAME);
    });
});
