import { render } from '@testing-library/react';
import { NameEntry } from 'apps/patient/data/entry';
import { NameEntryView } from './NameEntryView';
import { asSelectable } from 'options/selectable';

const mockPatientNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};

jest.mock('apps/patient/profile/names/usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => mockPatientNameCodedValues
}));

const entry: NameEntry = {
    asOf: '12/25/2020',
    type: asSelectable('AN', 'Adopted name'),
    prefix: asSelectable('MS', 'Miss'),
    first: 'test first',
    middle: 'test middle',
    secondMiddle: 'second middle',
    last: 'test last',
    secondLast: 'second last',
    suffix: asSelectable('SR', 'test 2'),
    degree: asSelectable('BA', 'test ba')
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

        const suffix = getByText('Suffix');
        expect(suffix.parentElement?.children[1]).toHaveTextContent('test 2');

        const degree = getByText('Degree');
        expect(degree.parentElement?.children[1]).toHaveTextContent('test ba');
    });
});
