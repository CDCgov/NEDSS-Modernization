import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';

import { CreateQuestionForm } from '../QuestionForm';

import { BasicInformationFields } from './BasicInformationFields';

const setError = vi.fn();
const validate = vi.fn();
const mockUseQuestionValidation = {
    validate,
    isValid: false,
    error: undefined,
    isLoading: false,
};

vi.mock('apps/page-builder/hooks/api/useQuestionValidation', () => ({
    useQuestionValidation: () => mockUseQuestionValidation,
}));

vi.mock('apps/page-builder/hooks/api/useOptions', () => ({
    useOptions: () => {
        return { options: [] };
    },
}));

const Fixture = () => {
    const form = useForm<CreateQuestionForm>({
        mode: 'onBlur',
    });

    return (
        <FormProvider {...form} setError={setError}>
            <BasicInformationFields />
        </FormProvider>
    );
};

describe('BasicInformationFields', () => {
    it('should validate unique Id on blur', async () => {
        const { getByRole } = render(<Fixture />);

        const input = getByRole('textbox', { name: 'Unique ID' });
        expect(input).toBeInTheDocument();

        await userEvent.type(input, 'duplicateUniqueId');

        await userEvent.tab();

        expect(validate).toHaveBeenCalledWith('duplicateUniqueId');
    });

    it('should validate unique name on blur', async () => {
        const { getByRole } = render(<Fixture />);

        const input = getByRole('textbox', { name: 'Unique name' });
        expect(input).toBeInTheDocument();

        await userEvent.type(input, 'duplicateUniqueName');

        await userEvent.tab();

        expect(validate).toHaveBeenCalledWith('duplicateUniqueName');
    });
});
