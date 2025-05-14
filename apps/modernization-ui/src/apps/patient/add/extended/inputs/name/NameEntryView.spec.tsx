import { render } from '@testing-library/react';
import { NameEntry } from 'apps/patient/data/name';
import { NameEntryView } from './NameEntryView';
import { asSelectable } from 'options/selectable';

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
        expect(asOf.parentElement).toContainElement(getByText('12/25/2020'));

        const type = getByText('Type');
        expect(type.parentElement).toContainElement(getByText('Adopted name'));

        const prefix = getByText('Prefix');
        expect(prefix.parentElement).toContainElement(getByText('Miss'));

        const last = getByText('Last');
        expect(last.parentElement).toContainElement(getByText('test last'));

        const first = getByText('First');
        expect(first.parentElement).toContainElement(getByText('test first'));

        const middle = getByText('Middle');
        expect(middle.parentElement).toContainElement(getByText('test middle'));

        const secondMiddle = getByText('Second middle');
        expect(secondMiddle.parentElement).toContainElement(getByText('second middle'));

        const suffix = getByText('Suffix');
        expect(suffix.parentElement).toContainElement(getByText('test 2'));

        const degree = getByText('Degree');
        expect(degree.parentElement).toContainElement(getByText('test ba'));
    });
});
