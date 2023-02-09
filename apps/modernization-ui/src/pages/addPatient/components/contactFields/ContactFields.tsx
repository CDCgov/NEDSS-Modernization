import { Label, TextInput } from '@trussworks/react-uswds';

export interface InputContactFields {
    homePhone: string;
    workPhone: string;
    workPhoneExt: string;
    cellPhone: string;
    email: string;
}
export default function ContactFields({
    contactFields,
    updateCallback
}: {
    contactFields: InputContactFields;
    updateCallback: (inputContactFields: InputContactFields) => void;
}) {
    return (
        <>
            <Label htmlFor="home-phone">Home Phone</Label>
            <TextInput
                id="home-phone"
                name="home-phone"
                type="tel"
                defaultValue={contactFields.homePhone}
                onChange={(e) => updateCallback({ ...contactFields, homePhone: e.target.value })}
            />

            <div className="grid-row grid-gap">
                <div className="mobile-lg:grid-col-8">
                    <Label htmlFor="work-phone">Work Phone</Label>
                    <TextInput
                        id="work-phone"
                        name="work-phone"
                        type="tel"
                        defaultValue={contactFields.workPhone}
                        onChange={(e) => updateCallback({ ...contactFields, workPhone: e.target.value })}
                    />
                </div>
                <div className="mobile-lg:grid-col-4">
                    <Label htmlFor="work-phone-ext">Work Phone Ext</Label>
                    <TextInput
                        id="work-phone-ext"
                        name="work-phone-ext"
                        inputMode="numeric"
                        type="text"
                        defaultValue={contactFields.workPhoneExt}
                        onChange={(e) => updateCallback({ ...contactFields, workPhoneExt: e.target.value })}
                    />
                </div>
            </div>

            <Label htmlFor="cell-phone">Cell Phone</Label>
            <TextInput
                id="cell-phone"
                name="cell-phone"
                type="tel"
                defaultValue={contactFields.cellPhone}
                onChange={(e) => updateCallback({ ...contactFields, cellPhone: e.target.value })}
            />

            <Label htmlFor="email">Email</Label>
            <TextInput
                id="email"
                name="email"
                type="email"
                defaultValue={contactFields.email}
                onChange={(e) => updateCallback({ ...contactFields, email: e.target.value })}
            />
        </>
    );
}
