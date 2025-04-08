import { BlockingAttribute, MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { PassConfigurationTable } from './PassConfigurationTable';
import { render } from '@testing-library/react';

const algorithm: { passes: Pass[] } = {
    passes: [
        {
            name: 'Test pass',
            description: 'Test description',
            active: true,
            blockingCriteria: [BlockingAttribute.ADDRESS],
            matchingCriteria: [{ attribute: MatchingAttribute.FIRST_NAME, method: MatchMethod.JAROWINKLER }],
            lowerBound: 0.25,
            upperBound: 0.88
        },
        {
            name: 'Test pass2',
            description: 'Test description 2',
            active: false,
            blockingCriteria: [BlockingAttribute.IDENTIFIER],
            matchingCriteria: [
                { attribute: MatchingAttribute.SEX, method: MatchMethod.EXACT },
                { attribute: MatchingAttribute.WIC_IDENTIFIER, method: MatchMethod.EXACT }
            ],
            lowerBound: 0.55,
            upperBound: 0.9
        }
    ]
};
const Fixture = () => {
    return <PassConfigurationTable algorithm={algorithm} />;
};
describe('PassConfigurationTable', () => {
    it('renders the proper title', () => {
        const { getByRole } = render(<Fixture />);

        const heading = getByRole('heading');

        expect(heading).toHaveTextContent('Pass configurations');
    });

    it('renders the proper columns labels', () => {
        const { container } = render(<Fixture />);
        const headerCells = container.querySelectorAll('thead tr:nth-child(1) th');
        expect(headerCells[0]).toHaveTextContent('Pass name');
        expect(headerCells[1]).toHaveTextContent('Description');
        expect(headerCells[2]).toHaveTextContent('Blocking criteria');
        expect(headerCells[3]).toHaveTextContent('Matching criteria');
        expect(headerCells[4]).toHaveTextContent('Lower bounds');
        expect(headerCells[5]).toHaveTextContent('Upper bounds');
        expect(headerCells[6]).toHaveTextContent('Active');
    });

    it('renders the proper data', () => {
        const { container } = render(<Fixture />);
        const firstRow = container.querySelectorAll('tbody tr:nth-child(1) td');

        expect(firstRow[0]).toHaveTextContent('Test pass');
        expect(firstRow[1]).toHaveTextContent('Test description');
        expect(firstRow[2]).toHaveTextContent('Street address 1');
        expect(firstRow[3]).toHaveTextContent('First name: JaroWinkler');
        expect(firstRow[4]).toHaveTextContent('0.25');
        expect(firstRow[5]).toHaveTextContent('0.88');
        expect(firstRow[6]).toHaveTextContent('Yes');

        const secondRow = container.querySelectorAll('tbody tr:nth-child(2) td');

        expect(secondRow[0]).toHaveTextContent('Test pass2');
        expect(secondRow[1]).toHaveTextContent('Test description 2');
        expect(secondRow[2]).toHaveTextContent('Identifier');
        expect(secondRow[3].children[0].children[0]).toHaveTextContent('Sex: Exact');
        expect(secondRow[3].children[0].children[1]).toHaveTextContent('WIC Identifier: Exact');
        expect(secondRow[4]).toHaveTextContent('0.55');
        expect(secondRow[5]).toHaveTextContent('0.9');
        expect(secondRow[6]).toHaveTextContent('No');
    });
});
