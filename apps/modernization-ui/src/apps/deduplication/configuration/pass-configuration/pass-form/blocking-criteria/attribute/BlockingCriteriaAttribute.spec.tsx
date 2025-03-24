import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { FormProvider, useForm } from 'react-hook-form';
import { BlockingCriteriaAttribute } from './BlockingCriteriaAttribute';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

const onRemove = jest.fn();
const Fixture = () => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [BlockingAttribute.FIRST_NAME],
            matchingCriteria: [],
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <BlockingCriteriaAttribute
                label="First name"
                description="first name description"
                attribute={BlockingAttribute.FIRST_NAME}
                onRemove={onRemove}
            />
            <BlockingCriteriaAttribute
                label="last name"
                description="last name description"
                attribute={BlockingAttribute.LAST_NAME}
                onRemove={onRemove}
            />
        </FormProvider>
    );
};
describe('BlockingCriteriaAttribute', () => {
    it('should display the proper label', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('First name')).toBeInTheDocument();
    });

    it('should display the proper description', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('first name description')).toBeInTheDocument();
    });

    it('should not display criteria that is not selected', () => {
        const { queryByText } = render(<Fixture />);

        expect(queryByText('Last name')).not.toBeInTheDocument();
    });

    it('should remove when delete button clicked', () => {
        const { getByRole } = render(<Fixture />);

        const deleteButton = getByRole('button');
        expect(deleteButton).toBeInTheDocument();
        userEvent.click(deleteButton);
        expect(onRemove).toHaveBeenCalledWith(BlockingAttribute.FIRST_NAME);
    });
});
