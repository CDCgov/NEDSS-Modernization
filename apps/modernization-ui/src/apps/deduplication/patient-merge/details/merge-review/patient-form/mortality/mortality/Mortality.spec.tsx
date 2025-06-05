import { FormProvider, useForm } from 'react-hook-form';
import { Mortality } from './Mortality';
import { MergeMortality } from 'apps/deduplication/api/model/MergeCandidate';
import { render, within } from '@testing-library/react';

const data: MergeMortality = {
    asOf: '2025-05-27T00:00:00',
    deceased: 'Yes',
    dateOfDeath: '2000-01-10T00:00:00',
    deathCity: 'Atlanta',
    deathState: 'Georgia',
    deathCounty: 'Fulton County',
    deathCountry: 'United States'
};
const Fixture = ({
    mortality = data,
    allowSelections = true
}: {
    mortality?: MergeMortality;
    allowSelections?: boolean;
}) => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <Mortality personUid="1" allowDetailedSelection={allowSelections} mortality={mortality} />
        </FormProvider>
    );
};
describe('Mortality', () => {
    it('should render proper label and values', () => {
        const { getByText } = render(<Fixture />);

        const asOf = getByText('As of date');
        expect(asOf).toBeInTheDocument();
        expect(within(asOf.parentElement!).getByText('05/27/2025')).toBeInTheDocument();

        const deceased = getByText('Is the patient deceased?');
        expect(deceased).toBeInTheDocument();
        expect(within(deceased.parentElement!).getByText('Yes')).toBeInTheDocument();

        const dateOfDeath = getByText('Date of death');
        expect(dateOfDeath).toBeInTheDocument();
        expect(within(dateOfDeath.parentElement!).getByText('01/10/2000')).toBeInTheDocument();

        const deathCity = getByText('Death city');
        expect(deathCity).toBeInTheDocument();
        expect(within(deathCity.parentElement!).getByText('Atlanta')).toBeInTheDocument();

        const deathState = getByText('Death state');
        expect(deathState).toBeInTheDocument();
        expect(within(deathState.parentElement!).getByText('Georgia')).toBeInTheDocument();

        const deathCounty = getByText('Death county');
        expect(deathCounty).toBeInTheDocument();
        expect(within(deathCounty.parentElement!).getByText('Fulton County')).toBeInTheDocument();

        const deathCountry = getByText('Death country');
        expect(deathCountry).toBeInTheDocument();
        expect(within(deathCountry.parentElement!).getByText('United States')).toBeInTheDocument();
    });

    it('should render --- for missing values', () => {
        const { getByText } = render(<Fixture mortality={{}} />);

        const asOf = getByText('As of date');
        expect(asOf).toBeInTheDocument();
        expect(within(asOf.parentElement!).getByText('---')).toBeInTheDocument();

        const deceased = getByText('Is the patient deceased?');
        expect(deceased).toBeInTheDocument();
        expect(within(deceased.parentElement!).getByText('---')).toBeInTheDocument();

        const dateOfDeath = getByText('Date of death');
        expect(dateOfDeath).toBeInTheDocument();
        expect(within(dateOfDeath.parentElement!).getByText('---')).toBeInTheDocument();

        const deathCity = getByText('Death city');
        expect(deathCity).toBeInTheDocument();
        expect(within(deathCity.parentElement!).getByText('---')).toBeInTheDocument();

        const deathState = getByText('Death state');
        expect(deathState).toBeInTheDocument();
        expect(within(deathState.parentElement!).getByText('---')).toBeInTheDocument();

        const deathCounty = getByText('Death county');
        expect(deathCounty).toBeInTheDocument();
        expect(within(deathCounty.parentElement!).getByText('---')).toBeInTheDocument();

        const deathCountry = getByText('Death country');
        expect(deathCountry).toBeInTheDocument();
        expect(within(deathCountry.parentElement!).getByText('---')).toBeInTheDocument();
    });

    it('should allow each field to be selectable when allowDetailedSelection is true', () => {
        const { getAllByRole } = render(<Fixture allowSelections={true} />);

        const checkBoxes = getAllByRole('radio');
        expect(checkBoxes).toHaveLength(5);
    });

    it('should only allow as of date to be selectable when allowDetailedSelection is false', () => {
        const { getAllByRole } = render(<Fixture allowSelections={false} />);

        const checkBoxes = getAllByRole('radio');
        expect(checkBoxes).toHaveLength(1);
    });

    it('should only allow as of date to be selectable when deceased value is not "Yes"', () => {
        const data: MergeMortality = {
            asOf: '2025-05-27T00:00:00',
            deceased: 'No',
            dateOfDeath: '2000-01-10T00:00:00',
            deathCity: 'Atlanta',
            deathState: 'Georgia',
            deathCounty: 'Fulton County',
            deathCountry: 'United States'
        };
        const { getAllByRole } = render(<Fixture allowSelections={true} mortality={data} />);

        const checkBoxes = getAllByRole('radio');
        expect(checkBoxes).toHaveLength(1);
    });
});
