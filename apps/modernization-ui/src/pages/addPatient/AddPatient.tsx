import { Accordion, Button, ButtonGroup, DatePicker, Fieldset, Form, Label } from '@trussworks/react-uswds';
import './AddPatient.scss';
import NameFields, { InputNameFields } from './components/nameFields/NameFields';
import AddressFields, { InputAddressFields } from './components/addressFields/AddressFields';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import PersonalDetails, { InputPersonalDetailsFields } from './components/personalDetails/PersonalDetails';
import ContactFields, { InputContactFields } from './components/contactFields/ContactFields';
import EthnicityFields, { InputEthnicityFields } from './components/ethnicityFields/EthnicityFields';
import { useState } from 'react';

export default function AddPatient() {
    const [nameFields, setNameFields]: [InputNameFields, (inputNameFields: InputNameFields) => void] = useState({
        firstName: '',
        middleName: '',
        lastName: '',
        suffix: ''
    });
    const [addressFields, setAddressFields]: [InputAddressFields, (inputNameFields: InputAddressFields) => void] =
        useState({
            streetAddress1: '',
            streetAddress2: '',
            city: '',
            state: '',
            zip: '',
            county: '',
            censusTract: '',
            country: ''
        });

    const [personalDetailsFields, setPersonalDetailsFields]: [
        InputPersonalDetailsFields,
        (inputNameFields: InputPersonalDetailsFields) => void
    ] = useState({
        dateOfBirth: '',
        sex: '',
        birthSex: '',
        isPatientDeceased: '',
        stateHivCaseId: '',
        maritalStatus: ''
    });

    const [contactFields, setContactFields]: [InputContactFields, (inputContactFields: InputContactFields) => void] =
        useState({ homePhone: '', workPhone: '', workPhoneExt: '', cellPhone: '', email: '' });

    const [ethnicityFields, setEthnicityFields]: [
        InputEthnicityFields,
        (ethnicityFields: InputEthnicityFields) => void
    ] = useState({
        ethnicity: ''
    });

    const [asOfDate, setAsOfDate]: [string, (asOfDate: string) => void] = useState(new Date().toISOString());

    async function submit(): Promise<void> {
        // TODO migrate to use a GraphQL mutation- NYI
        // const person = {
        //     // Name
        //     firstNm: nameFields.firstName,
        //     middleNm: nameFields.middleName,
        //     lastNm: nameFields.lastName,
        //     nmSuffix: nameFields.suffix,
        //     // Address
        //     hmStreetAddr1: addressFields.streetAddress1,
        //     hmStreetAddr2: addressFields.streetAddress2,
        //     hmCityCd: addressFields.city,
        //     hmStateCd: addressFields.state,
        //     hmZipCd: addressFields.zip,
        //     hmCntyCd: addressFields.county,
        //     // TODO census tract?
        //     hmCntryCd: addressFields.country,
        //     // Personal Details
        //     birthTime: personalDetailsFields.dateOfBirth.trim() == '' ? undefined : personalDetailsFields.dateOfBirth,
        //     currSexCd: personalDetailsFields.sex,
        //     birthGenderCd: personalDetailsFields.birthSex,
        //     deceasedIndCd: personalDetailsFields.isPatientDeceased,
        //     // TODO HIV case id?
        //     maritalStatusCd: personalDetailsFields.maritalStatus,
        //     // Contact
        //     hmPhoneNbr: contactFields.homePhone,
        //     wkPhoneNbr: contactFields.workPhone + 'x' + contactFields.workPhoneExt, // TODO how to store workPhoneExt
        //     cellPhoneNbr: contactFields.cellPhone,
        //     hmEmailAddr: contactFields.email,
        //     // Ethnicity
        //     ethnicityGroupCd: ethnicityFields.ethnicity, // TODO how is ethnicity stored
        //     // As of
        //     asOfDateAdmin: asOfDate,
        //     asOfDateEthnicity: asOfDate,
        //     asOfDateGeneral: asOfDate,
        //     asOfDateMorbidity: asOfDate,
        //     asOfDateSex: asOfDate
        // };
        // const newId = await PersonControllerService.createPersonUsingPost({ person });
    }

    function cancel(): void {
        console.log('Cancel clicked');
    }

    function getSections(): AccordionItemProps[] {
        const items: AccordionItemProps[] = [];
        items.push({
            title: 'Name',
            content: <NameFields nameFields={nameFields} updateCallback={setNameFields} />,
            expanded: true,
            id: 'name-section',
            headingLevel: 'h1'
        });
        items.push({
            title: 'Address',
            content: <AddressFields addressFields={addressFields} updateCallback={setAddressFields} />,
            expanded: false,
            id: 'address-section',
            headingLevel: 'h1'
        });
        items.push({
            title: 'Other Personal Details',
            content: (
                <PersonalDetails
                    personalDetailsFields={personalDetailsFields}
                    updateCallback={setPersonalDetailsFields}
                />
            ),
            expanded: false,
            id: 'personal-details',
            headingLevel: 'h1'
        });
        items.push({
            title: 'Contact Information',
            content: <ContactFields contactFields={contactFields} updateCallback={setContactFields} />,
            expanded: false,
            id: 'contact',
            headingLevel: 'h1'
        });
        items.push({
            title: 'Ethnicity and Race Information',
            content: <EthnicityFields ethnicityFields={ethnicityFields} updateCallback={setEthnicityFields} />,
            expanded: false,
            id: 'ethnicity',
            headingLevel: 'h1'
        });
        return items;
    }

    return (
        <>
            <div className="content">
                <div className="form-container">
                    <Form onSubmit={submit} large>
                        <Fieldset legend="Add Patient" legendStyle="large">
                            <Accordion multiselectable={true} items={getSections()} />
                            <Label htmlFor="as-of-date">As of Date</Label>
                            <DatePicker
                                id="asOf"
                                name="asOf"
                                defaultValue={asOfDate}
                                onChange={(e) => setAsOfDate(e || '')}
                            />
                        </Fieldset>
                        <ButtonGroup>
                            <Button type="button" onClick={cancel}>
                                Cancel
                            </Button>
                            <Button type="button" onClick={submit}>
                                Submit
                            </Button>
                        </ButtonGroup>
                    </Form>
                </div>
            </div>
        </>
    );
}
