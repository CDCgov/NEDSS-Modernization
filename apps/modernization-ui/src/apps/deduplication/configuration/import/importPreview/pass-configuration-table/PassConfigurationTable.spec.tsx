import { BlockingAttribute, MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { PassConfigurationTable } from './PassConfigurationTable';
import { render, within } from '@testing-library/react';

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
        const { getAllByRole } = render(<Fixture />);
        const headerCells = getAllByRole('columnheader');
        expect(headerCells[0]).toHaveTextContent('Pass name');
        expect(headerCells[1]).toHaveTextContent('Description');
        expect(headerCells[2]).toHaveTextContent('Blocking criteria');
        expect(headerCells[3]).toHaveTextContent('Matching criteria');
        expect(headerCells[4]).toHaveTextContent('Lower bounds');
        expect(headerCells[5]).toHaveTextContent('Upper bounds');
        expect(headerCells[6]).toHaveTextContent('Active');
    });

    it('renders the proper data', () => {
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');
        expect(rows).toHaveLength(3);

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        expect(firstRowCells[0]).toHaveTextContent('Test pass');
        expect(firstRowCells[1]).toHaveTextContent('Test description');
        expect(firstRowCells[2]).toHaveTextContent('Street address 1');
        expect(firstRowCells[3]).toHaveTextContent('First name: JaroWinkler');
        expect(firstRowCells[4]).toHaveTextContent('0.25');
        expect(firstRowCells[5]).toHaveTextContent('0.88');
        expect(firstRowCells[6]).toHaveTextContent('Yes');

        const secondRowCells = within(rows[2]).getAllByRole('cell');
        expect(secondRowCells[0]).toHaveTextContent('Test pass2');
        expect(secondRowCells[1]).toHaveTextContent('Test description 2');
        expect(secondRowCells[2]).toHaveTextContent('Identifier');
        expect(secondRowCells[3]).toHaveTextContent('Sex: Exact');
        expect(secondRowCells[3]).toHaveTextContent('WIC identifier: Exact');
        expect(secondRowCells[4]).toHaveTextContent('0.55');
        expect(secondRowCells[5]).toHaveTextContent('0.9');
        expect(secondRowCells[6]).toHaveTextContent('No');
    });
});
