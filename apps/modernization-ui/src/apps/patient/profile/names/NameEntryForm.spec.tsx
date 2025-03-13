import { act, fireEvent, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { NameEntryForm } from './NameEntryForm';

jest.mock('./usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => () => ({
        types: [],
        prefixes: [],
        suffixes: [],
        degrees: []
    })
}));

describe('When adding a name', () => {
    const entry = {
        patient: 1157,
        sequence: null,
        asOf: null,
        type: null,
        prefix: null,
        first: null,
        middle: null,
        secondMiddle: null,
        last: null,
        secondLast: null,
        suffix: null,
        degree: null
    };

    const onChange = jest.fn();

    describe('and entering the First name', () => {
        it('should not allow more than 50 characters in First name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

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
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('First');

            await userEvent.type(input, '1231');

            expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
        });

        it('should allow special characters in First name', async () => {
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('First');
            await userEvent.type(input, '!@#$%^&*');

            expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the special characters
        });
    });

    describe('and entering the Middle name', () => {
        it('should not allow more than 50 characters in Middle name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

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
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('Middle');

            await userEvent.type(input, '1231');

            expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
        });

        it('should allow special characters in Middle name', async () => {
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('Middle');
            await userEvent.type(input, '!@#$%^&*');

            expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the special characters
        });
    });

    describe('and entering the Second middle name', () => {
        it('should not allow more than 50 characters in Second middle name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Second middle');

            act(() => userEvent.paste(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Only allows 50 characters.')).toBeInTheDocument();
            });
        });

        it('should allow numbers in Second middle name', async () => {
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('Second middle');

            await userEvent.type(input, '1231');

            expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
        });

        it('should allow special characters in Second middle name', async () => {
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('Second middle');
            await userEvent.type(input, '!@#$%^&*');

            expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the special characters
        });
    });

    describe('and entering the Last name', () => {
        it('should not allow more than 50 characters in Last name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

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
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('Last');

            await userEvent.type(input, '1231');

            expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
        });

        it('should allow special characters in Last name', async () => {
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('Last');
            await userEvent.type(input, '!@#$%^&*');

            expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the special characters
        });
    });

    describe('and entering the Second last name', () => {
        it('should not allow more than 50 characters in Second last name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Second last');

            act(() => userEvent.paste(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Only allows 50 characters.')).toBeInTheDocument();
            });
        });

        it('should allow numbers in Second last name', async () => {
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('Second last');

            await userEvent.type(input, '1231');

            expect(input).toHaveValue('1231'); // Asserting that the input contains the numbers
        });

        it('should allow special characters in Second last name', async () => {
            const { getByLabelText } = render(<NameEntryForm action="Add" entry={entry} onChange={onChange} />);

            const input = getByLabelText('Second last');
            await userEvent.type(input, '!@#$%^&*');

            expect(input).toHaveValue('!@#$%^&*'); // Asserting that the input contains the special characters
        });
    });
});
