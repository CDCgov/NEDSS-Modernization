import { PatientData } from 'apps/deduplication/api/model/PatientData';
import { render, within } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';
import { RaceDataTable } from './RaceDataTable';

const patientData: Partial<PatientData> = {
    races: [
        {
            personUid: '1',
            raceCode: '2106-3',
            asOf: '2014-03-11T00:00:00.000',
            race: 'White',
            detailedRaces: 'European'
        },
        {
            personUid: '1',
            raceCode: '2028-9',
            asOf: '2000-01-12T00:00:00.000',
            race: 'Asian',
            detailedRaces: 'Asian Indian | Bangladeshi | Bhutanese | Burmese'
        }
    ]
};
const onViewRace = jest.fn();
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <RaceDataTable onViewRace={onViewRace} patientData={patientData as PatientData} />
        </FormProvider>
    );
};
describe('RaceDataTable', () => {
    it('should render proper headers', () => {
        const { getAllByRole } = render(<Fixture />);

        const headers = getAllByRole('columnheader');

        expect(headers).toHaveLength(5);
        expect(headers[0]).toHaveTextContent(''); // checkbox header
        expect(headers[1]).toHaveTextContent('As of');
        expect(headers[2]).toHaveTextContent('Race');
        expect(headers[3]).toHaveTextContent('Detailed race');
        expect(headers[4]).toHaveTextContent(''); // view icon
    });

    it('should render data in proper format', () => {
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        expect(firstRowCells[0]).toHaveTextContent('');
        expect(firstRowCells[1]).toHaveTextContent('03/11/2014');
        expect(firstRowCells[2]).toHaveTextContent('White');
        expect(firstRowCells[3]).toHaveTextContent('European');
        expect(firstRowCells[4]).toHaveTextContent('');

        const secondRowCells = within(rows[2]).getAllByRole('cell');
        expect(secondRowCells[0]).toHaveTextContent('');
        expect(secondRowCells[1]).toHaveTextContent('01/12/2000');
        expect(secondRowCells[2]).toHaveTextContent('Asian');
        expect(secondRowCells[3]).toHaveTextContent('Asian Indian | Bangladeshi | Bhutanese | Burmese');
        expect(secondRowCells[4]).toHaveTextContent('');
    });

    it('should call on select when checkbox is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        await user.click(firstRowCells[4].children[0].children[0]);
        expect(onViewRace).toHaveBeenCalledWith(patientData.races![0]);

        const secondRowCells = within(rows[2]).getAllByRole('cell');

        await user.click(secondRowCells[4].children[0].children[0]);
        expect(onViewRace).toHaveBeenCalledWith(patientData.races![1]);
    });
});
