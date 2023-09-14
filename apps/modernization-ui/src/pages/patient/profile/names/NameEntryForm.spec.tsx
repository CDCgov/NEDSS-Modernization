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

            const input = getByLabelText('First name');

            act(() => userEvent.paste(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Only allows 50 characters')).toBeInTheDocument();
            });
        });

        it('should not allow numbers in First name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('First name');

            await act(() => userEvent.type(input, '1231'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
        });

        it('should not allow special characters in First name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('First name');

            await act(() => userEvent.type(input, '!@#$%^&*'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
        });
    });

    describe('and entering the Middle name', () => {
        it('should not allow more than 50 characters in Middle name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Middle name');

            act(() => userEvent.paste(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Only allows 50 characters')).toBeInTheDocument();
            });
        });

        it('should not allow numbers in Middle name', async () => {
            const { getByLabelText, container, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Middle name');

            await act(() => userEvent.type(input, '1231'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
        });

        it('should not allow special characters in Middle name', async () => {
            const { getByLabelText, container, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Middle name');

            await act(() => userEvent.type(input, '!@#$%^&*'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
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
                expect(getByText('Only allows 50 characters')).toBeInTheDocument();
            });
        });

        it('should not allow numbers in Second middle name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Second middle');

            await act(() => userEvent.type(input, '1231'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
        });

        it('should not allow special characters in Second middle name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Second middle');

            await act(() => userEvent.type(input, '!@#$%^&*'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
        });
    });

    describe('and entering the Last name', () => {
        it('should not allow more than 50 characters in Last name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Last name');

            act(() => userEvent.paste(input, 'qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Only allows 50 characters')).toBeInTheDocument();
            });
        });

        it('should not allow numbers in Last name', async () => {
            const { getByLabelText, container, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Last name');

            await act(() => userEvent.type(input, '1231'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
        });

        it('should not allow special characters in Last name', async () => {
            const { getByLabelText, container, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Last name');

            await act(() => userEvent.type(input, '!@#$%^&*'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
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
                expect(getByText('Only allows 50 characters')).toBeInTheDocument();
            });
        });

        it('should not allow numbers in Second last name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Second last');

            await act(() => userEvent.type(input, '1231'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
        });

        it('should not allow special characters in Second last name', async () => {
            const { getByLabelText, getByText } = render(
                <NameEntryForm action="Add" entry={entry} onChange={onChange} />
            );

            const input = getByLabelText('Second last');

            await act(() => userEvent.type(input, '!@#$%^&*'));

            act(() => {
                fireEvent.blur(input);
            });

            await waitFor(() => {
                expect(getByText('Names can not contain numbers or special characters.')).toBeInTheDocument();
            });
        });
    });
});
