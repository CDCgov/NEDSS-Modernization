import { ConceptsContext, ConceptsProvider } from "apps/page-builder/context/ConceptContext"
import { EditConcept } from "./EditConcept"
import { Concept, ValueSet } from "apps/page-builder/generated";
import { AddConceptRequest } from "apps/page-builder/generated";
import { render, waitFor, screen, getByDisplayValue } from "@testing-library/react";

describe('When EditConcept form renders', () => {
        const selectedConcept: Concept = {    
            conceptCode: "ZZZ",
            display: "Test concept display",
            effectiveFromTime: "12/25/2000",
            effectiveToTime: "",
            status: AddConceptRequest.statusCode.A,
            codeSystem: "H7H"
        };
        const valueset: ValueSet = {
            valueSetCode: "ZZZ",
            valueSetTypeCd: "YYY",
            valueSetNm: "Test value set"
        };
        const options: any = [
            {label: 'Abnormal flags (HL7)', value: 'ABNORMAL_FLAGS_HL7'},
            {label: 'Accept/application acknowledgment conditions', value: 'PH_ACCEPTAPPLICATIONACKNOWLEDGMENTCONDITIONS_HL7_2X'}
        ]

    it('should disable Local code field', () => {
        const { container } = render(
            <EditConcept valueset={valueset} selectedConcept={selectedConcept} codeSystemOptionList={options} setShowForm={jest.fn()} updateCallback={jest.fn()} />
        );
        const inputs = container.getElementsByClassName('usa-input');
        expect(inputs[1]).toBeDisabled();
    });

    it('should display UI display name', async () => {
        const { container } = render(
            <EditConcept valueset={valueset} selectedConcept={selectedConcept} codeSystemOptionList={options} setShowForm={jest.fn()} updateCallback={jest.fn()} />
        );
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(13);
    });

    it('should display UI display name', async () => {
        const { container } = render(
            <EditConcept valueset={valueset} selectedConcept={selectedConcept} codeSystemOptionList={options} setShowForm={jest.fn()} updateCallback={jest.fn()} />
        );
        expect(screen.getAllByDisplayValue('Test concept display')).toHaveLength(1);
        expect(screen.getAllByDisplayValue('ZZZ')).toHaveLength(2);
    });
});
