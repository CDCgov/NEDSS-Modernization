import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { SearchTerms } from './SearchTerms';
import { useSkipLink } from 'SkipLink/SkipLinkContext';
import { useSearchInteraction } from 'apps/search';
import { focusedTarget } from 'utils/focusedTarget';
import { Term } from 'apps/search/terms';

const mockSkipTo = jest.fn();
const mockRemove = jest.fn();
const mockFocusedTarget = jest.fn();
const mockWithout = jest.fn();

vi.mock('SkipLink/SkipLinkContext');
vi.mock('apps/search');
vi.mock('utils/focusedTarget');

const terms: Term[] = [
    { title: 'Term1', name: 'term1', source: 'term1', value: '1' },
    { title: 'Term2', name: 'term2', source: 'term2', value: '2' }
];

describe('SearchTerms', () => {
    beforeEach(() => {
        (useSkipLink as jest.Mock).mockReturnValue({ skipTo: mockSkipTo, remove: mockRemove });
        (focusedTarget as jest.Mock).mockImplementation(mockFocusedTarget);
        (useSearchInteraction as jest.Mock).mockReturnValue({ without: mockWithout });
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('renders the correct number of results', () => {
        const { getByText } = render(<SearchTerms total={2} terms={terms} />);
        expect(getByText('2 results for:')).toBeInTheDocument();
    });

    it('renders the singular form of results when total is 1', () => {
        const { getByText } = render(<SearchTerms total={1} terms={terms} />);
        expect(getByText('1 result for:')).toBeInTheDocument();
    });

    it('renders the correct number of results when total and filteredTotal is set', () => {
        const { getByText } = render(<SearchTerms total={10} filteredTotal={5} terms={terms} />);
        expect(getByText('5 of 10 found:')).toBeInTheDocument();
    });

    it('renders the correct terms', () => {
        const { getByText } = render(<SearchTerms total={2} terms={terms} />);
        expect(getByText('Term1: term1')).toBeInTheDocument();
        expect(getByText('Term2: term2')).toBeInTheDocument();
    });

    it('calls skipTo and focusedTarget on mount', async () => {
        render(<SearchTerms total={2} terms={terms} />);

        await waitFor(() => {
            expect(mockSkipTo).toHaveBeenCalledWith('resultsCount');
            expect(mockFocusedTarget).toHaveBeenCalledWith('resultsCount');
        });
    });

    it('calls without function when chip close button is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<SearchTerms total={2} terms={terms} />);
        const closeButton = getAllByRole('button')[0];

        await user.click(closeButton);

        expect(mockWithout).toHaveBeenCalledWith(terms[0]);
    });

    it('has correct aria-label', () => {
        const { getByLabelText } = render(<SearchTerms total={2} terms={terms} />);
        const resultsCountDiv = getByLabelText('2 Results have been found');
        expect(resultsCountDiv).toBeInTheDocument();
    });

    it('has correct aria-label singular form when total is 1', () => {
        const { getByLabelText } = render(<SearchTerms total={1} terms={terms} />);
        const resultsCountDiv = getByLabelText('1 Result has been found');
        expect(resultsCountDiv).toBeInTheDocument();
    });

    it('has correct aria-label singular form when total and filteredTotal is 1', () => {
        const { getByLabelText } = render(<SearchTerms total={1} filteredTotal={1} terms={terms} />);
        const resultsCountDiv = getByLabelText('1 of 1 Result has been found');
        expect(resultsCountDiv).toBeInTheDocument();
    });
});
