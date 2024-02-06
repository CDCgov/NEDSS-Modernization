import { render, waitFor } from '@testing-library/react';
import { BasicInformationFields } from './BasicInformationFields';
import { FormProvider, useForm } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import { renderHook } from '@testing-library/react-hooks';
import userEvent from '@testing-library/user-event';

const { result } = renderHook(() =>
    useForm<CreateQuestionForm>({
        mode: 'onBlur',
        defaultValues: { uniqueId: 'duplicateUniqueId', uniqueName: 'duplicateUniqueName' }
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

describe('BasicInformationFields', () => {
    it('should validate unique Id on blur', async () => {
        const { getByText } = render(
            <FormProvider {...result.current} setError={setError}>
                <BasicInformationFields />
            </FormProvider>
        );
        const uniqueIdField = getByText('Unique ID');
        expect(uniqueIdField).toBeInTheDocument();
        const uniqueNameField = getByText('Unique name');
        expect(uniqueNameField).toBeInTheDocument();
        userEvent.click(uniqueIdField);
        userEvent.click(uniqueNameField);

        await waitFor(() => {
            expect(validate).toHaveBeenCalled();
            expect(setError).toHaveBeenNthCalledWith(1, 'uniqueId', {
                message: 'A question with Unique ID: duplicateUniqueId already exists in the system'
            });
        });
    });

    it('should validate unique name on blur', async () => {
        const { getByText } = render(
            <FormProvider {...result.current} setError={setError}>
                <BasicInformationFields />
            </FormProvider>
        );
        const uniqueIdField = getByText('Unique ID');
        expect(uniqueIdField).toBeInTheDocument();
        const uniqueNameField = getByText('Unique name');
        expect(uniqueNameField).toBeInTheDocument();
        userEvent.click(uniqueIdField);
        userEvent.click(uniqueNameField);

        await waitFor(() => {
            expect(validate).toHaveBeenCalled();
            expect(setError).toHaveBeenNthCalledWith(2, 'uniqueName', {
                message: 'A question with Unique name: duplicateUniqueName already exists in the system'
            });
        });
    });
});
