import { render } from '@testing-library/react';
import { NameEntryView } from './NameEntryView';
import { NameEntry } from 'apps/patient/profile/names/NameEntry';

const mockPatientNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{name: 'Sr.', value: 'SR'}],
    degrees: [{name: 'BA', value: 'BA'}]
};

jest.mock('apps/patient/profile/names/usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => mockPatientNameCodedValues
}));

const entry: NameEntry = {
    asOf: '12/25/2020',
    type: 'AN',
    prefix: 'MS',
    first: 'test first',
    middle: 'test middle',
    secondMiddle: 'second middle',
    last: 'test last',
    secondLast: 'second last',
    suffix: 'SR',
    degree: 'BA'
};

describe('NameEntryView', () => {
    it('should render label with value', () => {
        const { getByText } = render(<NameEntryView entry={entry} />);
        const asOf = getByText('As of');
        expect(asOf.parentElement?.children[1]).toHaveTextContent('12/25/2020');

        const type = getByText('Type');
        expect(type.parentElement?.children[1]).toHaveTextContent('Adopted name');

        const prefix = getByText('Prefix');
        expect(prefix.parentElement?.children[1]).toHaveTextContent('Miss');

        const last = getByText('Last');
        expect(last.parentElement?.children[1]).toHaveTextContent('test last');

        const first = getByText('First');
        expect(first.parentElement?.children[1]).toHaveTextContent('test first');

        const middle = getByText('Middle');
        expect(middle.parentElement?.children[1]).toHaveTextContent('test middle');

        const secondMiddle = getByText('Second middle');
        expect(secondMiddle.parentElement?.children[1]).toHaveTextContent('second middle');


    });
});
