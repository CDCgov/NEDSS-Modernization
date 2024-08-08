import { render } from "@testing-library/react";
import { InvestigationSearchResultsTable } from "./InvestigationSearchResultsTable";
import { Investigation } from "generated/graphql/schema";
import { SearchResultDisplayProvider } from 'apps/search/useSearchResultDisplay';
import { MemoryRouter } from 'react-router-dom';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { ColumnPreferenceProvider } from "design-system/table/preferences";

describe('When InvestigationSearchResultsTable renders', () => {
    const testResults: Investigation[] = [{
        "__typename": "Investigation",
        "relevance": 1,
        "id": "10000013",
        "cdDescTxt": "Pertussis",
        "jurisdictionCodeDescTxt": "Clayton County",
        "localId": "CAS10000000GA01",
        "addTime": "2015-09-22",
        "investigationStatusCd": "O",
        "notificationRecordStatusCd": "UNASSIGNED",
        "personParticipations": [
            {
                "__typename": "InvestigationPersonParticipation",
                "birthTime": "1990-01-01",
                "currSexCd": "M",
                "typeCd": "SubjOfPHC",
                "firstName": "Surma",
                "lastName": "Singh",
                "personCd": "PAT",
                "personParentUid": 10000001,
                "shortId": 63000
            }
        ]
    }];

    const Wrapper = () => {
        return (
            <MemoryRouter>
                <SkipLinkProvider>
                    <SearchResultDisplayProvider>
                        <ColumnPreferenceProvider>
                            <InvestigationSearchResultsTable results={testResults} />
                        </ColumnPreferenceProvider>
                    </SearchResultDisplayProvider>
                </SkipLinkProvider>
            </MemoryRouter>
        )
    }
    
    it('should display 12 headers, one for colspan', () => {
        const { container } = render(<Wrapper />);
        const columns = container.getElementsByTagName('th');
        expect(columns).toHaveLength(12);
    });

    it('should display column headers', () => {
        const { container } = render(<Wrapper />);
        const columns = container.getElementsByTagName('th');
        expect(columns[0]).toHaveTextContent('Legal name');
        expect(columns[1]).toHaveTextContent('Date of birth');
        expect(columns[2]).toHaveTextContent('Sex');
        expect(columns[3]).toHaveTextContent('Patient ID');
        expect(columns[4]).toHaveTextContent('Condition');
        expect(columns[5]).toHaveTextContent('Start date');
        expect(columns[6]).toHaveTextContent('Jurisdiction');
        expect(columns[7]).toHaveTextContent('Investigator');
        expect(columns[8]).toHaveTextContent('Investigation ID');
        expect(columns[9]).toHaveTextContent('Status');
        expect(columns[10]).toHaveTextContent('Notification');
    });
    
    it('should display column content', () => {
        const { container } = render(<Wrapper />);
        const columns = container.getElementsByTagName('td');
        expect(columns[0]).toHaveTextContent('Surma Singh');
        expect(columns[1]).toHaveTextContent('01/01/1990');
        expect(columns[2]).toHaveTextContent('M');
        expect(columns[3]).toHaveTextContent('63000');
        expect(columns[4]).toHaveTextContent('Pertussis');
        expect(columns[5]).toHaveTextContent('09/22/2015');
        expect(columns[6]).toHaveTextContent('Clayton County');
        expect(columns[7]).toHaveTextContent('No Data');
        expect(columns[8]).toHaveTextContent('CAS10000000GA01');
        expect(columns[9]).toHaveTextContent('OPEN');
        expect(columns[10]).toHaveTextContent('UNASSIGNED');
    });
});
