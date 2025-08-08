import { FormProvider, useForm } from 'react-hook-form';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { RaceDemographic } from '../race';
import { RaceDemographicFields, RaceDemographicFieldsProps } from './RaceDemographicFields';
import { RaceOptions } from './useRaceOptions';

const mockSelected = jest.fn();

type Props = Partial<RaceDemographicFieldsProps> & { entry?: RaceDemographic } & Partial<RaceOptions>;

const Fixture = ({
    categories = [],
    details = [],
    selected = mockSelected,
    categoryValidator = jest.fn().mockResolvedValue('true'),
    entry
}: Props) => {
    const form = useForm<RaceDemographic>({
        mode: 'onBlur',
        defaultValues: entry
    });

    const options = { categories, details, selected };

    return (
        <FormProvider {...form}>
            <RaceDemographicFields options={options} categoryValidator={categoryValidator} />
        </FormProvider>
    );
};

describe('Race entry fields', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('As of')).toBeInTheDocument();
        expect(getByLabelText('Race')).toBeInTheDocument();
    });

    it('detailed race should render once race is chosen', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const race = getByLabelText('Race');

        await user.type(race, '1');

        expect(getByText('Detailed race')).toBeInTheDocument();
    });

    it('detailed race values should be enabled when there are details available', async () => {
        const user = userEvent.setup();

        render(
            <Fixture
                categories={[
                    { value: 'other', name: 'other name' },
                    { value: 'selected', name: 'selected name' }
                ]}
                details={[{ value: 'detailed', name: 'detailed race' }]}
            />
        );

        const detailed = screen.getByLabelText('Detailed race');

        await user.click(detailed);

        expect(screen.getByText('detailed race')).toBeInTheDocument();
    });

    it('detailed race values should clear when the category changes', async () => {
        const entry = {
            id: 389,
            asOf: '05/08/2013',
            race: { value: 'selected', name: 'selected name' },
            detailed: [{ value: 'existing-detailed', name: 'existing detailed race name' }]
        };

        const user = userEvent.setup();

        const { rerender } = render(
            <Fixture
                entry={entry}
                categories={[
                    { value: 'other', name: 'other name' },
                    { value: 'selected', name: 'selected name' }
                ]}
            />
        );

        const race = screen.getByRole('combobox', { name: 'Race' });
        await user.selectOptions(race, 'other');

        expect(mockSelected).toBeCalledWith({ value: 'other', name: 'other name' });

        rerender(
            <Fixture
                categories={[
                    { value: 'other', name: 'other name' },
                    { value: 'selected', name: 'selected name' }
                ]}
                details={[{ value: 'other-detailed', name: 'other detailed' }]}
            />
        );

        const detailed = screen.getByLabelText('Detailed race');
        expect(detailed).toHaveValue('');
    });

    it('should be valid with as of, race', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(
            <Fixture
                categories={[
                    { value: 'other', name: 'other name' },
                    { value: 'selected', name: 'selected name' }
                ]}
            />
        );

        const asOf = getByLabelText('As of');
        const race = getByLabelText('Race');

        await user.type(asOf, '01/20/2020').then(() => user.selectOptions(race, 'other'));

        expect(queryByText('The As of is required')).not.toBeInTheDocument();
        expect(queryByText('The Race is required')).not.toBeInTheDocument();
    });

    it('should require as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const asOf = getByLabelText('As of');

        await user.clear(asOf).then(() => user.tab());

        expect(getByText(/The As of is required/)).toBeInTheDocument();
    });

    it('should require race category', async () => {
        const user = userEvent.setup();

        const { getByRole, getByText } = render(<Fixture />);

        const category = getByRole('combobox', { name: 'Race' });

        await user.click(category).then(() => user.tab());

        expect(getByText(/The Race is required/)).toBeInTheDocument();
    });

    it('should require race category to pass validation', async () => {
        const validator = jest.fn();
        validator.mockResolvedValue('category not valid');

        const { getByRole, getByText } = render(
            <Fixture
                entry={{
                    id: 19,
                    asOf: '04/11/2022',
                    race: { name: 'Selected Name', value: 'selected-value' },
                    detailed: []
                }}
                categoryValidator={validator}
                categories={[
                    { value: 'other', name: 'other name' },
                    { value: 'selected', name: 'selected name' }
                ]}
            />
        );

        const user = userEvent.setup();

        await user
            .selectOptions(getByRole('combobox', { name: 'Race' }), 'other')
            //  the select component is re-rendering when the value is set so it has
            // to be looked up again.  It should not be re-rendering...
            .then(() => user.click(getByRole('combobox', { name: 'Race' })))
            .then(() => user.tab());

        expect(getByText('category not valid')).toBeInTheDocument();
        expect(validator).toBeCalledWith(19, expect.objectContaining({ value: 'other' }));
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
