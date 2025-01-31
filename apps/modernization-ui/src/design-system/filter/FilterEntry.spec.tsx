import { render, act, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FieldValues, FormProvider, useForm } from 'react-hook-form';
import { FilterEntry } from './FilterEntry';
import { FilterProvider } from 'design-system/filter/useFilter';

const Fixture = ({ id }: { id: string }) => {
    const form = useForm<FieldValues>({
        mode: 'onBlur',
        defaultValues: {
            filter: {
                [id]: ''
            }
        }
    });

    return (
        <FilterProvider>
            <FormProvider {...form}>
                <FilterEntry id={id} property="text" />
            </FormProvider>
        </FilterProvider>
    );
};

describe('FilterEntry', () => {
    it('should render the TextInput component', () => {
        const { getByRole } = render(<Fixture id="test-id" />);
        const inputElement = getByRole('textbox');
        expect(inputElement).toBeInTheDocument();
    });

    it('should call onChange when input value changes', async () => {
        const { getByRole } = render(<Fixture id="test-id" />);
        const inputElement = getByRole('textbox');

        await act(async () => {
            userEvent.paste(inputElement, 'new value');
        });

        await waitFor(() => {
            expect(inputElement).toHaveValue('new value');
        });
    });

    it('should set the correct input value', async () => {
        const { getByRole } = render(<Fixture id="test-id" />);
        const inputElement = getByRole('textbox');
        expect(inputElement).toHaveValue('');
    });

    it('should handle empty input', async () => {
        const { getByRole } = render(<Fixture id="test-id" />);
        const inputElement = getByRole('textbox');

        await act(async () => {
            userEvent.clear(inputElement);
            userEvent.tab();
        });

        await waitFor(() => {
            expect(inputElement).toHaveValue('');
        });
    });
});
