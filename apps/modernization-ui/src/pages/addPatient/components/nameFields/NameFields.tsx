import { Dropdown, Grid, Label, TextInput } from '@trussworks/react-uswds';

export interface InputNameFields {
    firstName: string;
    middleName: string;
    lastName: string;
    suffix: string;
}
export default function NameFields({
    nameFields,
    updateCallback
}: {
    nameFields: InputNameFields;
    updateCallback: (inputNameFields: InputNameFields) => void;
}) {
    return (
        <Grid row className="flex-align-center bg-white border radius-md border-base-lighter">
            <Grid col={12} className="font-sans-lg text-bold padding-3 border-bottom border-base-lighter">
                Name information
            </Grid>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="last-name">Last</Label>
                        <TextInput
                            id="last-name"
                            name="last-name"
                            type="text"
                            defaultValue={nameFields.lastName}
                            onChange={(e) => updateCallback({ ...nameFields, lastName: e.target.value })}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="first-name">First</Label>
                        <TextInput
                            id="first-name"
                            name="first-name"
                            type="text"
                            defaultValue={nameFields.firstName}
                            onChange={(e) => updateCallback({ ...nameFields, firstName: e.target.value })}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="middle-name">Middle</Label>
                        <TextInput
                            id="middle-name"
                            name="middle-name"
                            type="text"
                            defaultValue={nameFields.middleName}
                            onChange={(e) => updateCallback({ ...nameFields, middleName: e.target.value })}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="suffix">Suffix</Label>
                        <Dropdown
                            id="suffix"
                            name="suffix"
                            defaultValue={nameFields.suffix}
                            onChange={(e) => updateCallback({ ...nameFields, suffix: e.target.value })}>
                            <option value=""></option>
                            <option value="ESQ">Esquire</option>
                            <option value="II">II / The Second</option>
                            <option value="III">III / The Third</option>
                            <option value="IV">IV / The Fourth</option>
                            <option value="JR">Jr.</option>
                            <option value="SR">Sr.</option>
                            <option value="V">V / The Fifth</option>
                        </Dropdown>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
    );
}
