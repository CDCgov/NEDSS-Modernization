import { FormProvider, useForm } from 'react-hook-form';
import { render, waitFor, within } from '@testing-library/react';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { ExtendedNewPatientEntry, initial } from './entry';
import { internalizeDate } from 'date';
import { ValidationErrors } from './useAddExtendedPatientInteraction';
import { Selectable } from 'options';

const mockStateCodedValues = [{ name: 'StateName', value: '1' }];

const mockCountryCodedValues = [{ name: 'CountryName', value: '3' }];

const mockCountyCodedValues = [{ name: 'CountyName', value: '2' }];

jest.mock('options/location', () => ({
    useCountyOptions: () => mockCountyCodedValues,
    useCountryOptions: () => mockCountryCodedValues,
    useStateOptions: () => mockStateCodedValues
}));

window.HTMLElement.prototype.scrollIntoView = jest.fn();

const mockLocationCodedValues = {
    states: {
        all: [{ name: 'StateName', value: '1' }]
    },
    counties: {
        byState: () => [{ name: 'CountyName', value: '2' }]
    },
    countries: [{ name: 'CountryName', value: '3' }]
};

jest.mock('location/useLocationCodedValues', () => ({
    useLocationCodedValues: () => mockLocationCodedValues
}));
const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

const mockRaceCategories: Selectable[] = [{ value: '1', name: 'race name' }];

const mockDetailedRaces: Selectable[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('options/race', () => ({
    useRaceCategoryOptions: () => mockRaceCategories,
    useDetailedRaceOptions: () => mockDetailedRaces
}));

type Props = {
    asOf?: string;
    validationErrors?: ValidationErrors;
};

const Fixture = ({ asOf, validationErrors }: Props) => {
    const defaultValues = initial(asOf);

    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues,
        mode: 'onBlur'
    });

    return (
        <FormProvider {...form}>
            <AddPatientExtendedForm setSubFormState={jest.fn()} validationErrors={validationErrors} />
        </FormProvider>
    );
};
describe('AddPatientExtendedForm', () => {
    it('should render the sections with appropriate help text', async () => {
        const { getAllByText, getByText, getAllByRole } = render(<Fixture />);

        await waitFor(() => expect(getByText('Administrative')).toBeInTheDocument());

        const headers = getAllByRole('heading');
        const requiredTexts = getAllByText('Required');

        expect(headers[0]).toHaveTextContent('Administrative');
        expect(headers[0].parentElement?.parentElement).toContainElement(requiredTexts[0]);

        expect(headers[1]).toHaveTextContent('Name');
        expect(headers[1].parentElement?.parentElement).toContainElement(requiredTexts[1]);

        expect(headers[2]).toHaveTextContent('Address');
        expect(headers[2].parentElement?.parentElement).toContainElement(requiredTexts[2]);

        expect(headers[3]).toHaveTextContent('Phone & email');
        expect(headers[3].parentElement?.parentElement).toContainElement(requiredTexts[3]);

        expect(headers[4]).toHaveTextContent('Identification');
        expect(headers[4].parentElement?.parentElement).toContainElement(requiredTexts[4]);

        expect(headers[5]).toHaveTextContent('Race');
        expect(headers[5].parentElement?.parentElement).toContainElement(requiredTexts[5]);

        expect(headers[6]).toHaveTextContent('Ethnicity');
        expect(headers[6].parentElement?.parentElement).toContainElement(requiredTexts[6]);

        expect(headers[7]).toHaveTextContent('Sex & birth');
        expect(headers[7].parentElement?.parentElement).toContainElement(requiredTexts[7]);

        expect(headers[8]).toHaveTextContent('Mortality');
        expect(headers[8].parentElement?.parentElement).toContainElement(requiredTexts[8]);

        expect(headers[9]).toHaveTextContent('General patient information');
        expect(headers[9].parentElement?.parentElement).toContainElement(requiredTexts[9]);
    });

    it('should set default date for as of fields', async () => {
        const { getByLabelText } = render(<Fixture asOf="05/07/1977" />);

        //  The Repeating block as of dates are being initialized to today's date within the component.
        const expected = internalizeDate(new Date());

        await waitFor(() => {
            expect(getByLabelText('Information as of date')).toHaveValue('05/07/1977');

            expect(getByLabelText('Name as of')).toHaveValue(expected);

            expect(getByLabelText('Address as of')).toHaveValue(expected);

            expect(getByLabelText('Phone & email as of')).toHaveValue(expected);

            expect(getByLabelText('Identification as of')).toHaveValue(expected);

            expect(getByLabelText('Race as of')).toHaveValue(expected);

            expect(getByLabelText('Ethnicity information as of')).toHaveValue('05/07/1977');

            expect(getByLabelText('Sex & birth information as of')).toHaveValue('05/07/1977');

            expect(getByLabelText('Mortality information as of')).toHaveValue('05/07/1977');

            expect(getByLabelText('General information as of')).toHaveValue('05/07/1977');
        });
    });

    it('should display validation errors', () => {
        const { getAllByText, getAllByRole } = render(
            <Fixture
                validationErrors={{
                    dirtySections: { name: true, phone: true, address: true, identification: true, race: true }
                }}
            />
        );

        expect(getAllByText('Please fix the following errors:')).toHaveLength(6);

        const errors = within(getAllByRole('list')[0]).getAllByRole('listitem');
        expect(errors).toHaveLength(5);
        // Name error
        expect(errors[0]).toHaveTextContent(
            'Data have been entered in the Name section. Please press Add or clear the data and submit again.'
        );
        let link = within(errors[0]).getByRole('link');
        expect(link).toHaveTextContent('Name');
        expect(link).toHaveAttribute('href', '#names');

        // Address
        expect(errors[1]).toHaveTextContent(
            'Data have been entered in the Address section. Please press Add or clear the data and submit again.'
        );
        link = within(errors[1]).getByRole('link');
        expect(link).toHaveTextContent('Address');
        expect(link).toHaveAttribute('href', '#addresses');

        // Phone & Email
        expect(errors[2]).toHaveTextContent(
            'Data have been entered in the Phone & Email section. Please press Add or clear the data and submit again.'
        );
        link = within(errors[2]).getByRole('link');
        expect(link).toHaveTextContent('Phone & Email');
        expect(link).toHaveAttribute('href', '#phoneEmails');

        // Identification
        expect(errors[3]).toHaveTextContent(
            'Data have been entered in the Identification section. Please press Add or clear the data and submit again.'
        );
        link = within(errors[3]).getByRole('link');
        expect(link).toHaveTextContent('Identification');
        expect(link).toHaveAttribute('href', '#identifications');

        // Race
        expect(errors[4]).toHaveTextContent(
            'Data have been entered in the Race section. Please press Add or clear the data and submit again.'
        );
        link = within(errors[4]).getByRole('link');
        expect(link).toHaveTextContent('Race');
        expect(link).toHaveAttribute('href', '#races');
    });
});
