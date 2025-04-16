import { render } from '@testing-library/react';
import { BlockingCriteria } from './BlockingCriteria';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';
import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';

const onAddAttributes = jest.fn();
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <BlockingCriteria onAddAttributes={onAddAttributes} />
        </FormProvider>
    );
};

const WithContent = () => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [BlockingAttribute.FIRST_NAME],
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <BlockingCriteria onAddAttributes={onAddAttributes} />
        </FormProvider>
    );
};

describe('BlockingCriteria', () => {
    it('should display header', () => {
        const { getByRole } = render(<Fixture />);

        expect(getByRole('heading')).toHaveTextContent('1. Blocking criteria');
    });

    it('should display description', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Include records that meet all these conditions')).toBeInTheDocument();
    });

    it('should display help text when no criteria are specified', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Please add blocking criteria to get started.')).toBeInTheDocument();
    });

    it('should not display help text when criteria are specified', () => {
        const { queryByText } = render(<WithContent />);

        expect(queryByText('Please add blocking criteria to get started.')).not.toBeInTheDocument();
        expect(queryByText('First name')).toBeInTheDocument();
    });

    it('should have a button with proper label', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('button')).toHaveTextContent('Add blocking attribute(s)');
    });

    it('should trigger onAddAttributes when button is clicked', async () => {
        const user = userEvent.setup();
        const { getByRole } = render(<Fixture />);

        const button = getByRole('button');

        await user.click(button);

        expect(onAddAttributes).toHaveBeenCalledTimes(1);
    });
});
