import { FieldCheckbox } from 'apps/deduplication/match-configuration/field-checkbox/FieldCheckbox';
import {
    MATCHING_FIELD_OPTIONS,
    MatchingField,
    MatchingFieldOption
} from 'apps/deduplication/match-configuration/model/Matching';
import { MatchingConfiguration } from 'apps/deduplication/match-configuration/model/Pass';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Modal } from 'design-system/modal';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';
import styles from './matching-criteria-field-selection.module.scss';
import { DataElements } from 'apps/deduplication/data-elements/DataElement';

type Props = {
    activePass: number;
    dataElements: DataElements;
    onAccept: (selectedFields: MatchingFieldOption[]) => void;
    onCancel: () => void;
};
export const MatchingCriteriaFieldSelection = ({ activePass, dataElements, onAccept, onCancel }: Props) => {
    const [selectedFields, setSelectedFields] = useState<MatchingField[]>([]);
    const form = useFormContext<MatchingConfiguration>();

    useEffect(() => {
        // get a list of which fields are already selected for matching
        const activeFields = form
            .getValues(`passes.${activePass}.matchingCriteria`)
            ?.map((a) => a.field.value)
            .filter((a) => a !== undefined);
        // update option list with existing selection
        setSelectedFields(activeFields);
    }, [form.getValues(`passes.${activePass}.matchingCriteria`)]);

    const handleToggleField = (option: MatchingFieldOption) => {
        if (selectedFields.includes(option.value)) {
            setSelectedFields(selectedFields.filter((f) => f !== option.value));
        } else {
            setSelectedFields((prev) => [...prev, option.value]);
        }
    };

    // Return the list of fields that were selected for use
    const handleAccept = () => {
        onAccept(Object.values(MATCHING_FIELD_OPTIONS).filter((o) => selectedFields.includes(o.value)));
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
            id={'matching-criteria-modal'}
            title="Select attributes for matching"
            onClose={onCancel}
            className={styles.modalSizing}
            footer={footer}>
            <div className={styles.fieldSelection}>
                <div className={styles.content}>
                    <section>
                        <Heading level={3}>Name</Heading>
                        <div className={styles.optionGroup}>
                            <FieldCheckbox
                                field={MATCHING_FIELD_OPTIONS.lastName}
                                selected={selectedFields.includes(MATCHING_FIELD_OPTIONS.lastName.value)}
                                onChange={handleToggleField}
                                disabled={!dataElements.lastName?.active}
                            />
                            <FieldCheckbox
                                field={MATCHING_FIELD_OPTIONS.firstName}
                                selected={selectedFields.includes(MATCHING_FIELD_OPTIONS.firstName.value)}
                                onChange={handleToggleField}
                                disabled={!dataElements.firstName?.active}
                            />
                        </div>
                    </section>
                    <section>
                        <Heading level={3}>Sex and birth</Heading>
                        <div className={styles.optionGroup}>
                            <FieldCheckbox
                                field={MATCHING_FIELD_OPTIONS.sex}
                                selected={selectedFields.includes(MATCHING_FIELD_OPTIONS.sex.value)}
                                onChange={handleToggleField}
                                disabled={!dataElements.sex?.active}
                            />
                            <FieldCheckbox
                                field={MATCHING_FIELD_OPTIONS.birthDate}
                                selected={selectedFields.includes(MATCHING_FIELD_OPTIONS.birthDate.value)}
                                onChange={handleToggleField}
                                disabled={!dataElements.birthDate?.active}
                            />
                        </div>
                    </section>
                    <section>
                        <Heading level={3}>Address</Heading>
                        <div className={styles.optionGroup}>
                            <FieldCheckbox
                                field={MATCHING_FIELD_OPTIONS.address}
                                selected={selectedFields.includes(MATCHING_FIELD_OPTIONS.address.value)}
                                onChange={handleToggleField}
                                disabled={!dataElements.address?.active}
                            />
                            <FieldCheckbox
                                field={MATCHING_FIELD_OPTIONS.city}
                                selected={selectedFields.includes(MATCHING_FIELD_OPTIONS.city.value)}
                                onChange={handleToggleField}
                                disabled={!dataElements.city?.active}
                            />
                            <FieldCheckbox
                                field={MATCHING_FIELD_OPTIONS.state}
                                selected={selectedFields.includes(MATCHING_FIELD_OPTIONS.state.value)}
                                onChange={handleToggleField}
                                disabled={!dataElements.state?.active}
                            />
                            <FieldCheckbox
                                field={MATCHING_FIELD_OPTIONS.zip}
                                selected={selectedFields.includes(MATCHING_FIELD_OPTIONS.zip.value)}
                                onChange={handleToggleField}
                                disabled={!dataElements.zip?.active}
                            />
                        </div>
                    </section>
                </div>
            </div>
        </Modal>
    );
};
