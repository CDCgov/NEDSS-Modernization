import { useEffect } from 'react';

import { Button } from '@trussworks/react-uswds';
import { FormProvider, useForm } from 'react-hook-form';

import { CreateValuesetRequest, Valueset } from 'apps/page-builder/generated';
import { useUpdateValueset } from 'apps/page-builder/hooks/api/useUpdateValueset';
import { useAlert } from 'libs/alert';

import { ValuesetForm } from '../AddValueset/ValuesetForm/ValuesetForm';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';

import styles from './edit-valueset.module.scss';

type Props = {
    valueset: Valueset;
    onClose: () => void;
    onCancel: () => void;
    onValuesetUpdated: () => void;
};
export const EditValuesetDetails = ({ valueset, onClose, onCancel, onValuesetUpdated }: Props) => {
    const { showError, showSuccess } = useAlert();
    const { response, error, update } = useUpdateValueset();

    const form = useForm<CreateValuesetRequest>({
        mode: 'onBlur',
        defaultValues: {
            code: valueset.code,
            type: valueset.type,
            name: valueset.name,
            description: valueset.description,
        },
    });

    const handleUpdateValueset = () => {
        update(valueset.code, form.getValues());
    };

    useEffect(() => {
        if (response) {
            showSuccess('Successfully updated value set');
            onValuesetUpdated();
        } else if (error) {
            showError(`Failed to update value set: ${valueset.name}`);
        }
    }, [response, error]);

    return (
        <>
            <CloseableHeader title={<div className={styles.addValuesetHeader}>Edit value set</div>} onClose={onClose} />
            <div className={styles.content}>
                <FormProvider {...form}>
                    <ValuesetForm isEditing={true} />
                </FormProvider>
            </div>
            <ButtonBar>
                <Button type="button" onClick={onCancel} outline={true}>
                    Cancel
                </Button>
                <Button
                    disabled={!form.formState.isDirty || !form.formState.isValid}
                    type="button"
                    onClick={handleUpdateValueset}
                >
                    Save changes
                </Button>
            </ButtonBar>
        </>
    );
};
