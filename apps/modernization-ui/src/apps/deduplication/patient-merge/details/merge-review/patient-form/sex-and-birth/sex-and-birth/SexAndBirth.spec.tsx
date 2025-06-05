import { MergeSexAndBirth } from 'apps/deduplication/api/model/MergeCandidate';
import { SexAndBirth } from './SexAndBirth';
import { render, within } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';

const data: MergeSexAndBirth = {
    asOf: '2025-05-27T00:00:00',
    dateOfBirth: '2020-05-12T00:00:00',
    currentSex: 'Male',
    sexUnknown: 'Refused',
    transgender: 'Did not ask',
    additionalGender: 'Additional Gender',
    birthGender: 'Male',
    multipleBirth: 'No',
    birthOrder: '1',
    birthCity: 'Some city',
    birthState: 'Tennessee',
    birthCounty: 'Some county',
    birthCountry: 'United States'
};
const Fixture = ({ sexAndBirth = data }: { sexAndBirth?: MergeSexAndBirth }) => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <SexAndBirth personUid="1" sexAndBirth={sexAndBirth} />
        </FormProvider>
    );
};
describe('SexAndBirth', () => {
    it('should render proper label and value', () => {
        const { getByText } = render(<Fixture />);

        const asOf = getByText('As of date');
        expect(asOf).toBeInTheDocument();
        expect(within(asOf.parentElement!).getByText('05/27/2025')).toBeInTheDocument();

        const dob = getByText('DOB');
        expect(dob).toBeInTheDocument();
        expect(within(dob.parentElement!).getByText('05/12/2020')).toBeInTheDocument();

        const age = getByText('Current age');
        expect(age).toBeInTheDocument();
        expect(within(age.parentElement!).getByText('5 years')).toBeInTheDocument();

        const sex = getByText('Current sex');
        expect(sex).toBeInTheDocument();
        expect(within(sex.parentElement!).getByText('Male')).toBeInTheDocument();

        const unknownReason = getByText('Unknown reason');
        expect(unknownReason).toBeInTheDocument();
        expect(within(unknownReason.parentElement!).getByText('Refused')).toBeInTheDocument();

        const transgender = getByText('Transgender information');
        expect(transgender).toBeInTheDocument();
        expect(within(transgender.parentElement!).getByText('Did not ask')).toBeInTheDocument();

        const additionalGender = getByText('Additional gender');
        expect(additionalGender).toBeInTheDocument();
        expect(within(additionalGender.parentElement!).getByText('Additional gender')).toBeInTheDocument();

        const birthSex = getByText('Birth sex');
        expect(birthSex).toBeInTheDocument();
        expect(within(birthSex.parentElement!).getByText('Male')).toBeInTheDocument();

        const multipleBirth = getByText('Multiple birth');
        expect(multipleBirth).toBeInTheDocument();
        expect(within(multipleBirth.parentElement!).getByText('No')).toBeInTheDocument();

        const birthOrder = getByText('Birth order');
        expect(birthOrder).toBeInTheDocument();
        expect(within(birthOrder.parentElement!).getByText('1')).toBeInTheDocument();

        const birthCity = getByText('Birth city');
        expect(birthCity).toBeInTheDocument();
        expect(within(birthCity.parentElement!).getByText('Some city')).toBeInTheDocument();

        const birthState = getByText('Birth state');
        expect(birthState).toBeInTheDocument();
        expect(within(birthState.parentElement!).getByText('Tennessee')).toBeInTheDocument();

        const birthCounty = getByText('Birth county');
        expect(birthCounty).toBeInTheDocument();
        expect(within(birthCounty.parentElement!).getByText('Some county')).toBeInTheDocument();

        const birthCountry = getByText('Birth country');
        expect(birthCountry).toBeInTheDocument();
        expect(within(birthCountry.parentElement!).getByText('United States')).toBeInTheDocument();
    });

    it('should render --- for missing values', () => {
        const { getByText } = render(<Fixture sexAndBirth={{}} />);

        const asOf = getByText('As of date');
        expect(asOf).toBeInTheDocument();
        expect(within(asOf.parentElement!).getByText('---')).toBeInTheDocument();

        const dob = getByText('DOB');
        expect(dob).toBeInTheDocument();
        expect(within(dob.parentElement!).getByText('---')).toBeInTheDocument();

        const age = getByText('Current age');
        expect(age).toBeInTheDocument();
        expect(within(age.parentElement!).getByText('---')).toBeInTheDocument();

        const sex = getByText('Current sex');
        expect(sex).toBeInTheDocument();
        expect(within(sex.parentElement!).getByText('---')).toBeInTheDocument();

        const unknownReason = getByText('Unknown reason');
        expect(unknownReason).toBeInTheDocument();
        expect(within(unknownReason.parentElement!).getByText('---')).toBeInTheDocument();

        const transgender = getByText('Transgender information');
        expect(transgender).toBeInTheDocument();
        expect(within(transgender.parentElement!).getByText('---')).toBeInTheDocument();

        const additionalGender = getByText('Additional gender');
        expect(additionalGender).toBeInTheDocument();
        expect(within(additionalGender.parentElement!).getByText('---')).toBeInTheDocument();

        const birthSex = getByText('Birth sex');
        expect(birthSex).toBeInTheDocument();
        expect(within(birthSex.parentElement!).getByText('---')).toBeInTheDocument();

        const multipleBirth = getByText('Multiple birth');
        expect(multipleBirth).toBeInTheDocument();
        expect(within(multipleBirth.parentElement!).getByText('---')).toBeInTheDocument();

        const birthOrder = getByText('Birth order');
        expect(birthOrder).toBeInTheDocument();
        expect(within(birthOrder.parentElement!).getByText('---')).toBeInTheDocument();

        const birthCity = getByText('Birth city');
        expect(birthCity).toBeInTheDocument();
        expect(within(birthCity.parentElement!).getByText('---')).toBeInTheDocument();

        const birthState = getByText('Birth state');
        expect(birthState).toBeInTheDocument();
        expect(within(birthState.parentElement!).getByText('---')).toBeInTheDocument();

        const birthCounty = getByText('Birth county');
        expect(birthCounty).toBeInTheDocument();
        expect(within(birthCounty.parentElement!).getByText('---')).toBeInTheDocument();

        const birthCountry = getByText('Birth country');
        expect(birthCountry).toBeInTheDocument();
        expect(within(birthCountry.parentElement!).getByText('---')).toBeInTheDocument();
    });
});
