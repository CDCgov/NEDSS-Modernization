import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { IdentificationDataTable } from './IdentificationDataTable';
import { render, within } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';

const patientData: Partial<MergeCandidate> = {
    identifications: [
        {
            personUid: '1',
            sequence: '1',
            asOf: '2014-03-11T00:00:00.000',
            type: "Driver's license",
            assigningAuthority: 'TX',
            value: '10001'
        },
        {
            personUid: '1',
            sequence: '2',
            asOf: '2024-03-21T00:00:00.000',
            type: 'Account number',
            value: '313'
        }
    ]
};
const onViewIdentification = jest.fn();
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <IdentificationDataTable
                onViewIdentification={onViewIdentification}
                patientData={patientData as MergeCandidate}
            />
        </FormProvider>
    );
};
describe('IdentificationDataTable', () => {
    it('should render proper headers', () => {
        const { getAllByRole } = render(<Fixture />);

        const headers = getAllByRole('columnheader');

        expect(headers).toHaveLength(5);
        expect(headers[0]).toHaveTextContent(''); // checkbox header
        expect(headers[1]).toHaveTextContent('As of');
        expect(headers[2]).toHaveTextContent('Type');
        expect(headers[3]).toHaveTextContent('ID value');
        expect(headers[4]).toHaveTextContent(''); // view icon
    });

    it('should render data in proper format', () => {
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        expect(firstRowCells[0]).toHaveTextContent('');
        expect(firstRowCells[1]).toHaveTextContent('03/11/2014');
        expect(firstRowCells[2]).toHaveTextContent("Driver's license");
        expect(firstRowCells[3]).toHaveTextContent('10001');
        expect(firstRowCells[4]).toHaveTextContent('');

        const secondRowCells = within(rows[2]).getAllByRole('cell');
        expect(secondRowCells[0]).toHaveTextContent('');
        expect(secondRowCells[1]).toHaveTextContent('03/21/2024');
        expect(secondRowCells[2]).toHaveTextContent('Account number');
        expect(secondRowCells[3]).toHaveTextContent('313');
        expect(secondRowCells[4]).toHaveTextContent('');
    });

    it('should call on select when checkbox is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        await user.click(firstRowCells[4].children[0].children[0]);
        expect(onViewIdentification).toHaveBeenCalledWith(patientData.identifications![0]);

        const secondRowCells = within(rows[2]).getAllByRole('cell');

        await user.click(secondRowCells[4].children[0].children[0]);
        expect(onViewIdentification).toHaveBeenCalledWith(patientData.identifications![1]);
    });
});
