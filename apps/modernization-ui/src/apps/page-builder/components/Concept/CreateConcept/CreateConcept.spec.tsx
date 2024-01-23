import { ConceptsContext, ConceptsProvider } from "apps/page-builder/context/ConceptContext"

import { Concept, ValueSet } from "apps/page-builder/generated";
import { AddConceptRequest } from "apps/page-builder/generated";
import { render } from "@testing-library/react";
import { CreateConcept } from "./CreateConcept";

describe('When EditConcept form renders', () => {
        const selectedConcept: Concept = {    
            conceptCode: "ZZZ",
            display: "Test concept",
            effectiveFromTime: "12/25/2000",
            effectiveToTime: "",
            status: AddConceptRequest.statusCode.A,
            codeSystem: "H7H"
        };
        const valueset: ValueSet = {
            valueSetCode: "ZZZ",
            valueSetTypeCd: "YYY",
            valueSetNm: "Text value set"
        };
        const options: any = [
            {label: 'Abnormal flags (HL7)', value: 'ABNORMAL_FLAGS_HL7'},
            {label: 'Accept/application acknowledgment conditions', value: 'PH_ACCEPTAPPLICATIONACKNOWLEDGMENTCONDITIONS_HL7_2X'}
        ]
        const { container } = render(
            <ConceptsProvider value={{
                selectedConcept,
                setSelectedConcept: () => {},
                searchQuery: '',
                setSearchQuery: () => {},
                currentPage: 1,
                setCurrentPage: () => {},
                sortBy: '',
                setSortBy: () => {},
                sortDirection: '',
                setSortDirection: () => {},
                pageSize: 10,
                setPageSize: () => {}
            }}>
                <CreateConcept valueset={valueset} codeSystemOptionList={options} setShowForm={jest.fn()} updateCallback={jest.fn()} setAlertMessage={jest.fn()} />
            </ConceptsProvider>
        );

    it('should display input labels', () => {
        const labels = container.getElementsByTagName('label');
        expect(labels.length).toBe(13);
        expect(labels[1]).toHaveTextContent('UI Display name');
        expect(labels[2]).toHaveTextContent('Local code');
        expect(labels[3]).toHaveTextContent('Concept code');
    });

    it('should display inputs', () => {
        const { container } = render(
            <ConceptsProvider value={{
                selectedConcept,
                setSelectedConcept: () => {},
                searchQuery: '',
                setSearchQuery: () => {},
                currentPage: 1,
                setCurrentPage: () => {},
                sortBy: '',
                setSortBy: () => {},
                sortDirection: '',
                setSortDirection: () => {},
                pageSize: 10,
                setPageSize: () => {}
            }}>
                <CreateConcept valueset={valueset} codeSystemOptionList={options} setShowForm={jest.fn()} updateCallback={jest.fn()} setAlertMessage={jest.fn()} />
            </ConceptsProvider>
        );
        const inputs = container.getElementsByClassName('usa-input');
        expect(inputs.length).toBe(8);
    });
    it('should have a disabled Add buttton', () => {
        const { container } = render(
            <ConceptsProvider value={{
                selectedConcept,
                setSelectedConcept: () => {},
                searchQuery: '',
                setSearchQuery: () => {},
                currentPage: 1,
                setCurrentPage: () => {},
                sortBy: '',
                setSortBy: () => {},
                sortDirection: '',
                setSortDirection: () => {},
                pageSize: 10,
                setPageSize: () => {}
            }}>
                <CreateConcept valueset={valueset} codeSystemOptionList={options} setShowForm={jest.fn()} updateCallback={jest.fn()} setAlertMessage={jest.fn()} />
            </ConceptsProvider>
        );
        const buttons = container.getElementsByTagName('button');
        expect(buttons[4]).toBeDisabled();
    })
});
