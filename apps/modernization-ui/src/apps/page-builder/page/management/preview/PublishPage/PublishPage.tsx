import { Label, Textarea, Button, Form, ModalRef, ModalToggleButton, ErrorMessage } from '@trussworks/react-uswds';
import { maxLengthRule } from 'validation/entry';
import { Controller, useForm } from 'react-hook-form';
import styles from './publish-page.module.scss';
import { Dispatch, RefObject, SetStateAction, useEffect, useState } from 'react';
import { usePageManagement } from '../../usePageManagement';
import { authorization as getAuthorization } from 'authorization';
import { PageInformationService, PagePublishControllerService, SelectableCondition } from 'apps/page-builder/generated';
import { useAlert } from 'alert';

type Props = {
    modalRef: RefObject<ModalRef>;
    onPublishing?: Dispatch<SetStateAction<boolean>>;
};

export const PublishPage = ({ modalRef, onPublishing }: Props) => {
    const { page, refresh } = usePageManagement();
    const publishForm = useForm({
        mode: 'onBlur',
        defaultValues: { notes: undefined }
    });
    const { handleSubmit, control } = publishForm;
    const authorization = getAuthorization();
    const { showAlert } = useAlert();
    const [conditions, setConditions] = useState<SelectableCondition[] | undefined>([]);

    useEffect(() => {
        if (page) {
            PageInformationService.find({
                authorization,
                page: page.id
            }).then((response) => {
                setConditions(response?.conditions);
            });
        }
    }, [page]);

    const onSubmit = handleSubmit((data) => {
        if (onPublishing) {
            onPublishing(true);
        }
        const onError = (error: Error | unknown) => {
            if (onPublishing) {
                onPublishing(false);
            }
            modalRef.current?.toggleModal();
            if (error instanceof Error) {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: error.message
                });
            } else {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: 'An unknown error occurred'
                });
            }
        };
        try {
            PagePublishControllerService.publishPageUsingPut({
                authorization,
                id: page.id,
                request: { versionNotes: data.notes }
            })
                .then(() => {
                    if (onPublishing) {
                        onPublishing(false);
                    }
                    modalRef.current?.toggleModal();
                    showAlert({
                        type: 'success',
                        header: 'Success',
                        message: `${page.name} was successfully published.`
                    });
                    refresh();
                })
                .catch(onError);
        } catch (error) {
            onError(error);
        }
    });

    return (
        <Form onSubmit={onSubmit} className={styles.form}>
            <div className={styles.body}>
                <p>
                    You have indicated that you would like to publish the {page.name} page. Please enter the version
                    notes below, review the related condition(s), then select publish to continue, or select cancel to
                    return to view the page.
                </p>
                <p className={styles.required}>
                    <span>*</span> Indicates a required field.
                </p>
                <Controller
                    control={control}
                    name="notes"
                    rules={{ required: { value: true, message: 'Version notes required' }, ...maxLengthRule(2000) }}
                    render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                        <>
                            <Label htmlFor={name}>Version notes</Label>
                            <Textarea onChange={onChange} defaultValue={value} name={name} id={name} rows={1} />
                            {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        </>
                    )}
                />
            </div>
            <div className={styles.conditions}>
                <h4>Related condition(s)</h4>
                {conditions ? conditions.map((condition, i) => <p key={i}>{condition.name}</p>) : null}
            </div>
            <div className={styles.footer}>
                <ModalToggleButton type="button" closer outline modalRef={modalRef}>
                    Cancel
                </ModalToggleButton>
                <Button type="submit" disabled={!publishForm.formState.isValid}>
                    Submit
                </Button>
            </div>
        </Form>
    );
};
