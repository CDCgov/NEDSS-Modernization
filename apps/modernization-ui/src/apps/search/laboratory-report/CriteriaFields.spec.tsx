import React from 'react';
import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { useForm } from 'react-hook-form';
import { LabReportFilterEntry } from 'apps/search/laboratory-report/labReportFormTypes';
import { CriteriaFields, LabReportCriteriaFields } from './CriteriaFields';
import { AutocompleteSingleProps } from 'design-system/autocomplete';

jest.mock('options/autocompete/ResultedTestsAutocomplete', () => ({
    ResultedTestsAutocomplete: ({ id, label, onChange }: AutocompleteSingleProps) => (
        <input
            data-testid={id}
            placeholder={label}
            onChange={(e) => onChange?.({ name: e.target.value, value: e.target.value, label: e.target.value })}
        />
    )
}));

jest.mock('options/autocompete/CodedResultsAutocomplete', () => ({
    CodedResultsAutocomplete: ({ id, label, onChange }: AutocompleteSingleProps) => (
        <input
            data-testid={id}
            placeholder={label}
            onChange={(e) => onChange?.({ name: e.target.value, value: e.target.value, label: e.target.value })}
        />
    )
}));

const LabReportCriteriaFieldsWithForm = () => {
    const form = useForm<LabReportFilterEntry>({ defaultValues: {} });
    return <LabReportCriteriaFields form={form} />;
};

const CriteriaFieldsWithForm = () => {
    const form = useForm<LabReportFilterEntry>({ defaultValues: {} });
    return <CriteriaFields form={form} />;
};

describe('LabReportCriteriaFields component', () => {
    it('should render ResultedTestsAutocomplete with correct props', async () => {
        const { getByTestId, getByPlaceholderText } = render(<LabReportCriteriaFieldsWithForm />);

        await waitFor(() => {
            const resultedTestInput = getByTestId('resultedTest');
            expect(resultedTestInput).toBeInTheDocument();
            expect(getByPlaceholderText('Resulted Test')).toBeInTheDocument();
        });
    });

    it('should render CodedResultsAutocomplete with correct props', async () => {
        const { getByTestId, getByPlaceholderText } = render(<LabReportCriteriaFieldsWithForm />);

        await waitFor(() => {
            const codedResultInput = getByTestId('codedResult');
            expect(codedResultInput).toBeInTheDocument();
            expect(getByPlaceholderText('Coded result/organism')).toBeInTheDocument();
        });
    });

    it('should update form values when inputs change', async () => {
        const { getByTestId } = render(<LabReportCriteriaFieldsWithForm />);

        const resultedTestInput = getByTestId('resultedTest');
        const codedResultInput = getByTestId('codedResult');

        userEvent.type(resultedTestInput, 'Test Result');
        userEvent.type(codedResultInput, 'Coded Result');

        await waitFor(() => {
            expect(resultedTestInput).toHaveValue('Test Result');
            expect(codedResultInput).toHaveValue('Coded Result');
        });
    });
});

describe('CriteriaFields component', () => {
    it('should render LabReportCriteriaFields within a div with id "criteria"', () => {
        const { container, getByTestId } = render(<CriteriaFieldsWithForm />);

        const criteriaDiv = container.querySelector('#criteria');
        expect(criteriaDiv).toBeInTheDocument();

        expect(getByTestId('resultedTest')).toBeInTheDocument();
        expect(getByTestId('codedResult')).toBeInTheDocument();
    });
});
