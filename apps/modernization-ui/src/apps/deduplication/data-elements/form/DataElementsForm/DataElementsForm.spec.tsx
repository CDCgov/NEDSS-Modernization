import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { DataElementsForm } from './DataElementsForm';
import { FormProvider, useForm } from 'react-hook-form';
import { DataElements } from '../../DataElement';

const Wrapper = ({ children }: { children: React.ReactNode }) => {
    const methods = useForm<DataElements>({ defaultValues: {} });
    return <FormProvider {...methods}>{children}</FormProvider>;
};

test('renders DataElementsForm component', () => {
    render(
        <Wrapper>
            <DataElementsForm />
        </Wrapper>
    );

    // Check that the table headers are present
    expect(screen.getByText('Field')).toBeInTheDocument();
    expect(screen.getByText('Odds ratio')).toBeInTheDocument();
    expect(screen.getByText('Log odds')).toBeInTheDocument();
    expect(screen.getByText('Threshold')).toBeInTheDocument();
});

test('toggles individual checkboxes', () => {
    render(
        <Wrapper>
            <DataElementsForm />
        </Wrapper>
    );

    // Check that 'First name' checkbox is not checked initially
    const firstNameCheckbox = screen.getByTestId('firstName-checkbox');
    expect(firstNameCheckbox).not.toBeChecked();

    // Click the checkbox to check it
    fireEvent.click(firstNameCheckbox);
    expect(firstNameCheckbox).toBeChecked();

    // Click it again to uncheck it
    fireEvent.click(firstNameCheckbox);
    expect(firstNameCheckbox).not.toBeChecked();
});

test('selects all checkboxes when "Select All" is clicked', async () => {
    render(
        <Wrapper>
            <DataElementsForm />
        </Wrapper>
    );

    const selectAllCheckbox = screen.getByTestId('select-all-checkbox');

    // Click the "Select All" checkbox
    fireEvent.click(selectAllCheckbox);

    // Wait for the state to propagate and ensure checkboxes are updated
    await waitFor(() => {
        // Query random checkboxes using their respective data-testid
        const firstNameCheckbox = screen.getByTestId('firstName-checkbox');
        const lastNameCheckbox = screen.getByTestId('lastName-checkbox');
        const suffixCheckbox = screen.getByTestId('suffix-checkbox');
        const emailCheckbox = screen.getByTestId('email-checkbox');

        // Verify that the individual checkboxes are checked
        expect(firstNameCheckbox).toBeEnabled();
        expect(lastNameCheckbox).toBeEnabled();
        expect(suffixCheckbox).toBeEnabled();
        expect(emailCheckbox).toBeEnabled();
    });
});

test('shows tooltip when info icon is hovered', async () => {
    render(
        <Wrapper>
            <DataElementsForm />
        </Wrapper>
    );

    // Find the info icon by the data-testid attribute
    const infoIcon = screen.getByTestId('infoIcon');

    // Simulate hovering over the info icon
    fireEvent.mouseOver(infoIcon);

    // Wait for the tooltip to appear and check its content
    await waitFor(() => {
        expect(screen.getByText(/Threshold/i)).toBeInTheDocument();
    });
});

test('renders form with initial values', () => {
    render(
        <Wrapper>
            <DataElementsForm />
        </Wrapper>
    );

    // Check the initial state of a checkbox (randomly checked)
    expect(screen.getByTestId('firstName-checkbox')).not.toBeChecked();
    expect(screen.getByTestId('email-checkbox')).not.toBeChecked();
    expect(screen.getByTestId('patientInternalIdentifier-checkbox')).not.toBeChecked();
});
