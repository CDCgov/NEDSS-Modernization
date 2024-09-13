import React from 'react';
import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { AdministrativeEntryFields } from './AdministrativeEntryFields';

jest.mock('components/FormInputs/DatePickerInput', () => ({
    DatePickerInput: ({ label, name }: { label: string; name: string }) => (
        <div data-testid={`mock-date-picker-${name}`}>{label}</div>
    )
}));

jest.mock('components/FormInputs/Input', () => ({
    Input: ({ label, name }: { label: string; name: string }) => <div data-testid={`mock-input-${name}`}>{label}</div>
}));

const TestWrapper: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const methods = useForm();
    return <FormProvider {...methods}>{children}</FormProvider>;
};

describe('AdministrativeEntryFields', () => {
    it('renders the component with correct fields', () => {
        const { getByTestId, getByText } = render(
            <TestWrapper>
                <AdministrativeEntryFields />
            </TestWrapper>
        );

        const datePicker = getByTestId('mock-date-picker-asOf');
        expect(datePicker).toBeInTheDocument();
        expect(getByText('Information as of date')).toBeInTheDocument();

        const commentInput = getByTestId('mock-input-comment');
        expect(commentInput).toBeInTheDocument();
        expect(getByText('General comments')).toBeInTheDocument();
    });
});
