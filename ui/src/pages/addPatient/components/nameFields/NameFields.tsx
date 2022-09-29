import { Dropdown, Label, TextInput } from '@trussworks/react-uswds';

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
        <>
            <div className="grid-row grid-gap">
                <div className="mobile-lg:grid-col-8">
                    <Label htmlFor="first-name">First name</Label>
                    <TextInput
                        id="first-name"
                        name="first-name"
                        type="text"
                        defaultValue={nameFields.firstName}
                        onChange={(e) => updateCallback({ ...nameFields, firstName: e.target.value })}
                    />
                </div>
                <div className="mobile-lg:grid-col-4">
                    <Label htmlFor="middle-name" hint=" (optional)">
                        Middle
                    </Label>
                    <TextInput
                        id="middle-name"
                        name="middle-name"
                        type="text"
                        defaultValue={nameFields.middleName}
                        onChange={(e) => updateCallback({ ...nameFields, middleName: e.target.value })}
                    />
                </div>
            </div>
            <div className="grid-row grid-gap">
                <div className="mobile-lg:grid-col-8">
                    <Label htmlFor="last-name">Last name</Label>
                    <TextInput
                        id="last-name"
                        name="last-name"
                        type="text"
                        defaultValue={nameFields.lastName}
                        onChange={(e) => updateCallback({ ...nameFields, lastName: e.target.value })}
                    />
                </div>
                <div className="mobile-lg:grid-col-4">
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
                </div>
            </div>
        </>
    );
}
