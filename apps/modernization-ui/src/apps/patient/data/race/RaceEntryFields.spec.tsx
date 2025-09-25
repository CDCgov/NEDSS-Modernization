import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { RaceEntry } from './entry';
import { RaceEntryFields, RaceEntryFieldsProps } from './RaceEntryFields';
import { vi } from 'vitest';

const mockDetailResolver = vi.fn();

vi.mock('options/race', () => ({
    useDetailedRaceOptions: () => ({ options: [], load: mockDetailResolver })
}));

type Props = Partial<RaceEntryFieldsProps> & { entry?: RaceEntry };

const Fixture = ({
    categories = [],
    categoryValidator = vi.fn().mockResolvedValue('true'),
    entry = {
        id: 19,
        asOf: '04/11/2022',
        race: null,
        detailed: []
    }
}: Props) => {
    const form = useForm<RaceEntry>({
        mode: 'onBlur',
        defaultValues: entry
    });

    return (
        <FormProvider {...form}>
            <RaceEntryFields categories={categories} categoryValidator={categoryValidator} />
        </FormProvider>
    );
};

describe('Race entry fields', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Race as of')).toBeInTheDocument();
        expect(getByLabelText('Race')).toBeInTheDocument();
    });

    it('detailed race should render once race is chosen', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const race = getByLabelText('Race');

        await user.type(race, '1');

        expect(getByText('Detailed race')).toBeInTheDocument();
    });

    it('detailed race values should depend on the race category', async () => {
        mockDetailResolver.mockReturnValue([{ value: 'detailed', name: 'detailed race' }]);

        const user = userEvent.setup();

        const { getByLabelText } = render(
            <Fixture
                categories={[
                    { value: 'other', name: 'other name' },
                    { value: 'selected', name: 'selected name' }
                ]}
            />
        );

        const race = getByLabelText('Race');

        await user.selectOptions(race, 'selected');

        expect(mockDetailResolver).toBeCalledWith('selected');
    });

    it('detailed race values should clear when the category changes', async () => {
        mockDetailResolver.mockReturnValue([{ value: 'other-detailed', name: 'other detailed name' }]);

        const entry = {
            id: 389,
            asOf: '05/08/2013',
            race: { value: 'selected', name: 'selected name' },
            detailed: [{ value: 'existing-detailed', name: 'existing detailed race name' }]
        };

        const user = userEvent.setup();

        const { getByLabelText } = render(
            <Fixture
                entry={entry}
                categories={[
                    { value: 'other', name: 'other name' },
                    { value: 'selected', name: 'selected name' }
                ]}
            />
        );

        const race = getByLabelText('Race');
        const detailed = getByLabelText('Detailed race');

        await user.selectOptions(race, 'other').then(() => user.click(detailed));

        expect(mockDetailResolver).toBeCalledWith('other');
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

        const asOf = getByLabelText('Race as of');
        const race = getByLabelText('Race');

        await user.type(asOf, '01/20/2020').then(() => user.selectOptions(race, 'other'));

        expect(queryByText('The Race as of is required')).not.toBeInTheDocument();
        expect(queryByText('The Race is required')).not.toBeInTheDocument();
    });

    it('should require as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const asOf = getByLabelText('Race as of');

        await user.clear(asOf).then(() => user.tab());

        expect(getByText(/The Race as of is required/)).toBeInTheDocument();
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
                categoryValidator={validator}
                categories={[
                    { value: '1', name: 'race name' },
                    { value: 'other', name: 'other name' }
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
        const dateInput = getByLabelText('Race as of');
        expect(dateInput).toHaveAttribute(
            'aria-description',
            "This field defaults to today's date and can be changed if needed."
        );
    });
});
