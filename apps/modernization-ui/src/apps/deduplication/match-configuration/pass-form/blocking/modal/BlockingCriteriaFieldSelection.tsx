import {
    BLOCKING_FIELD_OPTIONS,
    BlockingField,
    BlockingFieldOption
} from 'apps/deduplication/match-configuration/model/Blocking';
import { MatchingConfiguration } from 'apps/deduplication/match-configuration/model/Pass';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Modal } from 'design-system/modal';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';
import styles from './blocking-criteria-field-selection.module.scss';
import { FieldCheckbox } from 'apps/deduplication/match-configuration/field-checkbox/FieldCheckbox';

type Props = {
    activePass: number;
    onAccept: (selectedFields: BlockingFieldOption[]) => void;
    onCancel: () => void;
};
export const BlockingCriteriaFieldSelection = ({ activePass, onAccept, onCancel }: Props) => {
    const [selectedFields, setSelectedFields] = useState<BlockingField[]>([]);
    const form = useFormContext<MatchingConfiguration>();

    useEffect(() => {
        // get a list of which fields are already selected for blocking
        const activeFields = form.getValues(`passes.${activePass}.blockingCriteria`)?.map((a) => a.field.value);
        // update option list with existing selection
        setSelectedFields(activeFields);
    }, [form.getValues(`passes.${activePass}.blockingCriteria`)]);

    const handleToggleField = (option: BlockingFieldOption) => {
        if (selectedFields.includes(option.value)) {
            setSelectedFields(selectedFields.filter((f) => f !== option.value));
        } else {
            setSelectedFields((prev) => [...prev, option.value]);
        }
    };

    // Return the list of fields that were selected for use
    const handleAccept = () => {
        onAccept(Object.values(BLOCKING_FIELD_OPTIONS).filter((o) => selectedFields.includes(o.value)));
    };

    const footer = () => (
        <>
            <Button outline onClick={onCancel}>
                Cancel
            </Button>
            <Button onClick={handleAccept}>Accept</Button>
        </>
    );

    return (
        <Modal
            id={'blocking-criteria-modal'}
            title="Select attributes for blocking"
            onClose={onCancel}
            className={styles.modalSizing}
            footer={footer}>
            <div className={styles.fieldSelection}>
                <div className={styles.content}>
                    <section>
                        <Heading level={3}>Name</Heading>
                        <div className={styles.optionGroup}>
                            <FieldCheckbox
                                field={BLOCKING_FIELD_OPTIONS.lastName}
                                selected={selectedFields.includes(BLOCKING_FIELD_OPTIONS.lastName.value)}
                                onChange={handleToggleField}
                            />
                            <FieldCheckbox
                                field={BLOCKING_FIELD_OPTIONS.firstName}
                                selected={selectedFields.includes(BLOCKING_FIELD_OPTIONS.firstName.value)}
                                onChange={handleToggleField}
                            />
                        </div>
                    </section>
                    <section>
                        <Heading level={3}>Sex and birth</Heading>
                        <div className={styles.optionGroup}>
                            <FieldCheckbox
                                field={BLOCKING_FIELD_OPTIONS.sex}
                                selected={selectedFields.includes(BLOCKING_FIELD_OPTIONS.sex.value)}
                                onChange={handleToggleField}
                            />
                            <FieldCheckbox
                                field={BLOCKING_FIELD_OPTIONS.birthDate}
                                selected={selectedFields.includes(BLOCKING_FIELD_OPTIONS.birthDate.value)}
                                onChange={handleToggleField}
                            />
                        </div>
                    </section>
                    <section>
                        <Heading level={3}>Address</Heading>
                        <div className={styles.optionGroup}>
                            <FieldCheckbox
                                field={BLOCKING_FIELD_OPTIONS.address}
                                selected={selectedFields.includes(BLOCKING_FIELD_OPTIONS.address.value)}
                                onChange={handleToggleField}
                            />
                            <FieldCheckbox
                                field={BLOCKING_FIELD_OPTIONS.city}
                                selected={selectedFields.includes(BLOCKING_FIELD_OPTIONS.city.value)}
                                onChange={handleToggleField}
                            />
                            <FieldCheckbox
                                field={BLOCKING_FIELD_OPTIONS.state}
                                selected={selectedFields.includes(BLOCKING_FIELD_OPTIONS.state.value)}
                                onChange={handleToggleField}
                            />
                            <FieldCheckbox
                                field={BLOCKING_FIELD_OPTIONS.zip}
                                selected={selectedFields.includes(BLOCKING_FIELD_OPTIONS.zip.value)}
                                onChange={handleToggleField}
                            />
                        </div>
                    </section>
                </div>
            </div>
        </Modal>
    );
};
