import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicInformation } from './BasicInformation';
import { PatientCriteriaEntry } from '../criteria';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';

const mockAllows = jest.fn();

jest.mock('libs/permission/usePermissions', () => ({
    usePermissions: () => ({ permissions: [], allows: mockAllows })
}));

const Fixture = ({ mode }: { mode?: 'onChange' | 'onBlur' }) => {
    const form = useForm<PatientCriteriaEntry>({
        mode: mode ?? 'onChange',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] }
    });

    return (
        <SkipLinkProvider>
            <FormProvider {...form}>
                <BasicInformation />
            </FormProvider>
        </SkipLinkProvider>
    );
};

describe('when Basic information renders', () => {
    it('should render 8 input fields', () => {
        //  this test would be more effective if it checked for the existence of input labels and asserts accessibility settings.
        const { container } = render(<Fixture />);
        const inputs = container.getElementsByTagName('input');
        expect(inputs).toHaveLength(8);
    });

    it('should have helper text for patient ID', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('Separate IDs by commas, semicolons, or spaces')).toBeInTheDocument();
    });

    it('should show status component when a user can find inactive patients', () => {
        mockAllows.mockReturnValue(true);
        const { getByText } = render(<Fixture />);

        // expect(mockAllows).toBeCalledWith('FINDINACTIVE-PATIENT');

        expect(getByText('Include records that are')).toBeInTheDocument();
    });

    it('should hide status component when a user cannot find inactive patients', () => {
        mockAllows.mockReturnValue(false);
        const { queryByText } = render(<Fixture />);

        // expect(mockAllows).toBeCalledWith('FINDINACTIVE-PATIENT');

        expect(queryByText('Include records that are')).not.toBeInTheDocument();
    });

    it('should not accept 0/0/0 as a valid date of birth', async () => {
        const { getByRole } = render(<Fixture />);

        const user = userEvent.setup();

        const monthInput = getByRole('spinbutton', { name: 'Month' });
        const dayInput = getByRole('spinbutton', { name: 'Day' });
        const yearInput = getByRole('spinbutton', { name: 'Year' });

        // Try to enter 0 for month, day, and year

        await user.type(monthInput, '0{tab}');
        await user.type(dayInput, '0{tab}');
        await user.type(yearInput, '0{tab}');

        const errorMessage = getByRole('alert');
        expect(errorMessage).toHaveClass('message error');
        expect(errorMessage).toHaveTextContent('The Date of birth should occur after');
        expect(errorMessage).toHaveTextContent('The Date of birth should have a month between');
        expect(errorMessage).toHaveTextContent('The Date of birth should be at least');
    });

    it('should not show an error message when unchecking all status boxes then tabbing to next checkbox', async () => {
        mockAllows.mockReturnValue(true);
        const { getByRole, queryByRole } = render(<Fixture mode="onBlur" />);

        const user = userEvent.setup();

        const statusCheckbox = getByRole('checkbox', { name: 'Active' });
        expect(statusCheckbox).toBeChecked();
        await user.click(statusCheckbox);
        expect(statusCheckbox).not.toBeChecked();
        await user.tab();

        const errorMessage = queryByRole('alert');
        expect(errorMessage).not.toBeInTheDocument();
    });
});
