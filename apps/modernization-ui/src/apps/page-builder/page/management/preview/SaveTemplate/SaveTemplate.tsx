import { RefObject } from 'react';

import { Button, Form, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Controller, useForm } from 'react-hook-form';

import { CreateTemplateRequest, PagesService } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { useAlert } from 'libs/alert';
import { logErrorToUserConsole } from 'utils/logging';
import { maxLengthRule } from 'validation/entry';

import { usePageManagement } from '../../usePageManagement';

import styles from './save-tempate.module.scss';

const initSave = {
    name: undefined,
    description: undefined,
};

type Props = {
    modalRef: RefObject<ModalRef>;
};

export const SaveTemplate = ({ modalRef }: Props) => {
    const { page } = usePageManagement();
    const saveForm = useForm({
        mode: 'onBlur',
        defaultValues: { ...initSave },
    });
    const { handleSubmit, control } = saveForm;
    const { showAlert } = useAlert();

    const onSubmit = handleSubmit((data) => {
        if (data.name && data.description) {
            const request: CreateTemplateRequest = {
                name: data.name,
                description: data.description,
            };
            try {
                PagesService.createTemplate({
                    page: page.id,
                    requestBody: request,
                }).then(() => {
                    modalRef.current?.toggleModal();
                    showAlert({
                        type: 'success',
                        title: 'Success',
                        message: `${data.name} was saved successfully`,
                    });
                });
            } catch (error: unknown) {
                modalRef.current?.toggleModal();
                logErrorToUserConsole(error);
                if (error instanceof Error) {
                    showAlert({
                        type: 'error',
                        title: 'error',
                        message: error.message,
                    });
                } else {
                    showAlert({
                        type: 'error',
                        title: 'error',
                        message: 'An unknown error occurred',
                    });
                }
            }
        }
    });

    return (
        <Form onSubmit={onSubmit} className={styles.saveTemplate}>
            <div className={styles.body}>
                <p>
                    All fields with <span>*</span> are required.
                </p>
                <Controller
                    control={control}
                    name="name"
                    rules={{ required: { value: true, message: 'Name is required' }, ...maxLengthRule(50) }}
                    render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            name={name}
                            type="text"
                            label="Template name"
                            defaultValue={value}
                            onChange={onChange}
                            error={error?.message}
                            required={true}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="description"
                    rules={{ required: { value: true, message: 'Description is required' }, ...maxLengthRule(50) }}
                    render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            name={name}
                            type="text"
                            label="Template description"
                            defaultValue={value}
                            onChange={onChange}
                            error={error?.message}
                            required={true}
                        />
                    )}
                />
            </div>
            <div className={styles.footer}>
                <ModalToggleButton type="button" closer={true} outline={true} modalRef={modalRef}>
                    Cancel
                </ModalToggleButton>
                <Button type="submit" disabled={!saveForm.formState.isValid}>
                    Save
                </Button>
            </div>
        </Form>
    );
};
