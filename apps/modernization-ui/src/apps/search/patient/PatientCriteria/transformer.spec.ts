import { PatientCriteriaForm } from "../patientSearch/PatientCriteria";
import { transformObject } from "./transformer";

describe('when transformObject runs', () => {
    it('should render Selectable objects', () => {
        const input: PatientCriteriaForm = {
            country: { value: 'USA', name: 'United States of America', label: 'United States'}
        };
        const expected = {
            "assigningAuthority": undefined,
            "country": "USA",
            "deceased": undefined,
            "ethnicity": undefined,
            "gender": undefined,
            "identification": undefined,
            "identificationType": undefined,
            "labTest": "default",
            "mortalityStatus": undefined,
            "race": undefined,
            "recordStatus": [],
            "state": undefined,
            "status": undefined,
            "zip": undefined,
        };
        const result = transformObject(input);
        expect(result).toEqual(expected);
    });
});
