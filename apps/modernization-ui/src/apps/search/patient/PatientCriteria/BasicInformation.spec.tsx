import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicInformation } from './BasicInformation';
import { PatientCriteriaEntry } from '../criteria';

const mockAllows = jest.fn();

jest.mock('libs/permission/usePermissions', () => ({
    usePermissions: () => ({ permissions: [], allows: mockAllows })
}));

const Fixture = () => {
    const form = useForm<PatientCriteriaEntry>({
        mode: 'onChange',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] }
    });

    return (
        <FormProvider {...form}>
            <BasicInformation />
        </FormProvider>
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
});
