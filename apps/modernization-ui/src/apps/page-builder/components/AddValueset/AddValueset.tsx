import { Button, Icon } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { CreateValuesetRequest, ValueSetControllerService, Valueset } from 'apps/page-builder/generated';
import { FormProvider, useForm } from 'react-hook-form';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { ValuesetForm } from './ValuesetForm/ValuesetForm';
import styles from './create-valueset.module.scss';

type Props = {
    onClose: () => void;
    onCancel: () => void;
    onCreated: (valueset: string) => void;
};
export const AddValueset = ({ onClose, onCancel, onCreated }: Props) => {
    const { showSuccess, showError } = useAlert();
    const form = useForm<CreateValuesetRequest>({ mode: 'onBlur', defaultValues: { type: 'PHIN' } });

    const handleCreate = () => {
        ValueSetControllerService.create({ requestBody: { ...form.getValues() } })
            .then((response: Valueset) => {
                showSuccess(`Successfully created value set: ${response.name}`);
                onCreated(response.code);
            })
            .catch((error) => {
                showError(error?.body?.message ?? 'Failed to create value set');
            });
    };

    return (
        <div className={styles.createValueset}>
            <CloseableHeader
                title={
                    <div className={styles.addValuesetHeader}>
                        <Icon.ArrowBack onClick={onCancel} /> Add value set
                    </div>
                }
                onClose={onClose}
            />
            <div className={styles.scrollableContent}>
                <FormProvider {...form}>
                    <ValuesetForm />
                </FormProvider>
            </div>
            <ButtonBar>
                <Button onClick={onCancel} type="button" outline>
                    Cancel
                </Button>
                <Button disabled={!form.formState.isValid} onClick={handleCreate} type="button">
                    Continue to add value set concept
                </Button>
            </ButtonBar>
        </div>
    );
};
