import { render } from '@testing-library/react';
import { ActivateToggle } from './ActivateToggle';
import { FormProvider, useForm } from 'react-hook-form';
import { Pass } from 'apps/deduplication/api/model/Pass';
import userEvent from '@testing-library/user-event';

const Fixture = () => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [],
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <ActivateToggle />
        </FormProvider>
    );
};

describe('ActivateToggle', () => {
    it('should display proper label', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Activate this pass configuration')).toBeInTheDocument();
    });

    it('should update update active when clicked', () => {
        const { getByRole } = render(<Fixture />);

        expect(getByRole('checkbox')).toBeChecked();
        userEvent.click(getByRole('checkbox'));
        expect(getByRole('checkbox')).not.toBeChecked();
        userEvent.click(getByRole('checkbox'));
        expect(getByRole('checkbox')).toBeChecked();
    });
});
