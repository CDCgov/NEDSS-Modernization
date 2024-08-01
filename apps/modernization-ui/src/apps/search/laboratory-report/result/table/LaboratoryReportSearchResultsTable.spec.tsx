import { render, waitFor } from '@testing-library/react';
import { LaboratoryReportSearchResultsTable } from './LaboratoryReportSearchResultsTable';
import { useJurisdictionOptions } from 'options/jurisdictions';
import { LabReport } from 'generated/graphql/schema';
import { MemoryRouter } from 'react-router-dom';
import { SearchResultDisplayProvider } from 'apps/search/useSearchResultDisplay';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';

jest.mock('options/jurisdictions', () => ({
    useJurisdictionOptions: jest.fn()
}));

describe('LaboratoryReportSearchResultsTable', () => {
        const mockJurisdictions = [
            { value: '130006', name: 'Jurisdiction 1' },
            { value: '2', name: 'Jurisdiction 2' }
        ];

        (useJurisdictionOptions as jest.Mock).mockReturnValue({
            all: mockJurisdictions
        });

        const results: LabReport[] = [{
            "__typename": "LabReport",
            "addTime": "2015-09-22",
            "associatedInvestigations": [
                {
                    "__typename": "AssociatedInvestigation",
                    "cdDescTxt": "Bacterial Vaginosis",
                    "localId": "CAS10001001GA01"
                },
                {
                    "__typename": "AssociatedInvestigation",
                    "cdDescTxt": "Bacterial Vaginosis",
                    "localId": "CAS10001001GA01"
                }
            ],
            "id": "10000013",
            "jurisdictionCd": 130006,
            "localId": "CAS10000000GA01",
            "observations": [
                {
                    "__typename": "Observation",
                    "cdDescTxt": "No Information Given",
                    "statusCd": null,
                    "altCd": null,
                    "displayName": null
                },
                {
                    "__typename": "Observation",
                    "cdDescTxt": "11-Desoxycortisol",
                    "statusCd": null,
                    "altCd": "1657-6",
                    "displayName": "abnormal"
                }
            ],
            "organizationParticipations": [
                {
                    "__typename": "LabReportOrganizationParticipation",
                    "typeCd": "AUT",
                    "name": "Emory University Hospital"
                }
            ],
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
                },
                {
                    "__typename": "LabReportPersonParticipation",
                    "birthTime": "1990-01-01",
                    "currSexCd": "M",
                    "typeCd": "ORD",
                    "firstName": "John",
                    "lastName": "Henry",
                    "personCd": "PRV",
                    "personParentUid": 10000001,
                    "shortId": 63000
                }
            ],
            "relevance": 1
        }];

        const Wrapper = () => {
            return (
                <MemoryRouter>
                    <SearchResultDisplayProvider>
                        <ColumnPreferenceProvider>
                            <LaboratoryReportSearchResultsTable results={results} />
                        </ColumnPreferenceProvider>
                    </SearchResultDisplayProvider>
                </MemoryRouter>
            );
        };

        const { container } = render(<Wrapper />);

    it('displays table content', async () => {
        await waitFor(() => {
            const columns = container.getElementsByTagName('td');
            expect(columns[0]).toHaveTextContent('Surma Singh');
            expect(columns[1]).toHaveTextContent('01/01/1990');
            expect(columns[2]).toHaveTextContent('M');
            expect(columns[3]).toHaveTextContent('63000');
            expect(columns[4]).toHaveTextContent('Lab report');
            expect(columns[5]).toHaveTextContent('09/22/2015');
            expect(columns[6]).toHaveTextContent('11-Desoxycortisol = abnormal');
            expect(columns[7]).toHaveTextContent('Emory University Hospital');
            expect(columns[8]).toHaveTextContent('John Henry');
            expect(columns[9]).toHaveTextContent('Jurisdiction 1');
            expect(columns[10]).toHaveTextContent('Bacterial Vaginosis');
            expect(columns[11]).toHaveTextContent('CAS10000000GA01');
        });
    });
});
