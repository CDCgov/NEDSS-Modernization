import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { DataElementRow } from './DataElementRow';
import { useDataElements } from 'apps/deduplication/api/useDataElements';
import { DataElements } from 'apps/deduplication/data-elements/DataElement'
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

        // Query the checkbox via aria-labelledby or aria-label
        expect(screen.getByLabelText('First Name')).toBeInTheDocument();  // This will be the field name

        // Assert numeric inputs (Odds Ratio & Threshold)
        const inputs = screen.getAllByRole('spinbutton');
        expect(inputs).toHaveLength(2); // Odds Ratio & Threshold
    });

    it('should enable oddsRatio and threshold input when checkbox is checked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByLabelText('First Name'); // Use getByLabelText here
        const [oddsRatioInput, thresholdInput] = screen.getAllByRole('spinbutton');

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

        const logOddsDisplay = screen.getByText('--');
        const [oddsRatioInput] = screen.getAllByRole('spinbutton');

        fireEvent.change(oddsRatioInput, { target: { value: '0.5' } });

        await waitFor(() => {
            expect(screen.getByText(/-0\.693/)).toBeInTheDocument();
        });
    });

    it('should disable inputs when checkbox is unchecked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByLabelText('First Name'); // Use getByLabelText here
        const [oddsRatioInput, thresholdInput] = screen.getAllByRole('spinbutton');

        // Enable the inputs
        fireEvent.click(checkbox);

        await waitFor(() => {
            expect(oddsRatioInput).toBeEnabled();
            expect(thresholdInput).toBeEnabled();
        });

        // Uncheck to disable inputs
        fireEvent.click(checkbox);

        await waitFor(() => {
            expect(oddsRatioInput).toBeDisabled();
            expect(thresholdInput).toBeDisabled();
        });
    });

    it('should render the correct initial values based on configuration', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByLabelText('First Name'); // Use getByLabelText here
        const [oddsRatioInput, thresholdInput] = screen.getAllByRole('spinbutton');

        await userEvent.click(checkbox);

        await waitFor(() => {
            expect(oddsRatioInput).toHaveValue(1.5);
            expect(thresholdInput).toHaveValue(0.8);
        });
    });

    it('should clear input values when checkbox is unchecked', async () => {
        render(<TestFormProvider fieldName="First Name" field={field} />);

        const checkbox = screen.getByLabelText('First Name'); // Use getByLabelText here
        const [oddsRatioInput, thresholdInput] = screen.getAllByRole('spinbutton');

        // Enable inputs and set values
        fireEvent.click(checkbox);
        fireEvent.change(oddsRatioInput, { target: { value: '0.5' } });
        fireEvent.change(thresholdInput, { target: { value: '0.9' } });

        // Uncheck the checkbox to clear the values
        fireEvent.click(checkbox);

        await waitFor(() => {
            expect(oddsRatioInput).toHaveValue(null);
            expect(thresholdInput).toHaveValue(null);
        });
    });
});
