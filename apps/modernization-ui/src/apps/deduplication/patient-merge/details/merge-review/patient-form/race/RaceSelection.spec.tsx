import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import { RaceSelection } from './RaceSelection';

const patientData: Partial<MergePatient>[] = [
    {
        races: [
            {
                personUid: '1',
                raceCode: '2106-3',
                asOf: '2014-03-11T00:00:00.000',
                race: 'White',
                detailedRaces: 'European'
            }
        ]
    }
];
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <RaceSelection patientData={patientData as MergePatient[]} />
        </FormProvider>
    );
};
describe('RaceSelection', () => {
    it('should have the proper section title', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('RACE')).toBeInTheDocument();
    });
});
