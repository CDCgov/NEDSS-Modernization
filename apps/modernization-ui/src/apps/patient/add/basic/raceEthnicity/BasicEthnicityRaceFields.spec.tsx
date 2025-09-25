import { vi } from 'vitest';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicRaceEthnicityFields } from './BasicEthnicityRaceFields';
import { BasicEthnicityRace } from '../entry';
import { Selectable } from 'options';

let mockRaceCategories: Selectable[] = [];

const mockEthnicityValues = {
    ethnicGroups: [
        { value: 'hispanic', name: 'Hispanic or Latino' },
        { value: 'not-hispanic', name: 'Not Hispanic or Latino' }
    ]
};

vi.mock('apps/patient/data/ethnicity/useEthnicityCodedValues', () => ({
    useEthnicityCodedValues: () => mockEthnicityValues
}));

vi.mock('options/race', () => ({
    useRaceCategoryOptions: () => mockRaceCategories
}));

const FormWrapper = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
    const form = useForm<BasicEthnicityRace>({
        mode: 'onBlur',
        defaultValues: {
            ethnicity: undefined,
            races: undefined
        }
    });
    return (
        <FormProvider {...form}>
            <BasicRaceEthnicityFields sizing={props.sizing} />
        </FormProvider>
    );
};

describe('BasicRaceEthnicityFields', () => {
    beforeEach(() => {
        mockRaceCategories = [];
    });

    it('should allows selecting an ethnicity option', async () => {
        const { getByLabelText, getByText } = render(<FormWrapper />);

        const ethnicitySelect = getByLabelText(/ethnicity/i);
        await userEvent.click(ethnicitySelect);

        const hispanicOption = getByText('Hispanic or Latino');
        await userEvent.click(hispanicOption);

        expect(getByText('Hispanic or Latino')).toBeInTheDocument();
    });

    it('should allows selecting multiple race options', async () => {
        mockRaceCategories = [
            { value: 'asian', name: 'Asian' },
            { value: 'white', name: 'White' },
            { value: 'black', name: 'Black or African American' }
        ];

        const { getByLabelText } = render(<FormWrapper />);

        const asianCheckbox = getByLabelText(/asian/i);
        const whiteCheckbox = getByLabelText(/white/i);

        await userEvent.click(asianCheckbox);
        await userEvent.click(whiteCheckbox);

        expect(asianCheckbox).toBeChecked();
        expect(whiteCheckbox).toBeChecked();
    });

    it('should not render race field when no race options are available', () => {
        const { queryByLabelText } = render(<FormWrapper />);

        expect(queryByLabelText(/race/i)).not.toBeInTheDocument();
    });
});
