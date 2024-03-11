import { Button } from '@trussworks/react-uswds';

import { useAlert } from 'alert';
import { ConceptControllerService, CreateConceptRequest } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { externalizeDateTime, internalizeDate } from 'date';
import { FormProvider, useForm } from 'react-hook-form';
import { ConceptForm } from './concept/ConceptForm';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import styles from './edit-valueset.module.scss';

type Props = {
    onCreated: () => void;
    onCancel: () => void;
    onClose: () => void;
    valuesetName: string;
};
export const CreateConcept = ({ onCreated, onCancel, onClose, valuesetName }: Props) => {
    const { alertError, alertSuccess } = useAlert();
    const form = useForm<CreateConceptRequest>({
        mode: 'onBlur',
        defaultValues: { status: CreateConceptRequest.status.ACTIVE, effectiveFromTime: internalizeDate(new Date()) }
    });
    const handleCreate = () => {
        ConceptControllerService.createConceptUsingPost({
            authorization: authorization(),
            request: {
                ...form.getValues(),
                effectiveToTime: externalizeDateTime(form.getValues('effectiveToTime')) ?? undefined
            },
            codeSetNm: valuesetName
        })
            .then(() => {
                alertSuccess({ message: `Successfully created concept: ${form.getValues('localCode')}` });
                onCreated();
            })
            .catch(() => alertError({ message: 'Failed to create concept' }));
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
