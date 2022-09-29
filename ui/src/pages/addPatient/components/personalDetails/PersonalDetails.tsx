import { DatePicker, Dropdown, ErrorMessage, FormGroup, Label, TextInput } from '@trussworks/react-uswds';
import { useState } from 'react';
import './PersonalDetails.scss';

export interface InputPersonalDetailsFields {
    dateOfBirth: string;
    sex: string;
    birthSex: string;
    isPatientDeceased: string;
    maritalStatus: string;
    stateHivCaseId: string;
}
export default function PersonalDetails({
    personalDetailsFields,
    updateCallback
}: {
    personalDetailsFields: InputPersonalDetailsFields;
    updateCallback: (inputNameFields: InputPersonalDetailsFields) => void;
}) {
    const [isDobInvalid, setIsDobInvalid] = useState(false);
    const [yearsOld, setYearsOld] = useState('-');

    function handleDobChange(dob: string | undefined): void {
        const isInFuture = isDobInFuture(dob);
        setIsDobInvalid(isInFuture);
        setYearsOld(getYearsOld(dob));
        if (!isInFuture && dob != undefined) {
            updateCallback({ ...personalDetailsFields, dateOfBirth: dob });
        }
    }

    function getYearsOld(a: string = ''): string {
        const dob = new Date(a);
        if (a == undefined || isNaN(dob.getTime())) {
            return '-';
        }

        const timeDiff = Date.now() - new Date(a).getTime();
        const age = Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
        return age < 0 ? '-' : age.toString();
    }

    function isDobInFuture(dob: string | undefined): boolean {
        if (dob == undefined) {
            return false;
        }
        const now = new Date().getTime();
        const date = Date.parse(dob);
        return now < date;
    }

    return (
        <div className="person-details">
            <div className="flex-container">
                <FormGroup error={isDobInvalid}>
                    <Label htmlFor="dob" error={isDobInvalid}>
                        DOB
                    </Label>
                    <DatePicker
                        id="dob"
                        name="dob"
                        defaultValue={personalDetailsFields.dateOfBirth}
                        onChange={handleDobChange}
                        maxDate={new Date().toISOString()}
                    />
                    {isDobInvalid ? <ErrorMessage>DOB cannot be in the future</ErrorMessage> : ''}
                </FormGroup>

                <FormGroup>
                    <Label htmlFor="current-age">Current Age</Label>
                    <div className="age-text">{yearsOld}</div>
                </FormGroup>

                <FormGroup>
                    <Label htmlFor="current-sex">Current Sex</Label>
                    <Dropdown
                        id="sex"
                        name="sex"
                        defaultValue={personalDetailsFields.sex}
                        onChange={(e) => updateCallback({ ...personalDetailsFields, sex: e.target.value })}>
                        <option value=""></option>
                        <option value="F">Female</option>
                        <option value="M">Male</option>
                        <option value="U">Unknown</option>
                    </Dropdown>
                </FormGroup>
                <FormGroup>
                    <Label htmlFor="birth-sex">Birth Sex</Label>
                    <Dropdown
                        id="birth-sex"
                        name="birth-sex"
                        defaultValue={personalDetailsFields.birthSex}
                        onChange={(e) => updateCallback({ ...personalDetailsFields, birthSex: e.target.value })}>
                        <option value=""></option>
                        <option value="F">Female</option>
                        <option value="M">Male</option>
                        <option value="U">Unknown</option>
                    </Dropdown>
                </FormGroup>
                <FormGroup>
                    <Label htmlFor="deceased">Is the patient deceased</Label>
                    <Dropdown
                        id="decease"
                        name="deceased"
                        defaultValue={personalDetailsFields.isPatientDeceased}
                        onChange={(e) =>
                            updateCallback({ ...personalDetailsFields, isPatientDeceased: e.target.value })
                        }>
                        <option value=""></option>
                        <option value="N">No</option>
                        <option value="UNK">Unknown</option>
                        <option value="Y">Yes</option>
                    </Dropdown>
                </FormGroup>
                <FormGroup>
                    <Label htmlFor="marital-status">Marital Status</Label>
                    <Dropdown
                        id="marital-status"
                        name="marital status"
                        defaultValue={personalDetailsFields.maritalStatus}
                        onChange={(e) => updateCallback({ ...personalDetailsFields, maritalStatus: e.target.value })}>
                        <option value=""></option>
                        <option value="A">Annulled</option>
                        <option value="C">Common Law</option>
                        <option value="D">Divorced</option>
                        <option value="T">Domestic partner</option>
                        <option value="I">Interlocutory</option>
                        <option value="L">Legally separated</option>
                        <option value="G">Living Together</option>
                        <option value="M">Married</option>
                        <option value="O">Other</option>
                        <option value="P">Polygamous</option>
                        <option value="R">Refused to answer</option>
                        <option value="E">Separated</option>
                        <option value="S">Single, never married</option>
                        <option value="U">Unknown</option>
                        <option value="B">Unmarried</option>
                        <option value="F">Unreported</option>
                        <option value="W">Widowed</option>
                    </Dropdown>
                </FormGroup>
                <FormGroup>
                    <Label htmlFor="hiv-case-id">State HIV Case ID</Label>
                    <TextInput
                        id="hiv-case-number"
                        name="hiv-case-number"
                        type={'text'}
                        defaultValue={personalDetailsFields.stateHivCaseId}
                        onChange={(e) => updateCallback({ ...personalDetailsFields, stateHivCaseId: e.target.value })}
                    />
                </FormGroup>
            </div>
        </div>
    );
}
