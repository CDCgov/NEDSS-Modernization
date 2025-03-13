import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { DataElementRow } from './DataElementRow';
import { useDataElements } from 'apps/deduplication/api/useDataElements';
import { DataElements } from 'apps/deduplication/data-elements/DataElement'
import { useAlert } from 'alert';

import userEvent from '@testing-library/user-event';

// Mock the useDataElements hook
jest.mock('apps/deduplication/api/useDataElements', () => ({
    useDataElements: jest.fn(),
}));


// Test component that provides the form context
const TestFormProvider = ({ fieldName, field }: { fieldName: string; field: string }) => {
    const methods = useForm();

    return (
        <FormProvider {...methods}>
            <DataElementRow fieldName={fieldName} field={field as keyof DataElements} />
        </FormProvider>
    );
};

describe('DataElementRow Component', () => {
    const field = 'firstName'; // Example field name

    beforeEach(() => {
        // Mocking configuration data for the test
        (useDataElements as jest.Mock).mockReturnValue({
            configuration: {
                firstName: { oddsRatio: 1.5, threshold: 0.8 },
            },
        });
    });

    it('should render checkbox and numeric inputs', () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        // Test if checkbox and numeric inputs are rendered
        expect(screen.getByTestId(`${field}-checkbox`)).toBeInTheDocument();
        expect(screen.getByTestId(`${field}-oddsRatio`)).toBeInTheDocument();
        expect(screen.getByTestId(`${field}-threshold`)).toBeInTheDocument();
    });

    it('should enable oddsRatio and threshold input when checkbox is checked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByTestId(`${field}-checkbox`);
        const oddsRatioInput = screen.getByTestId(`${field}-oddsRatio`);
        const thresholdInput = screen.getByTestId(`${field}-threshold`);

        // Initially, inputs should be disabled
        expect(oddsRatioInput).toBeDisabled();
        expect(thresholdInput).toBeDisabled();

        // Click the checkbox to enable the inputs
        fireEvent.click(checkbox);

        // Wait for the inputs to be enabled
        await waitFor(() => {
            expect(oddsRatioInput).toBeEnabled();
            expect(thresholdInput).toBeEnabled();
        });
    });

    it('should calculate and display logOdds correctly when oddsRatio is updated', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        // Initially, logOdds should be 0 because the default oddsRatio is 2
        const initialLogOdds = screen.getByText('--');
        expect(initialLogOdds).toBeInTheDocument();

        // Simulate user input in the oddsRatio field to change the odds ratio to 5
        const oddsRatioInput = screen.getByTestId('firstName-oddsRatio') as HTMLInputElement;
        fireEvent.change(oddsRatioInput, { target: { value: '5' } });

        // Wait for the logOdds to be updated after the oddsRatio change
        await waitFor(() => {
            const updatedLogOdds = screen.getByText(/1\.6094379124341003/); // log(5) ≈ 1.60944
            expect(updatedLogOdds).toBeInTheDocument(); // Check if the new logOdds value is displayed
        });
    });

    it('should disable inputs when checkbox is unchecked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByTestId(`${field}-checkbox`);
        const oddsRatioInput = screen.getByTestId(`${field}-oddsRatio`);
        const thresholdInput = screen.getByTestId(`${field}-threshold`);

        // Initially, inputs should be disabled
        expect(oddsRatioInput).toBeDisabled();
        expect(thresholdInput).toBeDisabled();

        // Click the checkbox to enable the inputs
        fireEvent.click(checkbox);

        // Now inputs should be enabled
        await waitFor(() => {
            expect(oddsRatioInput).toBeEnabled();
            expect(thresholdInput).toBeEnabled();
        });

        // Uncheck the checkbox and ensure inputs are disabled again
        fireEvent.click(checkbox);
        await waitFor(() => {
            expect(oddsRatioInput).toBeDisabled();
            expect(thresholdInput).toBeDisabled();
        });
    });

    it('should render the correct initial values based on configuration', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = await screen.findByTestId(`${field}-checkbox`);
        const oddsRatioInput = await screen.findByTestId(`${field}-oddsRatio`) as HTMLInputElement;
        const thresholdInput = await screen.findByTestId(`${field}-threshold`) as HTMLInputElement;

        // Step 1: Ensure checkbox is checked
        await userEvent.click(checkbox);

        // Step 2: Wait for inputs to be populated
        await waitFor(() => {
            expect(oddsRatioInput.value).toBe("1.5");
            expect(thresholdInput.value).toBe("0.8");
        });
    });

    it('should clear input values when checkbox is unchecked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByTestId(`${field}-checkbox`) as HTMLInputElement;
        const oddsRatioInput = screen.getByTestId(`${field}-oddsRatio`) as HTMLInputElement;
        const thresholdInput = screen.getByTestId(`${field}-threshold`) as HTMLInputElement;

        // Enable the inputs by checking the checkbox
        fireEvent.click(checkbox);

        // Change values in the inputs
        fireEvent.change(oddsRatioInput, { target: { value: '2' } });
        fireEvent.change(thresholdInput, { target: { value: '0.9' } });

        // Uncheck the checkbox to clear the values
        fireEvent.click(checkbox);

        // Check that the values are cleared
        await waitFor(() => {
            expect(oddsRatioInput.value).toBe("");
            expect(thresholdInput.value).toBe("");
        });
    });
});
