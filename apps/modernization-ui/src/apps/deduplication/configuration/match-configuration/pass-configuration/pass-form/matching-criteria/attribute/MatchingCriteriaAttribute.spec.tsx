import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';
import { BlockingAttribute, MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { MatchingCriteriaAttribute } from './MatchingCriteriaAttribute';

const onRemove = jest.fn();
const Fixture = () => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [BlockingAttribute.LAST_NAME],
            matchingCriteria: [
                { attribute: MatchingAttribute.FIRST_NAME, method: MatchMethod.JAROWINKLER, threshold: 0.5 }
            ],
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <MatchingCriteriaAttribute
                label="First name"
                attribute={MatchingAttribute.FIRST_NAME}
                index={0}
                logOdds={2.3938}
                onRemove={onRemove}
            />
            <MatchingCriteriaAttribute
                label="Last name"
                attribute={MatchingAttribute.LAST_NAME}
                index={1}
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

    it('should display method label', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Method')).toBeInTheDocument();
        expect(getByLabelText('Method')).toHaveValue('JAROWINKLER');
    });

    it('should display threshold label', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Threshold')).toBeInTheDocument();
    });

    it('should display threshold value', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Threshold')).toBeInTheDocument();
        expect(getByLabelText('Threshold')).toHaveValue(0.5);
    });

    it('should set threshold value for exact match', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        await user.selectOptions(getByLabelText('Method'), 'EXACT');

        expect(getByLabelText('Threshold')).toHaveValue(1);
        expect(getByLabelText('Threshold')).toBeDisabled();
    });

    it('should display threshold tooltip', async () => {
        const user = userEvent.setup();

        const { container, findByText } = render(<Fixture />);

        const icon = container.querySelector('svg');
        await user.hover(icon!);

        expect(
            await findByText(
                'Values between 0 and 1, above which two strings are said to be "similar enough" that they are probably the same thing. Values that are less than the threshold will be calculated as 0. Attributes that use “Exact Match” will automatically have a threshold of 1 and cannot be adjusted.'
            )
        ).toBeInTheDocument();
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

    it('should remove when delete button clicked', async () => {
        const { getByRole } = render(<Fixture />);

        const user = userEvent.setup();

        const deleteButton = getByRole('button');

        expect(deleteButton).toBeInTheDocument();

        await user.click(deleteButton);

        expect(onRemove).toHaveBeenCalledWith(0);
    });
});
