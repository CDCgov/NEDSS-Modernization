import { PatientData } from 'apps/deduplication/api/model/PatientData';
import { AddressDataTable } from './AddressDataTable';
import { render, within } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';

const patientData: Partial<PatientData> = {
    addresses: [
        {
            id: '123',
            asOf: '2022-06-07T14:24:44.970',
            type: 'House',
            use: 'Home',
            address: '123 Main st',
            address2: 'Building 2',
            city: 'Atlanta',
            state: 'Georgia',
            zipcode: '12345',
            county: 'Fulton county',
            censusTract: '0224',
            country: 'United States',
            comments: 'Comment'
        },
        {
            id: '321',
            asOf: '2020-01-01T14:24:44.970',
            type: 'Type',
            use: 'Use',
            address: '222 Second st',
            city: 'Atlanta',
            state: 'Georgia',
            zipcode: '44112',
            county: 'Dekalb county',
            country: 'United States'
        }
    ]
};
const onViewAddress = jest.fn();
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <AddressDataTable onViewAddress={onViewAddress} patientData={patientData as PatientData} />
        </FormProvider>
    );
};
describe('AddressDataTable', () => {
    it('should render proper headers', () => {
        const { getAllByRole } = render(<Fixture />);

        const headers = getAllByRole('columnheader');

        expect(headers).toHaveLength(5);
        expect(headers[0]).toHaveTextContent(''); // checkbox header
        expect(headers[1]).toHaveTextContent('As of');
        expect(headers[2]).toHaveTextContent('Type');
        expect(headers[3]).toHaveTextContent('Address');
        expect(headers[4]).toHaveTextContent(''); // view icon
    });

    it('should render data in proper format', () => {
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        expect(firstRowCells[0]).toHaveTextContent('');
        expect(firstRowCells[1]).toHaveTextContent('06/07/2022');
        expect(firstRowCells[2]).toHaveTextContent('House/Home');
        expect(firstRowCells[3]).toHaveTextContent('123 Main st, Building 2 Atlanta, Georgia 12345');
        expect(firstRowCells[4]).toHaveTextContent('');

        const secondRowCells = within(rows[2]).getAllByRole('cell');
        expect(secondRowCells[0]).toHaveTextContent('');
        expect(secondRowCells[1]).toHaveTextContent('01/01/2020');
        expect(secondRowCells[2]).toHaveTextContent('Type/Use');
        expect(secondRowCells[3]).toHaveTextContent('222 Second st Atlanta, Georgia 44112');
        expect(secondRowCells[4]).toHaveTextContent('');
    });

    it('should call on select when checkbox is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        await user.click(firstRowCells[4].children[0].children[0]);
        expect(onViewAddress).toHaveBeenCalledWith(patientData.addresses![0]);

        const secondRowCells = within(rows[2]).getAllByRole('cell');

        await user.click(secondRowCells[4].children[0].children[0]);
        expect(onViewAddress).toHaveBeenCalledWith(patientData.addresses![1]);
    });
});
