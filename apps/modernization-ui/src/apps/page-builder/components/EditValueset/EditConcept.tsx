import { Concept, UpdateConceptRequest } from 'apps/page-builder/generated';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { Button } from '@trussworks/react-uswds';
import styles from './edit-valueset.module.scss';
import { ConceptForm } from './concept/ConceptForm';
import { FormProvider, useForm, useFormState } from 'react-hook-form';
import { useUpdateConcept } from 'apps/page-builder/hooks/api/useUpdateConcept';
import { useEffect } from 'react';
import { useAlert } from 'alert';
import { externalizeDateTime } from 'date/ExternalizeDateTime';
import { internalizeDate } from 'date';

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
        defaultValues: { ...concept, effectiveToTime: internalizeDate(concept.effectiveToTime) ?? undefined }
    });
    const { isDirty, isValid } = useFormState(form);
    const { alertError, alertSuccess } = useAlert();
    const { response, error, update } = useUpdateConcept();

    const handleSave = () => {
        update(valueset, concept.localCode, {
            ...form.getValues(),
            effectiveToTime: externalizeDateTime(form.getValues('effectiveToTime')) ?? undefined
        });
    };

    useEffect(() => {
        if (response) {
            alertSuccess({ message: `Successfully update concept: ${concept.localCode}` });
            onUpdated();
        } else if (error) {
            alertError({ message: `Failed to update concept: ${concept.localCode}` });
        }
    }, [response, error]);

    return (
        <>
            <CloseableHeader title={<div className={styles.addValuesetHeader}>Edit concept</div>} onClose={onClose} />
            <div className={styles.content}>
                <FormProvider {...form}>
                    <ConceptForm isEditing />
                </FormProvider>
            </div>

            <ButtonBar>
                <Button onClick={onCancel} type="button" outline>
                    Cancel
                </Button>
                <Button disabled={!isDirty || !isValid} type="button" onClick={handleSave}>
                    Save change
                </Button>
            </ButtonBar>
        </>
    );
};
