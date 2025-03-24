import { Pass } from 'apps/deduplication/api/model/Pass';
import { Input } from 'components/FormInputs/Input';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Modal } from 'design-system/modal';
import { Controller, useFormContext, useFormState } from 'react-hook-form';
import styles from './save-pass-modal.module.scss';

type Props = {
    visible: boolean;
    onCancel: () => void;
    onAccept: (value: { name: string; description?: string }) => void;
};
export const SavePassModal = ({ visible, onCancel, onAccept }: Props) => {
    const form = useFormContext<Pass>();
    const { isValid } = useFormState({ control: form.control });

    const footer = () => (
        <>
            <Button outline onClick={onCancel} data-close-modal>
                Cancel
            </Button>
            <Button disabled={!isValid} onClick={() => onAccept(form.getValues())} data-close-modal>
                Save
            </Button>
        </>
    );
    return (
        <Shown when={visible}>
            <Modal
                id={`save-pass-modal`}
                size="small"
                title="Save pass configuration"
                onClose={onCancel}
                footer={footer}
                className={styles.savePassModal}>
                <Controller
                    control={form.control}
                    name="name"
                    rules={{ required: true }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            label="Pass title"
                            orientation={'vertical'}
                            onBlur={onBlur}
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            name={name}
                            id={name}
                            error={error?.message}
                            required
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="description"
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            label="Description"
                            orientation={'vertical'}
                            onBlur={onBlur}
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            multiline
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
