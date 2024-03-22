import { Button, Icon } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { CreateValuesetRequest, Valueset, ValueSetControllerService } from 'apps/page-builder/generated';
import { FormProvider, useForm } from 'react-hook-form';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { ValuesetForm } from './ValuesetForm/ValuesetForm';
import styles from './create-valueset.module.scss';
import { authorization } from 'authorization';

type Props = {
    onClose: () => void;
    onCancel: () => void;
    onCreated: (valueset: string) => void;
};
export const AddValueset = ({ onClose, onCancel, onCreated }: Props) => {
    const { showSuccess: alertSuccess, showError: alertError } = useAlert();
    const form = useForm<CreateValuesetRequest>({ mode: 'onBlur', defaultValues: { type: 'PHIN' } });

    const handleCreate = () => {
        ValueSetControllerService.createUsingPost({ authorization: authorization(), request: { ...form.getValues() } })
            .then((response: Valueset) => {
                alertSuccess({ message: `Successfully created value set: ${response.name}` });
                onCreated(response.code);
            })
            .catch((error) => alertError({ message: error.message }));
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
