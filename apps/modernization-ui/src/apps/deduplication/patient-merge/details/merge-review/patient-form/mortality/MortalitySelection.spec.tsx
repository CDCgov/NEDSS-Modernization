import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { FormProvider, useForm } from 'react-hook-form';
import { MortalitySelection } from './MortalitySelection';

const data: Partial<MergeCandidate>[] = [
    {
        personUid: '1',
        mortality: {
            asOf: '2025-05-27T00:00:00',
            deceased: 'Yes',
            dateOfDeath: '2000-01-10T00:00:00',
            deathCity: 'Atlanta',
            deathState: 'Georgia',
            deathCounty: 'Fulton County',
            deathCountry: 'United States'
        }
    },
    {
        personUid: '2',
        mortality: {
            asOf: '2025-05-27T00:00:00',
            deceased: 'Yes',
            dateOfDeath: '1990-05-14T00:00:00',
            deathCity: 'Las Vegas',
            deathState: 'Nevada',
            deathCounty: 'Fulton County',
            deathCountry: 'United States'
        }
    },
    {
        personUid: '3',
        mortality: {
            asOf: '2013-03-13T00:00:00',
            deceased: 'No'
        }
    }
];
const Fixture = () => {
    const form = useForm();

    return (
        <FormProvider {...form}>
            <MortalitySelection mergeCandidates={data as MergeCandidate[]} />
        </FormProvider>
    );
};
describe('MortalitySelection', () => {
    it('should render the proper section title', () => {
        const { getAllByText } = render(<Fixture />);

        expect(getAllByText('MORTALITY')).toHaveLength(3);
    });

    it('should render radio buttons for As of date', () => {
        const { getAllByRole } = render(<Fixture />);

        expect(getAllByRole('radio')).toHaveLength(3);
    });

    it('should set values when radio button is checked', async () => {
        const user = userEvent.setup();
        const { getAllByLabelText } = render(<Fixture />);

        // Selecting the first mortality entry should also select all sub-fields due to deceased value of 'Yes'
        const firstMortality = getAllByLabelText('As of date')[0];
        await user.click(firstMortality);

        expect(getAllByLabelText('As of date')[0]).toBeChecked();
        expect(getAllByLabelText('Date of death')[0]).toBeChecked();
        expect(getAllByLabelText('Death city')[0]).toBeChecked();
        expect(getAllByLabelText('Death state')[0]).toBeChecked();
        expect(getAllByLabelText('Death country')[0]).toBeChecked();
    });

    it('should render radio buttons for all deceased fields when deceased entry is selected', async () => {
        const user = userEvent.setup();
        const { getAllByLabelText, getAllByRole } = render(<Fixture />);

        const firstMortality = getAllByLabelText('As of date')[0];
        await user.click(firstMortality);

        // all 5 fields for entries with deceased == 'Yes'. Only 'As of' field for non deceased entry
        expect(getAllByRole('radio')).toHaveLength(11);
    });

    it('should set all values when non-deceased mortality is selected', async () => {
        const user = userEvent.setup();
        const { getAllByLabelText, getAllByRole } = render(<Fixture />);

        // Selecting the first mortality entry should also select all sub-fields due to deceased value of 'Yes'
        const firstMortality = getAllByLabelText('As of date')[0];
        await user.click(firstMortality);

        const lastMortality = getAllByLabelText('As of date')[2];
        await user.click(lastMortality);

        expect(getAllByRole('radio')).toHaveLength(3);
    });

    it('should set specific value when deceased -> deceased mortality field is selected', async () => {
        const user = userEvent.setup();
        const { getAllByLabelText, getAllByRole } = render(<Fixture />);

        // Selecting the first mortality entry should also select all sub-fields due to deceased value of 'Yes'
        const firstMortality = getAllByLabelText('As of date')[0];
        await user.click(firstMortality);

        const secondMortalityDateOfDeath = getAllByLabelText('Date of death')[1];
        await user.click(secondMortalityDateOfDeath);

        expect(getAllByRole('radio')).toHaveLength(11);

        expect(getAllByLabelText('As of date')[0]).toBeChecked();
        expect(getAllByLabelText('Date of death')[0]).not.toBeChecked();
        expect(getAllByLabelText('Death city')[0]).toBeChecked();
        expect(getAllByLabelText('Death state')[0]).toBeChecked();
        expect(getAllByLabelText('Death country')[0]).toBeChecked();

        expect(getAllByLabelText('As of date')[1]).not.toBeChecked();
        expect(getAllByLabelText('Date of death')[1]).toBeChecked();
        expect(getAllByLabelText('Death city')[1]).not.toBeChecked();
        expect(getAllByLabelText('Death state')[1]).not.toBeChecked();
        expect(getAllByLabelText('Death country')[1]).not.toBeChecked();

        expect(getAllByLabelText('As of date')[2]).not.toBeChecked();
    });
});
