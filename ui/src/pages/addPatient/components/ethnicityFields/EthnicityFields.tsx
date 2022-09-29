import { Checkbox, Dropdown, Fieldset, Label } from '@trussworks/react-uswds';

export interface InputEthnicityFields {
    ethnicity: string;
    // TODO races
}
export default function EthnicityFields({
    ethnicityFields,
    updateCallback
}: {
    ethnicityFields: InputEthnicityFields;
    updateCallback: (ethnicityFields: InputEthnicityFields) => void;
}) {
    return (
        <>
            <Label htmlFor="ethnicity">Ethnicity</Label>
            <Dropdown
                id="ethnicity"
                name="ethnicity"
                defaultValue={ethnicityFields.ethnicity}
                onChange={(e) => updateCallback({ ...ethnicityFields, ethnicity: e.target.value })}>
                <option value=""></option>
                <option value="2135-2">Hispanic or Latino</option>
                <option value="2186-5">Not Hispanic or Latino</option>
                <option value="UNK">Unknown</option>
            </Dropdown>

            <Label htmlFor="race">Race</Label>
            <Fieldset>
                <Checkbox
                    id="americanIndianRace"
                    name="americanIndianRace"
                    value="true"
                    label="American Indian or Alaska Native"
                />
                <Checkbox id="asianRace" name="asianRace" value="true" label="Asian" />
                <Checkbox id="africanRace" name="africanRace" value="true" label="Black or African American" />
                <Checkbox
                    id="hawaiianRace"
                    name="hawaiianRace"
                    value="true"
                    label="Native Hawaiian or Other Pacific Islander"
                />
                <Checkbox id="whiteRace" name="whiteRace" value="true" label="White" />
                <Checkbox id="otherRace" name="otherRace" value="true" label="Other" />
                <Checkbox id="refusedToAnswer" name="refusedToAnswer" value="true" label="Refused to answer" />
                <Checkbox id="notAsked" name="notAsked" value="true" label="Not Asked" />
                <Checkbox id="unknownRace" name="unknownRace" value="true" label="Unknown" />
            </Fieldset>
        </>
    );
}
