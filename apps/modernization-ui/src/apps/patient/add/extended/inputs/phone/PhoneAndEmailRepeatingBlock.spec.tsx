import { screen, render, waitFor } from '@testing-library/react';
import { PhoneAndEmailRepeatingBlock } from './PhoneAndEmailRepeatingBlock';
import { internalizeDate } from 'date';
import userEvent from '@testing-library/user-event';

const onChange = jest.fn();
const isDirty = jest.fn();

const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('apps/patient/profile/phoneEmail/usePatientPhoneCodedValues', () => ({
    usePatientPhoneCodedValues: () => mockPatientPhoneCodedValues
}));

const mockEntry = {
    state: {
        data: [
            {
                asOf: internalizeDate(new Date()),
                type: 'PH',
                use: 'H'
            }
        ]
    }
};

jest.mock('design-system/entry/multi-value/useMultiValueEntryState', () => ({
    useMultiValueEntryState: () => mockEntry
}));

const awaitRender = async () => {
    // wait on render to prevent act warning
    expect(await screen.findByText('URL')).toBeInTheDocument();
};

const Fixture = () => <PhoneAndEmailRepeatingBlock id="phoneAndEmail" onChange={onChange} isDirty={isDirty} />;

describe('PhoneAndEmailMultiEntry', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(<Fixture />);
        // wait on render to prevent act warning
        await awaitRender();

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Type');
        expect(headers[2]).toHaveTextContent('Phone number');
        expect(headers[3]).toHaveTextContent('Email address');
        expect(headers[4]).toHaveTextContent('Comments');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText } = render(<Fixture />);
        // wait on render to prevent act warning
        await awaitRender();

        const dateInput = getByLabelText('Phone & email as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const type = getByLabelText('Type');
        expect(type).toHaveValue('');

        const use = getByLabelText('Use');
        expect(use).toHaveValue('');

        const countryCode = getByLabelText('Country code');
        expect(countryCode).toHaveValue('');

        const phoneNumber = getByLabelText('Phone number');
        expect(phoneNumber).toHaveValue('');

        const extension = getByLabelText('Extension');
        expect(extension).toHaveValue('');

        const email = getByLabelText('Email');
        expect(email).toHaveValue('');

        const url = getByLabelText('URL');
        expect(url).toHaveValue('');

        const comments = getByLabelText('Phone & email comments');
        expect(comments).toHaveValue('');
    });

    it('should trigger on change when value added', async () => {
        const { getByLabelText, getAllByRole } = render(<Fixture />);
        // wait on render to prevent act warning
        await awaitRender();
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');
        const buttons = getAllByRole('button');

        await waitFor(async () => {
            userEvent.selectOptions(use, 'H');
            userEvent.selectOptions(type, 'PH');
            // warning says no effect, but it lies
            userEvent.click(buttons[0]); // Add phone & email button
        });

        await waitFor(async () => {
            const date = internalizeDate(new Date());

            expect(onChange).toHaveBeenNthCalledWith(1, [
                {
                    asOf: date,
                    type: 'PH',
                    use: 'H'
                }
            ]);
        });
    });
});
