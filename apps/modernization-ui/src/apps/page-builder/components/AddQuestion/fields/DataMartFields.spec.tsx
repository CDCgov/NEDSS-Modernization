import { render, renderHook } from '@testing-library/react';
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

const Fixture = () => {
    const form = useForm<CreateQuestionForm>({
        mode: 'onBlur',
        defaultValues: {
            subgroup: 'RSK',
            dataMartInfo: { dataMartColumnName: 'duplicateDataMartColumnName', rdbColumnName: 'duplicateRdbColumnName' }
        }
    });

    return (
        <FormProvider {...form} setError={setError}>
            <DataMartFields />
        </FormProvider>
    );
};

describe('DataMartFields', () => {
    it('should validate data mart column on blur', () => {
        const { getByText } = render(<Fixture />);
        const dataMartField = getByText('Data mart column name');
        expect(dataMartField).toBeInTheDocument();
        const rdbColumnField = getByText('RDB column name');
        expect(rdbColumnField).toBeInTheDocument();

        expect(validate).toHaveBeenCalled();
        expect(setError).toHaveBeenNthCalledWith(2, 'dataMartInfo.dataMartColumnName', {
            message: 'A Data mart column named: duplicateDataMartColumnName already exists in the system'
        });
    });

    it('should validate rdb column on blur', () => {
        const { getByText } = render(<Fixture />);
        const dataMartField = getByText('Data mart column name');
        expect(dataMartField).toBeInTheDocument();
        const rdbColumnField = getByText('RDB column name');
        expect(rdbColumnField).toBeInTheDocument();

        expect(validate).toHaveBeenCalled();
        expect(setError).toHaveBeenNthCalledWith(1, 'dataMartInfo.rdbColumnName', {
            message:
                'An Rdb column named: duplicateRdbColumnName already exists in the system for the specified subgroup'
        });
    });
});
