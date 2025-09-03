import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { internalizeDate } from 'date';
import { AddressRepeatingBlock } from './AddressRepeatingBlock';
import { AddressEntry } from 'apps/patient/data';
import { LocationOptions } from 'options/location';

const mockState = vi.fn();

const mockLocationOptions: LocationOptions = {
    states: [{ name: 'StateName', value: '1' }],
    counties: [{ name: 'CountyName', value: '2' }],
    countries: [{ name: 'CountryName', value: '3' }],
    state: mockState
};

vi.mock('options/location', () => ({
    useLocationOptions: () => mockLocationOptions
}));

const onChange = vi.fn();
const isDirty = vi.fn();

type FixtureProps = {
    values?: AddressEntry[];
};

const Fixture = ({ values }: FixtureProps) => (
    <AddressRepeatingBlock id="races" values={values} onChange={onChange} isDirty={isDirty} />
);

describe('when entering multiple address demographics', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(
            <Fixture
                values={[
                    {
                        asOf: '07/11/1997',
                        type: { name: 'type-name', value: 'type-value' },
                        use: { name: 'use-name', value: 'use-value' }
                    }
                ]}
            />
        );

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Type');
        expect(headers[2]).toHaveTextContent('Address');
        expect(headers[3]).toHaveTextContent('City');
        expect(headers[4]).toHaveTextContent('State');
        expect(headers[5]).toHaveTextContent('Zip');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText } = render(<Fixture />);

        const dateInput = getByLabelText('Address as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const race = getByLabelText('Type');
        expect(race).toHaveValue('');

        const use = getByLabelText('Use');
        expect(use).toHaveValue('');

        const street1 = getByLabelText('Street address 1');
        expect(street1).toHaveValue('');

        const street2 = getByLabelText('Street address 2');
        expect(street2).toHaveValue('');

        const city = getByLabelText('City');
        expect(city).toHaveValue('');

        const state = getByLabelText('State');
        expect(state).toHaveValue('');

        const zip = getByLabelText('Zip');
        expect(zip).toHaveValue('');

        const county = getByLabelText('County');
        expect(county).toHaveValue('');

        const censusTract = getByLabelText('Census tract');
        expect(censusTract).toHaveValue('');

        const country = getByLabelText('Country');
        expect(country).toHaveValue('');

        const comments = getByLabelText('Address comments');
        expect(comments).toHaveValue('');
    });
});
