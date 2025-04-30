import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { DataElementRow } from './DataElementRow';
import { DataElements } from 'apps/deduplication/api/model/DataElement';
import userEvent from '@testing-library/user-event';

// Test component that provides the form context
const TestFormProvider = ({ fieldName, field }: { fieldName: string; field: string }) => {
    const methods = useForm();

    return (
        <FormProvider {...methods}>
            <table>
                <tbody>
                    <DataElementRow
                        dataElements={{ firstName: { oddsRatio: 1.5 } }}
                        fieldName={fieldName}
                        field={field as keyof DataElements}
                    />
                </tbody>
            </table>
        </FormProvider>
    );
};

describe('DataElementRow Component', () => {
    const field = 'firstName'; // Example field name

    it('should render checkbox and numeric inputs', () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        // Query the checkbox via aria-labelledby or aria-label
        expect(screen.getByLabelText('First Name')).toBeInTheDocument(); // This will be the field name

        // Assert numeric inputs (Odds Ratio)
        const inputs = screen.getAllByRole('spinbutton');
        expect(inputs).toHaveLength(1); // Odds Ratio
    });

    it('should enable oddsRatio input when checkbox is checked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByLabelText('First Name'); // Use getByLabelText here
        const [oddsRatioInput] = screen.getAllByRole('spinbutton');

        // Initially, inputs should be disabled
        expect(oddsRatioInput).toBeDisabled();

        // Click the checkbox to enable the inputs
        fireEvent.click(checkbox);

        // Wait for the inputs to be enabled
        await waitFor(() => {
            expect(oddsRatioInput).toBeEnabled();
        });
    });

    it('should calculate and display logOdds correctly when oddsRatio is updated', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const [oddsRatioInput] = screen.getAllByRole('spinbutton');

        fireEvent.change(oddsRatioInput, { target: { value: '0.5' } });

        await waitFor(() => {
            expect(screen.getByText(/-0\.693/)).toBeInTheDocument();
        });
    });

    it('should disable inputs when checkbox is unchecked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByLabelText('First Name'); // Use getByLabelText here
        const [oddsRatioInput] = screen.getAllByRole('spinbutton');

        // Enable the inputs
        fireEvent.click(checkbox);

        await waitFor(() => {
            expect(oddsRatioInput).toBeEnabled();
        });

        // Uncheck to disable inputs
        fireEvent.click(checkbox);

        await waitFor(() => {
            expect(oddsRatioInput).toBeDisabled();
        });
    });

    it('should render the correct initial values based on configuration', async () => {
        const user = userEvent.setup();
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByLabelText('First Name'); // Use getByLabelText here
        const [oddsRatioInput] = screen.getAllByRole('spinbutton');

        await user.click(checkbox);

        await waitFor(() => {
            expect(oddsRatioInput).toHaveValue(1.5);
        });
    });

    it('should clear input values when checkbox is unchecked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByLabelText('First Name'); // Use getByLabelText here
        const [oddsRatioInput] = screen.getAllByRole('spinbutton');

        // Enable inputs and set values
        fireEvent.click(checkbox);
        fireEvent.change(oddsRatioInput, { target: { value: '0.5' } });

        // Uncheck the checkbox to clear the values
        fireEvent.click(checkbox);

        await waitFor(() => {
            expect(oddsRatioInput).toHaveValue(null);
        });
    });
});
