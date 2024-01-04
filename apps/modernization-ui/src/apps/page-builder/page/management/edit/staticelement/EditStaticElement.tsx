import { Button, Form, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import {
    PageStaticControllerService,
    PagesQuestion,
    UpdateDefault,
    UpdateHyperlink,
    UpdateReadOnlyComments
} from 'apps/page-builder/generated';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { RefObject } from 'react';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import styles from './staticelement.module.scss';
import { maxLengthRule } from 'validation/entry';
import { CommentsFields } from './CommentsFields';
import { HyperlinkFields } from './HyperlinkFields';
import { useAlert } from 'alert/useAlert';
import { usePageManagement } from '../../usePageManagement';
import { authorization } from 'authorization';

const staticType = [
    { value: 'LIN', name: 'Line separator' },
    { value: 'HYP', name: 'Hyperlink' },
    { value: 'COM', name: 'Comments (read-only)' },
    { value: 'PAR', name: 'Participant list (read-only)' },
    { value: 'ELE', name: 'Electronic document list (read-only)' }
];

type EditStaticProps = {
    modalRef?: RefObject<ModalRef>;
    question: PagesQuestion;
    onChange?: () => void;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const originalElecDocId = 1036;
const readOnlyPartId = 1030;

type StaticElementFormValues = UpdateReadOnlyComments | UpdateHyperlink | UpdateDefault;

export const EditStaticElement = ({ modalRef, question }: EditStaticProps) => {
    const form = useForm<StaticElementFormValues>({
        mode: 'onBlur',
        defaultValues: {
            adminComments: question.adminComments,
            label: question.name,
            commentsText: question.name,
            linkUrl: question.defaultValue
        }
    });

    // const watch = useWatch({ control: form.control });

    const { page, fetch } = usePageManagement();
    const { showAlert } = useAlert();

    // const checkFormValues = () => {
    //     switch (question.displayComponent) {
    //         case hyperlinkId:
    //             if (watch.adminComments !== question.adminComments) {
    //                 return false;
    //             } else {
    //                 return true;
    //             }
    //     }
    // };

    const handleSubmit = () => {
        onSubmit();
    };

    const handleAlert = (message: string) => {
        showAlert({ message: message, type: 'success' });
    };

    const onSubmit = form.handleSubmit((data) => {
        switch (question.displayComponent) {
            case hyperlinkId:
                PageStaticControllerService.updateHyperlinkUsingPut({
                    authorization: authorization(),
                    id: question.id,
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The element ${(data as UpdateHyperlink).label} has been successfully updated.`);
                    fetch(page.id);
                });
                break;
            case commentsReadOnlyId:
                PageStaticControllerService.updateReadOnlyCommentsUsingPut({
                    authorization: authorization(),
                    id: question.id,
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The comment element has been successfully updated.`);
                    fetch(page.id);
                });
                break;
            case lineSeparatorId:
                PageStaticControllerService.updateDefaultStaticElementUsingPut({
                    authorization: authorization(),
                    id: question.id,
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The line separator element has been successfully updated.`);
                    fetch(page.id);
                });
                break;
            case readOnlyPartId:
                PageStaticControllerService.updateDefaultStaticElementUsingPut({
                    authorization: authorization(),
                    id: question.id,
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The participant list has been successfully updated.`);
                    fetch(page.id);
                });
                break;
            case originalElecDocId:
                PageStaticControllerService.updateDefaultStaticElementUsingPut({
                    authorization: authorization(),
                    id: question.id,
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The electronic document list has been successfully updated.`);
                    fetch(page.id);
                });
                break;
        }
    });

    const checkStaticType = (displayComponent: number | undefined): string => {
        switch (displayComponent) {
            case hyperlinkId:
                return 'HYP';
            case lineSeparatorId:
                return 'LIN';
            case readOnlyPartId:
                return 'PAR';
            case commentsReadOnlyId:
                return 'COM';
            case originalElecDocId:
                return 'ELE';
            default:
                return '';
        }
    };

    return (
        <div className={styles.static_element}>
            <Form onSubmit={onSubmit} className={styles.form}>
                <div className={styles.container}>
                    <div className={styles.staticType}>
                        <SelectInput
                            label="Choose a static element"
                            options={staticType}
                            required
                            defaultValue={checkStaticType(question.displayComponent)}
                            aria-label="staticType"
                            disabled
                            className={styles.select_input}></SelectInput>
                    </div>
                    <>
                        {question.displayComponent === hyperlinkId && (
                            <FormProvider {...form}>
                                <HyperlinkFields />
                            </FormProvider>
                        )}
                        {question.displayComponent === commentsReadOnlyId && (
                            <FormProvider {...form}>
                                <CommentsFields />
                            </FormProvider>
                        )}
                        <Controller
                            control={form.control}
                            name="adminComments"
                            rules={{ ...maxLengthRule(2000) }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    defaultValue={value}
                                    label="Administrative Comments"
                                    type="text"
                                    ariaLabel="adminComments"
                                    multiline
                                    error={error?.message}
                                />
                            )}
                        />
                    </>
                </div>

                <div className={styles.footer_buttons}>
                    {modalRef ? (
                        <>
                            <ModalToggleButton modalRef={modalRef} closer outline onClick={() => form.reset()}>
                                Cancel
                            </ModalToggleButton>
                            <ModalToggleButton
                                modalRef={modalRef}
                                closer
                                disabled={!form.formState.isDirty || !form.formState.isValid}
                                onClick={handleSubmit}
                                aria-label='submit-btn'>
                                Save changes
                            </ModalToggleButton>
                        </>
                    ) : (
                        <>
                            <Button outline onClick={() => form.reset()} type={'button'}>
                                Cancel
                            </Button>
                            <Button
                                disabled={!form.formState.isDirty || !form.formState.isValid}
                                onClick={handleSubmit}
                                type={'button'}
                                aria-label='submit-btn'>
                                Save changes
                            </Button>
                        </>
                    )}
                </div>
            </Form>
        </div>
    );
};
