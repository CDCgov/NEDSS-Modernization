import { render } from '@testing-library/react';
import { SEARCH_TYPE } from 'apps/search/advancedSearch/AdvancedSearch';
import { PersonFilter } from 'generated/graphql/schema';
import { AdvancedSearchChips } from './AdvancedSearchChips';

describe('AdvancedSearchChips', () => {
    const mockPersonFilter: PersonFilter = {
        firstName: 'John',
        lastName: 'Doe',
        recordStatus: []
    };

    const mockOnPersonFilterChange = jest.fn();

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('should render PatientChips when lastSearchType is SEARCH_TYPE.PERSON', () => {
        const { getByText } = render(
            <AdvancedSearchChips
                lastSearchType={SEARCH_TYPE.PERSON}
                personFilter={mockPersonFilter}
                onPersonFilterChange={mockOnPersonFilterChange}
                onInvestigationFilterChange={() => {}}
                onLabReportFilterChange={() => {}}
            />
        );

        expect(getByText('John')).toBeInTheDocument();
    });
});
