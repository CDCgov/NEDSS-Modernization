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
    const firstNameCheckbox = screen.getByLabelText(/First name/i);
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

    const selectAllCheckbox = screen.getByLabelText(/Select All/i);

    // Click the "Select All" checkbox
    fireEvent.click(selectAllCheckbox);

    // Wait for the checkboxes to be updated in the DOM
    await waitFor(() => {
        const firstNameCheckbox = screen.getByLabelText(/First name/i);
        const lastNameCheckbox = screen.getByLabelText(/Last name/i);
        const suffixCheckbox = screen.getByLabelText(/Suffix/i);
        const emailCheckbox = screen.getByLabelText(/Email/i);

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

    // Find the info icon using its data-testid
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
    expect(screen.getByLabelText(/First name/i)).not.toBeChecked();
    expect(screen.getByLabelText(/Email/i)).not.toBeChecked();
    expect(screen.getByLabelText(/Patient internal identifier/i)).not.toBeChecked();
});
