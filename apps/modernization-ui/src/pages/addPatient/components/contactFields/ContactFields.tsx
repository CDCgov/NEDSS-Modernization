import { Grid, Label, TextInput } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';

export interface InputContactFields {
    homePhone: string;
    workPhone: string;
    workPhoneExt: string;
    cellPhone: string;
    email: string;
}
export default function ContactFields({
    contactFields,
    updateCallback,
    id,
    title
}: {
    contactFields: InputContactFields;
    updateCallback: (inputContactFields: InputContactFields) => void;
    id?: string;
    title?: string;
}) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="home-phone">Home Phone</Label>
                        <TextInput
                            placeholder="333-444-555"
                            id="home-phone"
                            name="home-phone"
                            type="tel"
                            defaultValue={contactFields.homePhone}
                            onChange={(e) => updateCallback({ ...contactFields, homePhone: e.target.value })}
                        />
                    </Grid>
                </Grid>
                <Grid row gap={2}>
                    <Grid col={4}>
                        <Label htmlFor="work-phone">Work Phone</Label>
                        <TextInput
                            placeholder="333-444-555"
                            id="work-phone"
                            name="work-phone"
                            type="tel"
                            defaultValue={contactFields.workPhone}
                            onChange={(e) => updateCallback({ ...contactFields, workPhone: e.target.value })}
                        />
                    </Grid>
                    <Grid col={2}>
                        <Label htmlFor="work-phone-ext">Ext</Label>
                        <TextInput
                            placeholder="1234"
                            id="work-phone-ext"
                            name="work-phone-ext"
                            inputMode="numeric"
                            type="text"
                            defaultValue={contactFields.workPhoneExt}
                            onChange={(e) =>
                                updateCallback({
                                    ...contactFields,
                                    workPhoneExt: e.target.value
                                })
                            }
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="cell-phone">Cell Phone</Label>
                        <TextInput
                            placeholder="333-444-555"
                            id="cell-phone"
                            name="cell-phone"
                            type="tel"
                            defaultValue={contactFields.cellPhone}
                            onChange={(e) => updateCallback({ ...contactFields, cellPhone: e.target.value })}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor="email">Email</Label>
                        <TextInput
                            placeholder="jdoe@gmail.com"
                            id="email"
                            name="email"
                            type="email"
                            defaultValue={contactFields.email}
                            onChange={(e) => updateCallback({ ...contactFields, email: e.target.value })}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
