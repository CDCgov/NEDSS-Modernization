import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ExtendedNewPatientEntry } from 'apps/patient/add/extended';

import { FormProvider, useForm } from 'react-hook-form';
import { GeneralInformationDemographicFields } from './GeneralInformationDemographicFields';
import { GeneralInformationOptions } from './useGeneralInformationOptions';
import { indicators } from 'options/indicator';
import { PatientDemographics } from '../../demographics';

const mockGeneralCodedValues: GeneralInformationOptions = {
    maritalStatuses: [{ name: 'Married', value: 'M' }],
    educationLevels: [{ name: '1 or more years of college', value: '1' }],
    primaryOccupations: [{ name: 'Tester', value: 'T' }],
    primaryLanguages: [{ name: 'Welsh', value: 'W' }],
    speaksEnglish: indicators
};

const Fixture = () => {
    const form = useForm<PatientDemographics>({
        mode: 'onBlur'
    });

    return <GeneralInformationDemographicFields form={form} options={mockGeneralCodedValues} />;
};
describe('when entering patient general information demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        expect(getByLabelText('As of')).toBeInTheDocument();
        expect(getByLabelText('Marital status')).toBeInTheDocument();
        expect(getByLabelText("Mother's maiden name")).toBeInTheDocument();
        expect(getByLabelText('Number of adults in residence')).toBeInTheDocument();
        expect(getByLabelText('Primary occupation')).toBeInTheDocument();
        expect(getByLabelText('Highest level of education')).toBeInTheDocument();
        expect(getByLabelText('Speaks English')).toBeInTheDocument();
        expect(queryByLabelText('State HIV case ID')).not.toBeInTheDocument();
    });

    it('should require as of date', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const dateInput = getByLabelText('As of');

        const user = userEvent.setup();

        await user.click(dateInput).then(() => user.tab());

        expect(getByText('The As of is required.')).toBeInTheDocument();
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
