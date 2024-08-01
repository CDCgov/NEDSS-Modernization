import { render } from "@testing-library/react";
import { LabReport } from "generated/graphql/schema";
import { SearchResultDisplayProvider } from 'apps/search/useSearchResultDisplay';
import { MemoryRouter } from 'react-router-dom';
import { ColumnPreferenceProvider } from "design-system/table/preferences";
import { LaboratoryReportSearchResultsTable } from "./LaboratoryReportSearchResultsTable";
import { MockedProvider } from "@apollo/react-testing";
import { findByValue } from "options";
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
                "typeCd": "SubjOfPHC",
                "firstName": "Surma",
                "lastName": "Singh",
                "personCd": "PAT",
                "personParentUid": 10000001,
                "shortId": 63000
            }
        ],
        "relevance": 1
    }];

    const FIND_BY_VALUE_QUERY = gql`
        query findByValue($jurisdictions: Selectable) {
            findAllJurisdictions(page: $page) {
            // specify the fields you expect in the response
            }
        }
    `;

    const mocks = [
        {
          request: {
            query: FIND_BY_VALUE_QUERY,
            variables: {
              page: {
                current: 1,
                pageSize: 10,
              },
            },
          },
          result: {
            data: {
              findAllJurisdictions: [
                { name: 'asdf', value: 'asdf', label: 'asdf' }
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
        const { container } = render(<Wrapper />)
        const columns = container.getElementsByTagName('th');
        expect(columns).toHaveLength(13);
        expect(columns[0]).toHaveTextContent('Legal name');
    });
});
