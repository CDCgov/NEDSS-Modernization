import { PatientData } from 'apps/deduplication/api/model/PatientData';
import { NameDataTable } from './NameDataTable';
import { render, within } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';

const patientData: Partial<PatientData> = {
    names: [
        {
            personUid: '1',
            sequence: '1',
            asOf: '2022-06-07T14:24:44.970',
            type: 'Legal',
            first: 'John',
            last: 'Doe'
        },
        {
            personUid: '1',
            sequence: '2',
            asOf: '2020-01-01T14:24:44.970',
            type: 'Alias',
            first: 'Johnathan',
            last: 'Doer'
        }
    ]
};
const onSelectName = jest.fn();
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <NameDataTable onViewName={onSelectName} patientData={patientData as PatientData} />
        </FormProvider>
    );
};
describe('NameDataTable', () => {
    it('should render proper headers', () => {
        const { getAllByRole } = render(<Fixture />);

        const headers = getAllByRole('columnheader');

        expect(headers).toHaveLength(5);
        expect(headers[0]).toHaveTextContent(''); // checkbox header
        expect(headers[1]).toHaveTextContent('As of');
        expect(headers[2]).toHaveTextContent('Type');
        expect(headers[3]).toHaveTextContent('Name');
        expect(headers[4]).toHaveTextContent(''); // view icon
    });

    it('should render data in proper format', () => {
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        expect(firstRowCells[0]).toHaveTextContent('');
        expect(firstRowCells[1]).toHaveTextContent('06/07/2022');
        expect(firstRowCells[2]).toHaveTextContent('Legal');
        expect(firstRowCells[3]).toHaveTextContent('Doe, John');
        expect(firstRowCells[4]).toHaveTextContent('');

        const secondRowCells = within(rows[2]).getAllByRole('cell');
        expect(secondRowCells[0]).toHaveTextContent('');
        expect(secondRowCells[1]).toHaveTextContent('01/01/2020');
        expect(secondRowCells[2]).toHaveTextContent('Alias');
        expect(secondRowCells[3]).toHaveTextContent('Doer, Johnathan');
        expect(secondRowCells[4]).toHaveTextContent('');
    });

    it('should call on select when checkbox is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        await user.click(firstRowCells[4].children[0].children[0]);
        expect(onSelectName).toHaveBeenCalledWith(patientData.names![0]);

        const secondRowCells = within(rows[2]).getAllByRole('cell');

        await user.click(secondRowCells[4].children[0].children[0]);
        expect(onSelectName).toHaveBeenCalledWith(patientData.names![1]);
    });
});
