import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Modal } from 'design-system/modal';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';
import { BlockingField, MatchingConfiguration } from '../../../Configuration';
import styles from './blocking-criteria-field-selection.module.scss';
import { BlockingFieldOption } from './BlockingFieldOption';

type Options = {
    [key in BlockingField]: { active: boolean; label: string };
};

const options: Options = {
    firstName: { active: false, label: 'First name' },
    lastName: { active: false, label: 'Last name' },
    suffix: { active: false, label: 'Suffix' },
    birthDate: { active: false, label: 'Date of birth' },
    mrn: { active: false, label: 'MRN' },
    ssn: { active: false, label: 'SSN' },
    sex: { active: false, label: 'Current sex' },
    gender: { active: false, label: 'Gender' },
    race: { active: false, label: 'Race' },
    address: { active: false, label: 'Street address' },
    city: { active: false, label: 'City' },
    state: { active: false, label: 'State' },
    zip: { active: false, label: 'Zip' },
    county: { active: false, label: 'County' },
    telephone: { active: false, label: 'Telephone' }
};
type Props = {
    activePass: number;
    onAccept: (selectedFields: BlockingField[]) => void;
    onCancel: () => void;
};
export const BlockingCriteriaFieldSelection = ({ activePass, onAccept, onCancel }: Props) => {
    const [fields, setFields] = useState<Options>(options);
    const form = useFormContext<MatchingConfiguration>();

    useEffect(() => {
        // get a list of which fields are already selected for blocking
        const activeFields = form.getValues(`passes.${activePass}.blockingCriteria`)?.map((a) => a.field);

        // update option list with existing selection
        setFields((previous) => {
            const updated = { ...previous };
            activeFields.forEach((f) => (updated[f] = { active: true, label: options[f].label }));
            return updated;
        });
    }, [form.getValues(`passes.${activePass}.blockingCriteria`)]);

    const handleToggleField = (field: BlockingField) => {
        // set selected true / false for entry user selected
        setFields((prev) => {
            return { ...prev, [field]: { ...prev[field], active: !prev[field].active } };
        });
    };

    // Return the list of fields that were selected for use
    const handleAccept = () => {
        const activeFields = (Object.keys(fields) as Array<keyof Options>).filter((k) => fields[k].active);
        onAccept(activeFields);
    };

    return (
        <Modal
            id={'blocking-criteria-modal'}
            title="Select attributes for blocking"
            onClose={onCancel}
            className={styles.modalSizing}
            footer={() => (
                <>
                    <Button outline onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button onClick={handleAccept}>Accept</Button>
                </>
            )}>
            <div className={styles.fieldSelection}>
                <div className={styles.content}>
                    <section>
                        <Heading level={3}>Name</Heading>
                        <div className={styles.optionGroup}>
                            <BlockingFieldOption
                                id="lastName"
                                label={fields.lastName.label}
                                selected={fields.lastName.active}
                                onChange={handleToggleField}
                            />
                            <BlockingFieldOption
                                id="firstName"
                                label={fields.firstName.label}
                                selected={fields.firstName.active}
                                onChange={handleToggleField}
                            />
                        </div>
                    </section>
                    <section>
                        <Heading level={3}>Sex and birth</Heading>
                        <div className={styles.optionGroup}>
                            <BlockingFieldOption
                                id="sex"
                                label={fields.sex.label}
                                selected={fields.sex.active}
                                onChange={handleToggleField}
                            />
                            <BlockingFieldOption
                                id="birthDate"
                                label={fields.birthDate.label}
                                selected={fields.birthDate.active}
                                onChange={handleToggleField}
                            />
                        </div>
                    </section>
                    <section>
                        <Heading level={3}>Address</Heading>
                        <div className={styles.optionGroup}>
                            <BlockingFieldOption
                                id="address"
                                label={fields.address.label}
                                selected={fields.address.active}
                                onChange={handleToggleField}
                            />
                            <BlockingFieldOption
                                id="city"
                                label={fields.city.label}
                                selected={fields.city.active}
                                onChange={handleToggleField}
                            />
                            <BlockingFieldOption
                                id="state"
                                label={fields.state.label}
                                selected={fields.state.active}
                                onChange={handleToggleField}
                            />
                            <BlockingFieldOption
                                id="zip"
                                label={fields.zip.label}
                                selected={fields.zip.active}
                                onChange={handleToggleField}
                            />
                        </div>
                    </section>
                </div>
            </div>
        </Modal>
    );
};
