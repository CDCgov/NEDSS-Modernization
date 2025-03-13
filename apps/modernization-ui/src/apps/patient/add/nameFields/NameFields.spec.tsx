import { act, fireEvent, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { NameFields } from './NameFields';
import { DefaultNewPatentEntry, NewPatientEntry, initialEntry } from 'apps/patient/add';
import { FormProvider, useForm } from 'react-hook-form';
import { ReactNode } from 'react';

const coded = {
    suffixes: []
};

const Wrapper = ({ children }: { children: ReactNode }) => {
    const methods = useForm<NewPatientEntry, DefaultNewPatentEntry>({
        defaultValues: initialEntry(),
        mode: 'onBlur'
    });

    return <FormProvider {...methods}>{children}</FormProvider>;
};

const setup = () => {
    return render(
        <Wrapper>
            <NameFields id="id" title="title" coded={coded} />
        </Wrapper>
    );
};

describe('when entering a last name', () => {
    it('should not allow more than 50 characters in Last name', async () => {
        const { getByLabelText, getByText } = setup();

        const input = getByLabelText('Last');

        act(() => userEvent.paste(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty'));

        act(() => {
            fireEvent.blur(input);
        });

        await waitFor(() => {
            expect(getByText('Only allows 50 characters.')).toBeInTheDocument();
        });
    });

    it('should allow numbers in Last name', async () => {
        const { getByLabelText } = setup();

        const input = getByLabelText('Last');

        await userEvent.type(input, '1231');

        expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
    });

    it('should allow special characters Last name', async () => {
        const { getByLabelText } = setup();

        const input = getByLabelText('Last');

        await userEvent.type(input, '!@#$%^&*');

        expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the numbers
    });
});

describe('when entering a first name', () => {
    it('should not allow more than 50 characters in First name', async () => {
        const { getByLabelText, getByText } = setup();

        const input = getByLabelText('First');

        act(() => userEvent.paste(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty'));

        act(() => {
            fireEvent.blur(input);
        });

        await waitFor(() => {
            expect(getByText('Only allows 50 characters.')).toBeInTheDocument();
        });
    });

    it('should allow numbers in First name', async () => {
        const { getByLabelText } = setup();

        const input = getByLabelText('First');

        await userEvent.type(input, '1231');

        expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
    });

    it('should allow special characters First name', async () => {
        const { getByLabelText } = setup();

        const input = getByLabelText('First');

        await userEvent.type(input, '!@#$%^&*');

        expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the numbers
    });
});

describe('when entering a middle name', () => {
    it('should not allow more than 50 characters in Middle name', async () => {
        const { getByLabelText, getByText } = setup();

        const input = getByLabelText('Middle');

        act(() => userEvent.paste(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty'));

        act(() => {
            fireEvent.blur(input);
        });

        await waitFor(() => {
            expect(getByText('Only allows 50 characters.')).toBeInTheDocument();
        });
    });

    it('should allow numbers in Middle name', async () => {
        const { getByLabelText } = setup();

        const input = getByLabelText('Middle');

        await userEvent.type(input, '1231');

        expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
    });

    it('should allow special characters Middle name', async () => {
        const { getByLabelText } = setup();

        const input = getByLabelText('Middle');

        await userEvent.type(input, '!@#$%^&*');

        expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the numbers
    });
});
