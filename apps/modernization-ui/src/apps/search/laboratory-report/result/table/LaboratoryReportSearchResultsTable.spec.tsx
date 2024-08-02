import { render } from "@testing-library/react";
import { LabReport } from "generated/graphql/schema";
import { SearchResultDisplayProvider } from 'apps/search/useSearchResultDisplay';
import { MemoryRouter } from 'react-router-dom';
import { ColumnPreferenceProvider } from "design-system/table/preferences";
import { LaboratoryReportSearchResultsTable } from "./LaboratoryReportSearchResultsTable";
import { MockedProvider, MockedResponse } from "@apollo/react-testing";
import { gql } from "@apollo/client";

describe('When InvestigationSearchResultsTable renders', () => {
    const testResults: LabReport[] = [{
        "__typename": "LabReport",
        "addTime": "2015-09-22",
        "associatedInvestigations": [],
        "id": "10000013",
        "jurisdictionCd": 333,
        "localId": "CAS10000000GA01",
        "observations": [],
        "organizationParticipations": [],
        "personParticipations": [
            {
                "__typename": "LabReportPersonParticipation",
                "birthTime": "1990-01-01",
                "currSexCd": "M",
                "typeCd": "PATSBJ",
                "firstName": "Surma",
                "lastName": "Singh",
                "personCd": "PAT",
                "personParentUid": 10000001,
                "shortId": 63000
            }
        ],
        "relevance": 1
    }];

    const FIND_ALL_JURISDICTIONS = gql`
    query findAllJurisdictions($page: Page) {
      findAllJurisdictions(page: $page) {
        id
        typeCd
        assigningAuthorityCd
        assigningAuthorityDescTxt
        codeDescTxt
        codeShortDescTxt
        effectiveFromTime
        effectiveToTime
        indentLevelNbr
        isModifiableInd
        parentIsCd
        stateDomainCd
        statusCd
        statusTime
        codeSetNm
        codeSeqNum
        nbsUid
        sourceConceptId
        codeSystemCd
        codeSystemDescTxt
        exportInd
      }
    }
  `;
  
    const mocks: MockedResponse[] = [
        {
            request: {
                query: FIND_ALL_JURISDICTIONS,
                variables: { id: '567' }
            },
            result: {
                data: {
                    findAllJurisdictions: [
                        {
                            id: '1',
                            typeCd: 'TestType',
                            assigningAuthorityCd: 'TestAuthority',
                            assigningAuthorityDescTxt: 'TestDescription',
                        }
                    ],
                },
            },
        },
    ];

    const Wrapper = () => {
        return (
            <MockedProvider mocks={mocks} addTypename={false}>
                <MemoryRouter>
                    <SearchResultDisplayProvider>
                        <ColumnPreferenceProvider>
                            <LaboratoryReportSearchResultsTable results={testResults} />
                        </ColumnPreferenceProvider>
                    </SearchResultDisplayProvider>
                </MemoryRouter>
            </MockedProvider>
        )
    }
    
    it('should display 9 columns', () => {
        const { container } = render(<Wrapper />);
        const columns = container.getElementsByTagName('th');
        expect(columns).toHaveLength(13);
    });

    it('should display column headers', () => {
        const { container } = render(<Wrapper />);
        const columns = container.getElementsByTagName('th');
        expect(columns[0]).toHaveTextContent('Legal name');
    })

    it('should display cell content', () => {
        const { container } = render(<Wrapper />);
        const cells = container.getElementsByTagName('td');
        expect(cells[0]).toHaveTextContent('Surma Singh');
    })
});
