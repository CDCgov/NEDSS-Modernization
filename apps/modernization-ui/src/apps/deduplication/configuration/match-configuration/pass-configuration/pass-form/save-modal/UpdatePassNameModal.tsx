import { useEffect } from 'react';

import { Controller, useForm } from 'react-hook-form';

import { Input } from 'components/FormInputs/Input';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Modal } from 'design-system/modal';

import styles from './save-pass-modal.module.scss';

type Props = {
    name: string;
    description?: string;
    visible: boolean;
    onCancel: () => void;
    onAccept: (name: string, description?: string) => void;
};
export const UpdatePassNameModal = ({ name, description, visible, onCancel, onAccept }: Props) => {
    const form = useForm<{ name: string; description?: string }>({
        mode: 'onBlur',
        defaultValues: { name, description },
    });

    useEffect(() => {
        form.reset({ name, description }, { keepDefaultValues: false });
    }, [name, description]);

    const footer = () => (
        <>
            <Button secondary={true} onClick={onCancel} data-close-modal={true}>
                Cancel
            </Button>
            <Button
                disabled={!form.formState.isValid}
                onClick={() => onAccept(form.getValues().name, form.getValues().description)}
                data-close-modal={true}
            >
                Save
            </Button>
        </>
    );
    return (
        <Shown when={visible}>
            <Modal
                id="update-pass-name-modal"
                size="small"
                title="Save pass configuration"
                onClose={onCancel}
                footer={footer}
                className={styles.savePassModal}
            >
                <Controller
                    control={form.control}
                    name="name"
                    rules={{ required: true }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            label="Pass title"
                            orientation="vertical"
                            onBlur={onBlur}
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            name={name}
                            id={name}
                            error={error?.message}
                            required={true}
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="description"
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            label="Description"
                            orientation="vertical"
                            onBlur={onBlur}
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            multiline={true}
                            name={name}
                            id={name}
                            error={error?.message}
                        />
                    )}
                />
            </Modal>
        </Shown>
    );
};
