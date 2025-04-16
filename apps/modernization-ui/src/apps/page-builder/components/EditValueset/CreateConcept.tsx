import { Button } from '@trussworks/react-uswds';

import { useAlert } from 'alert';
import { ConceptControllerService, CreateConceptRequest } from 'apps/page-builder/generated';
import { externalizeDateTime, internalizeDate } from 'date';
import { FormProvider, useForm } from 'react-hook-form';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { ConceptForm } from './concept/ConceptForm';
import styles from './edit-valueset.module.scss';

type Props = {
    onCreated: () => void;
    onCancel: () => void;
    onClose: () => void;
    valuesetName: string;
};
export const CreateConcept = ({ onCreated, onCancel, onClose, valuesetName }: Props) => {
    const { showError, showSuccess } = useAlert();
    const form = useForm<CreateConceptRequest>({
        mode: 'onBlur',
        defaultValues: { status: CreateConceptRequest.status.ACTIVE, effectiveFromTime: internalizeDate(new Date()) }
    });
    const handleCreate = () => {
        ConceptControllerService.createConcept({
            requestBody: {
                ...form.getValues(),
                effectiveToTime: externalizeDateTime(form.getValues('effectiveToTime')) ?? undefined,
                effectiveFromTime: externalizeDateTime(form.getValues('effectiveFromTime')) ?? new Date().toISOString()
            },
            codeSetNm: valuesetName
        })
            .then(() => {
                showSuccess(`Successfully created concept: ${form.getValues('localCode')}`);
                onCreated();
            })
            .catch((error) => showError(error?.body?.message ?? 'Failed to create concept'));
    };
    return (
        <>
            <CloseableHeader title={<div className={styles.addValuesetHeader}>Add concept</div>} onClose={onClose} />
            <div className={styles.content}>
                <FormProvider {...form}>
                    <ConceptForm />
                </FormProvider>
            </div>
            <ButtonBar>
                <Button type="button" outline onClick={onCancel}>
                    Cancel
                </Button>
                <Button disabled={!form.formState.isValid} type="button" onClick={handleCreate}>
                    Add concept
                </Button>
            </ButtonBar>
        </>
    );
};
