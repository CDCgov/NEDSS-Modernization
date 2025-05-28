import { PatientData } from 'apps/deduplication/api/model/PatientData';
import { PhoneEmailDataTable } from './PhoneEmailDataTable';
import { render, within } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';

const patientData: Partial<PatientData> = {
    phoneEmails: [
        {
            id: '123',
            asOf: '2022-06-07T14:24:44.970',
            type: 'Cellular Phone',
            use: 'Home',
            countryCode: '1',
            phoneNumber: '1234445555',
            extension: '12',
            email: 'email@email.com',
            url: 'url@url.com',
            comments: 'phone comment'
        },
        {
            id: '321',
            asOf: '2020-01-01T14:24:44.970',
            type: 'Type',
            use: 'Use',
            countryCode: '2',
            phoneNumber: '2222222222',
            extension: '3',
            email: 'email2@email.com',
            url: 'google.com'
        }
    ]
};
const onViewPhoneEmail = jest.fn();
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <PhoneEmailDataTable onViewPhoneEmail={onViewPhoneEmail} patientData={patientData as PatientData} />
        </FormProvider>
    );
};
describe('PhoneEmailDataTable', () => {
    it('should render proper headers', () => {
        const { getAllByRole } = render(<Fixture />);

        const headers = getAllByRole('columnheader');

        expect(headers).toHaveLength(5);
        expect(headers[0]).toHaveTextContent(''); // checkbox header
        expect(headers[1]).toHaveTextContent('As of');
        expect(headers[2]).toHaveTextContent('Type');
        expect(headers[3]).toHaveTextContent('Phone number');
        expect(headers[4]).toHaveTextContent(''); // view icon
    });

    it('should render data in proper format', () => {
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        expect(firstRowCells[0]).toHaveTextContent('');
        expect(firstRowCells[1]).toHaveTextContent('06/07/2022');
        expect(firstRowCells[2]).toHaveTextContent('Cellular Phone/Home');
        expect(firstRowCells[3]).toHaveTextContent('123-444-5555');
        expect(firstRowCells[4]).toHaveTextContent('');

        const secondRowCells = within(rows[2]).getAllByRole('cell');
        expect(secondRowCells[0]).toHaveTextContent('');
        expect(secondRowCells[1]).toHaveTextContent('01/01/2020');
        expect(secondRowCells[2]).toHaveTextContent('Type/Use');
        expect(secondRowCells[3]).toHaveTextContent('222-222-2222');
        expect(secondRowCells[4]).toHaveTextContent('');
    });

    it('should call on select when checkbox is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        await user.click(firstRowCells[4].children[0].children[0]);
        expect(onViewPhoneEmail).toHaveBeenCalledWith(patientData.phoneEmails![0]);

        const secondRowCells = within(rows[2]).getAllByRole('cell');

        await user.click(secondRowCells[4].children[0].children[0]);
        expect(onViewPhoneEmail).toHaveBeenCalledWith(patientData.phoneEmails![1]);
    });
});
