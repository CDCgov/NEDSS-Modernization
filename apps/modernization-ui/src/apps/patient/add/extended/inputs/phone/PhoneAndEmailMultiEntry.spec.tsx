import { screen, render, waitFor } from '@testing-library/react';
import { PhoneAndEmailMultiEntry } from './PhoneAndEmailMultiEntry';
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

describe('PhoneAndEmailMultiEntry', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(<PhoneAndEmailMultiEntry onChange={onChange} isDirty={isDirty} />);
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
        const { getByLabelText } = render(<PhoneAndEmailMultiEntry onChange={onChange} isDirty={isDirty} />);
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
        const { getByLabelText, getAllByRole } = render(
            <PhoneAndEmailMultiEntry onChange={onChange} isDirty={isDirty} />
        );
        // wait on render to prevent act warning
        await awaitRender();
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');
        const countryCode = getByLabelText('Country code');
        const phoneNumber = getByLabelText('Phone number');
        const extension = getByLabelText('Extension');
        const email = getByLabelText('Email');
        const url = getByLabelText('URL');
        const comments = getByLabelText('Phone & email comments');
        const buttons = getAllByRole('button');

        await waitFor(async () => {
            userEvent.selectOptions(use, 'H');
            userEvent.selectOptions(type, 'PH');
            // warning says no effect, but it lies
            await userEvent.type(countryCode, '1');
            await userEvent.type(phoneNumber, '1234567890');
            await userEvent.type(extension, '2');
            await userEvent.type(email, 'email@email.com');
            await userEvent.type(url, 'url');
            await userEvent.type(comments, 'comments go here');
            userEvent.click(buttons[1]); // Add phone & email button
        });

        await waitFor(async () => {
            const date = internalizeDate(new Date());

            expect(onChange).toHaveBeenNthCalledWith(2, [
                {
                    asOf: date,
                    comment: 'comments go here',
                    countryCode: '1',
                    email: 'email@email.com',
                    extension: '2',
                    phoneNumber: '123-456-7890',
                    type: mockPatientPhoneCodedValues.types[0],
                    url: 'url',
                    use: mockPatientPhoneCodedValues.uses[0]
                }
            ]);
        });
    });
});
