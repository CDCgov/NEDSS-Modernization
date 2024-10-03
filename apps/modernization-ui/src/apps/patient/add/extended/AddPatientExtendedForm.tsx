import { AdministrativeEntryFields } from 'apps/patient/data/administrative/AdministrativeEntryFields';
import { EthnicityEntryFields } from 'apps/patient/data/ethnicity/EthnicityEntryFields';
import { GeneralInformationEntryFields } from 'apps/patient/data/general/GeneralInformationEntryFields';
import { MortalityEntryFields } from 'apps/patient/data/mortality/MortalityEntryFields';
import { SexAndBirthEntryFields } from 'apps/patient/data/sexAndBirth/SexAndBirthEntryFields';
import { useFormContext } from 'react-hook-form';
import { Card } from './card/Card';
import { ExtendedNewPatientEntry } from './entry';
import { AddressRepeatingBlock } from './inputs/address/AddressRepeatingBlock';
import { IdentificationRepeatingBlock } from './inputs/identification/IdentificationRepeatingBlock';
import { NameRepeatingBlock } from './inputs/name/NameRepeatingBlock';
import { PhoneAndEmailRepeatingBlock } from './inputs/phone/PhoneAndEmailRepeatingBlock';
import { RaceRepeatingBlock } from './inputs/race/RaceRepeatingBlock';

import { AlertMessage } from 'design-system/message/AlertMessage';
import styles from './add-patient-extended-form.module.scss';
import { SubFormDirtyState } from './useAddExtendedPatientInteraction';

type Props = {
    validationErrors?: SubFormDirtyState;
    setSubFormState: (state: Partial<SubFormDirtyState>) => void;
};
export const AddPatientExtendedForm = ({ validationErrors, setSubFormState }: Props) => {
    const { setValue } = useFormContext<ExtendedNewPatientEntry>();

    const renderErrorMessages = () => {
        const generateError = (section: string, id: string) => {
            return (
                <>
                    Data has been entered in the <a href={`#${id}`}>{section}</a> section. Please press Add or clear the
                    data and submit again.
                </>
            );
        };
        return (
            <ul className={styles.errorList}>
                {validationErrors?.name && <li>{generateError('Name', 'name')}</li>}
                {validationErrors?.address && <li>{generateError('Address', 'address')}</li>}
                {validationErrors?.phone && <li>{generateError('Phone & Email', 'phoneAndEmail')}</li>}
                {validationErrors?.identification && <li>{generateError('Identification', 'identification')}</li>}
                {validationErrors?.race && <li>{generateError('Race', 'races')}</li>}
            </ul>
        );
    };

    return (
        <div className={styles.addPatientForm}>
            <div className={styles.formContent}>
                {validationErrors && (
                    <AlertMessage title="Please fix the following errors:" type="error">
                        {renderErrorMessages()}
                    </AlertMessage>
                )}
                <Card
                    title="Administrative"
                    id="administrative"
                    info={<span className="required-before">All required fields for adding comments</span>}>
                    <AdministrativeEntryFields />
                </Card>
                <NameRepeatingBlock
                    isDirty={(isDirty) => setSubFormState({ name: isDirty })}
                    onChange={(nameData) => setValue('names', nameData)}
                />
                <AddressRepeatingBlock
                    isDirty={(isDirty) => setSubFormState({ address: isDirty })}
                    onChange={(addressData) => setValue('addresses', addressData)}
                />
                <PhoneAndEmailRepeatingBlock
                    isDirty={(isDirty) => setSubFormState({ phone: isDirty })}
                    onChange={(phoneEmailData) => setValue('phoneEmails', phoneEmailData)}
                />
                <IdentificationRepeatingBlock
                    isDirty={(isDirty) => setSubFormState({ identification: isDirty })}
                    onChange={(identificationData) => setValue('identifications', identificationData)}
                />
                <RaceRepeatingBlock
                    isDirty={(isDirty) => setSubFormState({ race: isDirty })}
                    onChange={(raceData) => setValue('races', raceData)}
                />
                <Card
                    id="ethnicity"
                    title="Ethnicity"
                    info={<span className="required-before">All required fields for adding ethnicity</span>}>
                    <EthnicityEntryFields />
                </Card>
                <Card
                    id="sexAndBirth"
                    title="Sex & birth"
                    info={<span className="required-before">All required fields for adding sex & birth</span>}>
                    <SexAndBirthEntryFields />
                </Card>
                <Card
                    id="mortality"
                    title="Mortality"
                    info={<span className="required-before">All required fields for adding mortality</span>}>
                    <MortalityEntryFields />
                </Card>
                <Card
                    id="generalInformation"
                    title="General patient information"
                    info={
                        <span className="required-before">
                            All required fields for adding general patient information
                        </span>
                    }>
                    <GeneralInformationEntryFields />
                </Card>
            </div>
        </div>
    );
};
