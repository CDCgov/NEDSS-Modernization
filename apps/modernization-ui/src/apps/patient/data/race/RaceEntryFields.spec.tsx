import { FormProvider, useForm } from 'react-hook-form';
import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { RaceEntry } from './entry';
import { categoryRequiredValidator } from './categoryRequiredValidator';
import { RaceEntryFields, RaceEntryFieldsProps } from './RaceEntryFields';

const mockDetailResolver = jest.fn();

jest.mock('options/race', () => ({
    useDetailedRaceOptions: (category?: string) => mockDetailResolver(category)
}));

type Props = Partial<RaceEntryFieldsProps> & { entry?: RaceEntry } & { isDirty?: boolean };

const Fixture = ({
    categories = [
        { value: '1', name: 'race name' },
        { value: 'other', name: 'other name' }
    ],
    categoryValidator = jest.fn().mockResolvedValue('true'),
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

        const { getByLabelText, getByText } = render(<Fixture />);

        const race = getByLabelText('Race');

        await user.selectOptions(race, '1');

        const detailed = getByLabelText('Detailed race');

        await user.click(detailed);

        expect(getByText('detailed race')).toBeInTheDocument();

        expect(mockDetailResolver).toBeCalledWith('1');
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
                isDirty={false}
            />
        );

        const race = getByLabelText('Race');
        const detailed = getByLabelText('Detailed race');

        await user.selectOptions(race, 'other');
        await user.tab();
        await user.click(detailed);

        expect(mockDetailResolver).toBeCalledWith('other');
        expect(detailed).toHaveValue('');
    });

    it('should require as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Race as of');

        await user.clear(asOf);
        await user.tab();

        expect(await findByText(/The Race as of is required/)).toBeInTheDocument();
    });

    it('should require race category', async () => {
        const user = userEvent.setup();

        const { getByRole, getByText } = render(<Fixture categoryValidator={categoryRequiredValidator} />);

        const category = getByRole('combobox', { name: 'Race' });

        await user.click(category);
        await user.tab();

        expect(getByText('The Race is required.')).toBeInTheDocument();
    });

    it('should require race category to pass validation', async () => {
        const validator = jest.fn();
        validator.mockResolvedValue('category not valid');

        const user = userEvent.setup();

        const { getByRole, getByText } = render(<Fixture categoryValidator={validator} />);

        const category = getByRole('combobox', { name: 'Race' });

        const other = getByRole('option', { name: 'other name' });

        await user.selectOptions(category, other);
        await user.tab();

        expect(validator).toBeCalledWith(19, expect.objectContaining({ value: 'other' }));
        await waitFor(() => expect(getByText('category not valid')).toBeInTheDocument());
    });

    it('should be valid with as of, race', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Race as of');
        const race = getByLabelText('Race');

        await user.type(asOf, '01/20/2020');
        await user.tab();
        await user.selectOptions(race, '1');
        await user.tab();

        expect(queryByText('The Race as of is required')).not.toBeInTheDocument();
        expect(queryByText('The Race is required')).not.toBeInTheDocument();
    });
});
