import { MergeInvestigation } from 'apps/deduplication/api/model/MergeCandidate';
import { Investigation } from './Investigation';
import { render } from '@testing-library/react';

const investigations: MergeInvestigation[] = [
    {
        id: 'CAS10001000GA01',
        startDate: '2025-06-05T00:00:00',
        condition: '2019 Novel Coronavirus'
    },
    {
        id: 'CAS10001001GA01',
        startDate: undefined,
        condition: 'Cholera'
    }
];

const Fixture = () => {
    return <Investigation investigations={investigations} />;
};

describe('Investigation', () => {
    it('should render investigation conditions and ids', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('2019 Novel Coronavirus:').parentElement).toHaveTextContent(
            '2019 Novel Coronavirus: CAS10001000GA01'
        );
        expect(getByText('Cholera:').parentElement).toHaveTextContent('Cholera: CAS10001001GA01');
    });

    it('should render investigation start dates or "---"', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('06/05/2025')).toBeInTheDocument();
        expect(getByText('---')).toBeInTheDocument();
    });
});
