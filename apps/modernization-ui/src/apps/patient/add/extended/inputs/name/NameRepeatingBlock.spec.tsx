import { render } from '@testing-library/react';
import { internalizeDate } from 'date';
import { NameRepeatingBlock } from './NameRepeatingBlock';

const onChange = jest.fn();
const isDirty = jest.fn();

const mockPatientNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};

const mockEntry = {
    state: {
        data: [
            {
                asOf: internalizeDate(new Date()),
                type: 'AN',
                first: 'test'
            }
        ]
    }
};

jest.mock('design-system/entry/multi-value/useMultiValueEntryState', () => ({
    useMultiValueEntryState: () => mockEntry
}));

jest.mock('apps/patient/profile/names/usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => mockPatientNameCodedValues
}));

const Fixture = () => <NameRepeatingBlock id="names" onChange={onChange} isDirty={isDirty} />;

describe('NameRepeatingBlock', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(<Fixture />);

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Type');
        expect(headers[2]).toHaveTextContent('Last');
        expect(headers[3]).toHaveTextContent('First');
        expect(headers[4]).toHaveTextContent('Middle');
        expect(headers[5]).toHaveTextContent('Suffix');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText } = render(<Fixture />);

        const dateInput = getByLabelText('Name as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const type = getByLabelText('Type');
        expect(type).toHaveValue('');

        const last = getByLabelText('Last');
        expect(last).toHaveValue('');

        const prefix = getByLabelText('Prefix');
        expect(prefix).toHaveValue('');

        const secondLast = getByLabelText('Second last');
        expect(secondLast).toHaveValue('');

        const middle = getByLabelText('Middle');
        expect(middle).toHaveValue('');

        const secondMiddle = getByLabelText('Second middle');
        expect(secondMiddle).toHaveValue('');

        const first = getByLabelText('First');
        expect(first).toHaveValue('');

        const suffix = getByLabelText('Suffix');
        expect(suffix).toHaveValue('');

        const degree = getByLabelText('Degree');
        expect(degree).toHaveValue('');
    });
});
