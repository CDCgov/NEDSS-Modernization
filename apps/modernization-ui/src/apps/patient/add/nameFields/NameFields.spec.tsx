import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { NameFields } from './NameFields';
import { DefaultNewPatentEntry, NewPatientEntry, initialEntry } from 'apps/patient/add';
import { FormProvider, useForm } from 'react-hook-form';

const coded = {
    suffixes: []
};

const Fixture = () => {
    const methods = useForm<NewPatientEntry, DefaultNewPatentEntry>({
        defaultValues: initialEntry(),
        mode: 'onBlur'
    });

    return (
        <FormProvider {...methods}>
            <NameFields id="id" title="title" coded={coded} />
        </FormProvider>
    );
};

describe('when entering a last name', () => {
    it('should not allow more than 50 characters in Last name', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const input = getByLabelText('Last');

        await user.type(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty{tab}');

        expect(getByText(/Only allows 50 characters/)).toBeInTheDocument();
    });

    it('should allow numbers in Last name', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        const input = getByLabelText('Last');

        await user.type(input, '1231');

        expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
    });

    it('should allow special characters Last name', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        const input = getByLabelText('Last');

        await user.type(input, '!@#$%^&*');

        expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the numbers
    });
});

describe('when entering a first name', () => {
    it('should not allow more than 50 characters in First name', async () => {
        const user = userEvent.setup();
        const { getByLabelText, getByText } = render(<Fixture />);

        const input = getByLabelText('First');

        await user.type(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty{tab}');

        expect(getByText(/Only allows 50 characters/)).toBeInTheDocument();
    });

    it('should allow numbers in First name', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        const input = getByLabelText('First');

        await user.type(input, '1231');

        expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
    });

    it('should allow special characters First name', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        const input = getByLabelText('First');

        await user.type(input, '!@#$%^&*');

        expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the numbers
    });
});

describe('when entering a middle name', () => {
    it('should not allow more than 50 characters in Middle name', async () => {
        const user = userEvent.setup();
        const { getByLabelText, getByText } = render(<Fixture />);

        const input = getByLabelText('Middle');

        await user.type(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty{tab}');

        expect(getByText(/Only allows 50 characters/)).toBeInTheDocument();
    });

    it('should allow numbers in Middle name', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        const input = getByLabelText('Middle');

        await user.type(input, '1231');

        expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
    });

    it('should allow special characters Middle name', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        const input = getByLabelText('Middle');

        await user.type(input, '!@#$%^&*');

        expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the numbers
    });
});
