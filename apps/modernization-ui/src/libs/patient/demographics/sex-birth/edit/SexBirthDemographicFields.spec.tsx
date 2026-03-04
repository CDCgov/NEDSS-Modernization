import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { useForm, useWatch } from 'react-hook-form';
import { PatientDemographics } from '../../demographics';
import { SexBirthDemographicFields } from './SexBirthDemographicFields';
import { asOfAgeResolver } from 'date';
import { useCallback } from 'react';
import { SexBirthOptions } from './useSexBirthOptions';
import { genders } from 'options/gender';
import { indicators } from 'options/indicator';
import { LocationOptions } from 'options/location';

const mockState = jest.fn();

const location: LocationOptions = {
    states: [{ name: 'StateName', value: '1' }],
    counties: [{ name: 'CountyName', value: '2' }],
    countries: [{ name: 'CountryName', value: '3' }],
    state: mockState
};

const mockSexBirthCodedValues: SexBirthOptions = {
    genders: genders,
    preferredGenders: [{ name: 'FTM', value: 'FTM' }],
    genderUnknownReasons: [{ name: 'Did not ask', value: 'DNA' }],
    multipleBirth: indicators,
    location
};

const Fixture = () => {
    const form = useForm<PatientDemographics>({
        mode: 'onBlur'
    });

    const deceasedOn = useWatch({ control: form.control, name: 'mortality.deceasedOn' });

    const ageResolver = useCallback(asOfAgeResolver(deceasedOn), [deceasedOn]);

    return <SexBirthDemographicFields form={form} options={mockSexBirthCodedValues} ageResolver={ageResolver} />;
};

describe('when entering patient sex and birth demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText, getByText, queryByLabelText } = render(<Fixture />);

        expect(getByLabelText('As of')).toBeInTheDocument();
        expect(getByLabelText('Date of birth')).toBeInTheDocument();
        expect(getByText('Current age')).toBeInTheDocument();
        expect(getByLabelText('Current sex')).toBeInTheDocument();
        expect(queryByLabelText('Unknown reason')).not.toBeInTheDocument();
        expect(getByLabelText('Transgender information')).toBeInTheDocument();
        expect(getByLabelText('Additional gender')).toBeInTheDocument();
        expect(getByLabelText('Birth sex')).toBeInTheDocument();
        expect(getByLabelText('Multiple birth')).toBeInTheDocument();
        expect(getByLabelText('Birth city')).toBeInTheDocument();
        expect(getByLabelText('Birth state')).toBeInTheDocument();
        expect(getByLabelText('Birth county')).toBeInTheDocument();
        expect(getByLabelText('Birth country')).toBeInTheDocument();
    });

    it('should require as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('As of');

        await user.click(asOf);
        await user.tab();

        expect(await findByText('The As of is required.')).toBeInTheDocument();
    });

    it('should be valid with as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('As of');

        await user.type(asOf, '01/20/2020');
        await user.tab();

        expect(queryByText('As of is required.')).not.toBeInTheDocument();
    });

    it('should enable unknown reason when current sex is unknown', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const currentSex = getByLabelText('Current sex');
        expect(queryByLabelText('Unknown reason')).not.toBeInTheDocument();

        await user.selectOptions(currentSex, 'U');
        expect(getByLabelText('Unknown reason')).not.toBeDisabled();
    });

    it('should reset unknown reason when current sex is changed from unknown', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const currentSex = getByLabelText('Current sex');
        expect(queryByLabelText('Unknown reason')).not.toBeInTheDocument();

        await user.selectOptions(currentSex, 'U');
        expect(getByLabelText('Unknown reason')).not.toBeDisabled();

        await user.selectOptions(getByLabelText('Unknown reason'), 'DNA');
        expect(getByLabelText('Unknown reason')).toHaveValue('DNA');
    });

    it('should only render birth order', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const currentSex = getByLabelText('Multiple birth');
        expect(queryByLabelText('Birth order')).not.toBeInTheDocument();

        await user.selectOptions(currentSex, 'Y');
        expect(getByLabelText('Birth order')).toBeInTheDocument();
    });

    it('should render age only when date of birth is valid', async () => {
        const user = userEvent.setup();

        const { getByText, getByLabelText } = render(<Fixture />);
        const dateOfBirth = getByLabelText('Date of birth');

        await user.clear(dateOfBirth).then(() => user.type(dateOfBirth, '12012012{tab}'));

        expect(getByText('13 years')).toBeInTheDocument();
    });

    it('should have accessibility description for the as of date field', () => {
        const { getByLabelText } = render(<Fixture />);
        const dateInput = getByLabelText('As of');
        expect(dateInput).toHaveAttribute(
            'aria-description',
            "This field defaults to today's date and can be changed if needed."
        );
    });
});
