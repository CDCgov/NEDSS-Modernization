import { useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { MortalityDemographicFields } from './MortalityDemographicFields';
import { HasMortalityDemographic, initial } from '../mortality';
import { useMortalityOptions } from './useMortalityOptions';

const mockStateCodedValues = [{ name: 'StateName', value: '1' }];

const mockCountryCodedValues = [{ name: 'CountryName', value: '3' }];

const mockCountyCodedValues = [{ name: 'CountyName', value: '2' }];

jest.mock('options/location', () => ({
    useCountyOptions: () => mockCountyCodedValues,
    useCountryOptions: () => mockCountryCodedValues,
    useStateOptions: () => mockStateCodedValues
}));

const Fixture = () => {
    const form = useForm<HasMortalityDemographic>({
        mode: 'onBlur',
        defaultValues: { mortality: initial() }
    });

    const options = useMortalityOptions();

    return <MortalityDemographicFields form={form} options={options} />;
};
describe('when entering patient mortality demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        expect(getByLabelText('Mortality information as of')).toBeInTheDocument();
        expect(getByLabelText('Is the patient deceased?')).toBeInTheDocument();
        expect(queryByLabelText('Date of death')).not.toBeInTheDocument();
        expect(queryByLabelText('City of death')).not.toBeInTheDocument();
        expect(queryByLabelText('State of death')).not.toBeInTheDocument();
        expect(queryByLabelText('County of death')).not.toBeInTheDocument();
        expect(queryByLabelText('Country of death')).not.toBeInTheDocument();
    });

    it('should require as of', async () => {
        const user = userEvent.setup();
        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Mortality information as of');

        await user.clear(asOf);
        await user.tab();

        expect(await findByText('The Mortality information as of is required.')).toBeInTheDocument();
    });

    it('should render the death information when deceased is true', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        const deceased = getByLabelText('Is the patient deceased?');

        await user.selectOptions(deceased, 'Y');

        expect(getByLabelText('Date of death')).toBeInTheDocument();
        expect(getByLabelText('City of death')).toBeInTheDocument();
        expect(getByLabelText('State of death')).toBeInTheDocument();
        expect(getByLabelText('County of death')).toBeInTheDocument();
        expect(getByLabelText('Country of death')).toBeInTheDocument();
    });

    it('should reset the death information when deceased is reverted to false', async () => {
        const user = userEvent.setup();

        const { getByLabelText } = render(<Fixture />);
        // Set patient deceased Y
        await user.selectOptions(getByLabelText('Is the patient deceased?'), 'Y');

        // Enter data in previously hidden fields
        await user.type(getByLabelText('Date of death'), '12/01/2020');
        await user.type(getByLabelText('City of death'), 'City value');
        await user.selectOptions(getByLabelText('State of death'), '1');
        await user.selectOptions(getByLabelText('County of death'), '2');
        await user.selectOptions(getByLabelText('Country of death'), '3');

        // Toggle deceased to N then back to Y
        await user.selectOptions(getByLabelText('Is the patient deceased?'), 'N');
        await user.selectOptions(getByLabelText('Is the patient deceased?'), 'Y');

        // Verify previously input data is cleared
        expect(getByLabelText('Date of death')).toHaveValue('');
        expect(getByLabelText('City of death')).toHaveValue('');
        expect(getByLabelText('State of death')).toHaveValue('');
        expect(getByLabelText('County of death')).toHaveValue('');
        expect(getByLabelText('Country of death')).toHaveValue('');
    });
});
