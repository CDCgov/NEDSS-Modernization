import { Button } from '@trussworks/react-uswds';

import { useAlert } from 'alert';
import { ConceptControllerService, CreateConceptRequest } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { externalizeDateTime } from 'date';
import { FormProvider, useForm } from 'react-hook-form';
import { ConceptForm } from './ConceptForm';
import styles from './create-concept.module.scss';
type Props = {
    onCreated: () => void;
    onCancel: () => void;
    valuesetName: string;
};
export const CreateConcept = ({ onCreated, onCancel, valuesetName }: Props) => {
    const { alertError, alertSuccess } = useAlert();
    const form = useForm<CreateConceptRequest>({
        mode: 'onBlur',
        defaultValues: { status: CreateConceptRequest.status.ACTIVE }
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
        <div className={styles.createConcepts}>
            <FormProvider {...form}>
                <ConceptForm />
            </FormProvider>
            <div className={styles.buttons}>
                <Button type="button" outline onClick={onCancel}>
                    Cancel
                </Button>
                <Button disabled={!form.formState.isValid} type="button" onClick={handleCreate}>
                    Add concept
                </Button>
            </div>
        </div>
    );
};
