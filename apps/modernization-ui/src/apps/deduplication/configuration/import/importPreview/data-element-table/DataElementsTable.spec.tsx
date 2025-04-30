import { render } from '@testing-library/react';
import { DataElementsTable } from './DataElementsTable';
import { DataElements } from 'apps/deduplication/api/model/DataElement';

const dataElements: DataElements = {
    firstName: {
        active: true,
        oddsRatio: 5,
        logOdds: 2.3154
    },
    lastName: {
        active: false,
        oddsRatio: 3,
        logOdds: 1.44
    }
};
const Fixture = () => {
    return <DataElementsTable dataElements={dataElements} />;
};
describe('DataElementsTable', () => {
    it('renders the proper title', () => {
        const { getByRole } = render(<Fixture />);

        const heading = getByRole('heading');

        expect(heading).toHaveTextContent('Data elements');
    });

    it('renders the proper columns labels', () => {
        const { container } = render(<Fixture />);
        const headerCells = container.querySelectorAll('thead tr:nth-child(1) th');
        expect(headerCells[0]).toHaveTextContent('Field');
        expect(headerCells[1]).toHaveTextContent('Odds ratio');
        expect(headerCells[2]).toHaveTextContent('Log odds');
    });

    it('renders the proper data', () => {
        const { container } = render(<Fixture />);
        const firstRow = container.querySelectorAll('tbody tr:nth-child(1) td');

        expect(firstRow[0]).toHaveTextContent('First name');
        expect(firstRow[1]).toHaveTextContent('5');
        expect(firstRow[2]).toHaveTextContent('2.3154');
    });

    it('does not render inactive elements', () => {
        const { container } = render(<Fixture />);
        const rows = container.querySelectorAll('tbody tr');

        expect(rows).toHaveLength(1);
    });
});
