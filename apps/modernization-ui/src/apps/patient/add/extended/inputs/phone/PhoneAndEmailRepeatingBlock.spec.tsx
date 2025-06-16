import { screen, render, waitFor, act } from '@testing-library/react';
import { PhoneAndEmailRepeatingBlock, PhoneAndEmailRepeatingBlockProps } from './PhoneAndEmailRepeatingBlock';
import { internalizeDate } from 'date';
import userEvent from '@testing-library/user-event';

const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('apps/patient/data/phoneEmail/usePhoneCodedValues', () => ({
    usePhoneCodedValues: () => mockPatientPhoneCodedValues
}));

const awaitRender = async () => {
    // wait on render to prevent act warning
    expect(await screen.findByText('URL')).toBeInTheDocument();
};

const Fixture = ({ values, onChange = jest.fn(), isDirty = jest.fn() }: Partial<PhoneAndEmailRepeatingBlockProps>) => (
    <PhoneAndEmailRepeatingBlock id="phoneAndEmail" values={values} onChange={onChange} isDirty={isDirty} />
);

describe('PhoneAndEmailRepeatingBlock', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(
            <Fixture
                values={[
                    {
                        asOf: '07/11/1997',
                        type: { name: 'type-name', value: 'type-value' },
                        use: { name: 'use-name', value: 'use-value' }
                    }
                ]}
            />
        );
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
        const onChange = jest.fn();

        const { getByLabelText, getByRole } = render(<Fixture onChange={onChange} />);

        // wait on render to prevent act warning
        await awaitRender();

        const type = getByLabelText('Type');
        const use = getByLabelText('Use');
        const add = getByRole('button', { name: 'Add phone & email' });

        userEvent.selectOptions(use, 'H');
        userEvent.selectOptions(type, 'PH');

        act(() => {
            userEvent.click(add);
        });

        await waitFor(async () => {
            expect(onChange).toHaveBeenCalledWith(
                expect.arrayContaining([
                    expect.objectContaining({
                        type: expect.objectContaining({ name: 'Phone', value: 'PH' }),
                        use: expect.objectContaining({ name: 'Home', value: 'H' })
                    })
                ])
            );
        });
    });
});
