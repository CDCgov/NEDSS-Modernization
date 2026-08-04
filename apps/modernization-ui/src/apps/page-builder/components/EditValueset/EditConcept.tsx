import { useEffect } from 'react';

import { Button } from '@trussworks/react-uswds';
import { Concept, UpdateConceptRequest } from 'apps/page-builder/generated';
import { useUpdateConcept } from 'apps/page-builder/hooks/api/useUpdateConcept';
import { internalizeDate } from 'date';
import { externalizeDateTime } from 'date/ExternalizeDateTime';
import { useAlert } from 'libs/alert';
import { FormProvider, useForm, useFormState } from 'react-hook-form';

import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';

import { ConceptForm } from './concept/ConceptForm';
import styles from './edit-valueset.module.scss';

type Props = {
    valueset: string;
    concept: Concept;
    onClose: () => void;
    onCancel: () => void;
    onUpdated: () => void;
};
export const EditConcept = ({ valueset, concept, onClose, onCancel, onUpdated }: Props) => {
    const form = useForm<UpdateConceptRequest>({
        mode: 'onBlur',
        defaultValues: {
            ...concept,
            effectiveToTime: internalizeDate(concept.effectiveToTime) ?? undefined,
            effectiveFromTime: internalizeDate(concept.effectiveFromTime) ?? internalizeDate(new Date()),
        },
    });
    const { isDirty, isValid } = useFormState(form);
    const { showError, showSuccess } = useAlert();
    const { response, error, update } = useUpdateConcept();

    const handleSave = () => {
        update(valueset, concept.localCode, {
            ...form.getValues(),
            effectiveToTime: externalizeDateTime(form.getValues('effectiveToTime')) ?? undefined,
            effectiveFromTime: externalizeDateTime(form.getValues('effectiveFromTime')) ?? new Date().toISOString(),
        });
    };

    useEffect(() => {
        if (response) {
            showSuccess(`Successfully update concept: ${concept.localCode}`);
            onUpdated();
        } else if (error) {
            showError(`Failed to update concept: ${concept.localCode}`);
        }
    }, [response, error]);

    return (
        <>
            <CloseableHeader title={<div className={styles.addValuesetHeader}>Edit concept</div>} onClose={onClose} />
            <div className={styles.content}>
                <FormProvider {...form}>
                    <ConceptForm isEditing={true} />
                </FormProvider>
            </div>

            <ButtonBar>
                <Button onClick={onCancel} type="button" outline={true}>
                    Cancel
                </Button>
                <Button disabled={!isDirty || !isValid} type="button" onClick={handleSave}>
                    Save changes
                </Button>
            </ButtonBar>
        </>
    );
};
