import { Button } from '@trussworks/react-uswds';

import styles from './create-concept.module.scss';
import { FormProvider, useForm } from 'react-hook-form';
import { CreateConceptRequest } from 'apps/page-builder/generated';
import { ConceptForm } from './ConceptForm';
type Props = {
    onCreated: () => void;
    onCancel: () => void;
};
export const CreateConcept = ({ onCreated, onCancel }: Props) => {
    const form = useForm<CreateConceptRequest>({
        mode: 'onBlur',
        defaultValues: { status: CreateConceptRequest.status.ACTIVE }
    });
    const handleCreate = () => {
        onCreated();
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
