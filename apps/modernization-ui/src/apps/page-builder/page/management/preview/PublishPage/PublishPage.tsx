import { Button, ErrorMessage, Form, Label, ModalRef, ModalToggleButton, Textarea } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { PageInformationService, PagePublishControllerService, SelectableCondition } from 'apps/page-builder/generated';
import { Dispatch, RefObject, SetStateAction, useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { usePageManagement } from '../../usePageManagement';
import styles from './publish-page.module.scss';

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
    const { showAlert } = useAlert();
    const [conditions, setConditions] = useState<SelectableCondition[] | undefined>([]);

    useEffect(() => {
        if (page) {
            PageInformationService.find({
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
                    title: 'error',
                    message: error.message
                });
            } else {
                console.error(error);
                showAlert({
                    type: 'error',
                    title: 'error',
                    message: 'An unknown error occurred'
                });
            }
        };
        try {
            PagePublishControllerService.publishPage({
                id: page.id,
                requestBody: { versionNotes: data.notes }
            })
                .then(() => {
                    if (onPublishing) {
                        onPublishing(false);
                    }
                    modalRef.current?.toggleModal();
                    showAlert({
                        type: 'success',
                        title: 'Success',
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
                    You have indicated that you would like to publish the <b>"{page.name}"</b> page. Please enter the
                    <b> version notes </b> below, review the <b> related condition(s)</b>, then select <b> publish </b>{' '}
                    to continue, or select <b> cancel </b> to return to view the page.
                </p>
                <p className={styles.required}>
                    <span className={styles.requiredIndicator}>*</span> Indicates a required field.
                </p>
                <Controller
                    control={control}
                    name="notes"
                    rules={{ required: { value: true, message: 'Version notes required' }, ...maxLengthRule(2000) }}
                    render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                        <>
                            <Label htmlFor={name}>
                                <b>Version notes </b>
                                <span className={styles.required}>
                                    <span>*</span>
                                </span>
                            </Label>
                            <Textarea
                                onChange={onChange}
                                defaultValue={value}
                                name={name}
                                id={name}
                                rows={1}
                                onBlur={onBlur}
                            />
                            {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        </>
                    )}
                />
                <div className={styles.conditions}>
                    <h4>Related condition(s)</h4>
                    {conditions?.length && conditions.filter((c) => c.name).length ? (
                        conditions.map((condition, i) => <p key={i}>{condition.name}</p>)
                    ) : (
                        <ErrorMessage>
                            At least one condition must be related to this page before it can be published. Please
                            update the Page Details by mapping Related Condition(s) to the page.
                        </ErrorMessage>
                    )}
                </div>
            </div>
            <div className={styles.footer}>
                <ModalToggleButton type="button" closer outline modalRef={modalRef}>
                    Cancel
                </ModalToggleButton>
                <Button
                    type="submit"
                    className="publishBtnOnPublishPageModal"
                    data-testid="publishBtnOnPublishPageModal"
                    disabled={
                        !publishForm.formState.isValid ||
                        !(conditions?.length && conditions.filter((c) => c.name).length)
                    }>
                    Publish
                </Button>
            </div>
        </Form>
    );
};
