import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { RaceSelection } from './RaceSelection';

const mergeCandidates: Partial<MergeCandidate>[] = [
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
            <RaceSelection mergeCandidates={mergeCandidates as MergeCandidate[]} />
        </FormProvider>
    );
};
describe('RaceSelection', () => {
    it('should have the proper section title', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('RACE')).toBeInTheDocument();
    });
});
