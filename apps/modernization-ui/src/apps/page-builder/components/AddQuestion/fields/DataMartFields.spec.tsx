import { render, waitFor } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import { DataMartFields } from './DataMartFields';

const { result } = renderHook(() =>
    useForm<CreateQuestionForm>({
        mode: 'onBlur',
        defaultValues: {
            subgroup: 'RSK',
            dataMartInfo: { dataMartColumnName: 'duplicateDataMartColumnName', rdbColumnName: 'duplicateRdbColumnName' }
        }
    })
);

const setError = jest.fn();
const validate = jest.fn();
const mockUseQuestionValidation = {
    validate,
    isValid: false,
    error: undefined,
    isLoading: false
};

jest.mock('apps/page-builder/hooks/api/useQuestionValidation', () => ({
    useQuestionValidation: () => mockUseQuestionValidation
}));

jest.mock('apps/page-builder/hooks/api/useOptions', () => ({
    useOptions: () => {
        return { options: [] };
    }
}));

describe('DataMartFields', () => {
    it('should validate data mart column on blur', async () => {
        const { getByText } = render(
            <FormProvider {...result.current} setError={setError}>
                <DataMartFields />
            </FormProvider>
        );
        const dataMartField = getByText('Data mart column name');
        expect(dataMartField).toBeInTheDocument();
        const rdbColumnField = getByText('RDB column name');
        expect(rdbColumnField).toBeInTheDocument();
        userEvent.click(dataMartField);
        userEvent.click(rdbColumnField);

        await waitFor(() => {
            expect(validate).toHaveBeenCalled();
            expect(setError).toHaveBeenNthCalledWith(2, 'dataMartInfo.dataMartColumnName', {
                message: 'A column name: duplicateDataMartColumnName already exists in the system'
            });
        });
    });

    it('should validate rdb column on blur', async () => {
        const { getByText } = render(
            <FormProvider {...result.current} setError={setError}>
                <DataMartFields />
            </FormProvider>
        );
        const dataMartField = getByText('Data mart column name');
        expect(dataMartField).toBeInTheDocument();
        const rdbColumnField = getByText('RDB column name');
        expect(rdbColumnField).toBeInTheDocument();
        userEvent.click(dataMartField);
        userEvent.click(rdbColumnField);

        await waitFor(() => {
            expect(validate).toHaveBeenCalled();
            expect(setError).toHaveBeenNthCalledWith(1, 'dataMartInfo.rdbColumnName', {
                message:
                    'A column name: duplicateRdbColumnName already exists in the system with the specified subgroup'
            });
        });
    });
});
