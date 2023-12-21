import { Button, Form, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { AddDefault, AddHyperlink, AddReadOnlyComments, PageStaticControllerService, PagesQuestion } from 'apps/page-builder/generated';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { watch } from 'fs';
import { RefObject } from 'react';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import styles from './staticelement.module.scss';
import { maxLengthRule } from 'validation/entry';
import { CommentsFields } from './CommentsFields';
import { HyperlinkFields } from './HyperlinkFields';
import { authorization } from 'authorization';
import { usePageManagement } from '../../usePageManagement';
import { useAlert } from 'alert/useAlert';

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
};

type StaticElementType = {
    type: 'HYP' | 'COM' | 'LIN' | 'PAR' | 'ELE' | '';
};

type StaticElementFormValues = (AddReadOnlyComments | AddHyperlink | AddDefault) & StaticElementType;


export const EditStaticElement = ({ modalRef, question }: EditStaticProps) => {
    const form = useForm<StaticElementFormValues>({
        mode: 'onBlur'
    });
    const { page } = usePageManagement();
    const { showAlert } = useAlert();

    const handleSubmit = () => {
        onSubmit();
    };

    const handleAlert = (message: string) => {
        showAlert({ message: message, type: 'success' });
    };

    const onSubmit = form.handleSubmit((data) => {
        switch (data.type) {
            case 'HYP': {
                PageStaticControllerService.addStaticHyperLinkUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The element ${(data as AddHyperlink).label} has been successfully updated.`);
                });
                break;
            }
            case 'COM': {
                PageStaticControllerService.addStaticReadOnlyCommentsUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The comment element has been successfully updated.`);
                });
                break;
            }
            case 'LIN': {
                PageStaticControllerService.addStaticLineSeparatorUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The line separator element has been successfully updated.`);
                });
                break;
            }
            case 'ELE': {
                PageStaticControllerService.addStaticOriginalElectronicDocListUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The electronic document list has been successfully updated.`);
                });
                break;
            }
            case 'PAR': {
                PageStaticControllerService.addStaticReadOnlyParticipantsListUsingPost({
                    authorization: authorization(),
                    page: page.id,
                    request: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The participant list has been successfully updated.`);
                });
                break;
            }
        }
    });
    


    return (
        <div className={styles.static_element}>
            <Form onSubmit={onSubmit} className={styles.form}>
                <div className={styles.container}>
                    <div className={styles.staticType}>
                        <Controller
                            control={form.control}
                            name="type"
                            rules={{ required: { value: true, message: 'Static element type is required.' } }}
                            render={({ field: { onBlur, onChange }, fieldState: { error } }) => (
                                <SelectInput
                                    label="Choose a static element"
                                    options={staticType}
                                    required
                                    defaultValue={question.dataType}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    error={error?.message}
                                    data-testid="staticType"
                                    disabled
                                    className={styles.select_input}></SelectInput>
                            )}
                        />
                    </div>
                    {question.dataType && (
                        <>
                            {question.dataType === 'HYP' && (
                                <FormProvider {...form}>
                                    <HyperlinkFields />
                                </FormProvider>
                            )}
                            {question.dataType === 'COM' && (
                                <FormProvider {...form}>
                                    <CommentsFields />
                                </FormProvider>
                            )}
                            <Controller
                                control={form.control}
                                name="adminComments"
                                rules={{ ...maxLengthRule(2000) }}
                                render={({ field: { onBlur, onChange }, fieldState: { error } }) => (
                                    <Input
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        defaultValue={question.adminComments}
                                        label="Administrative Comments"
                                        type="text"
                                        data-testid="adminComments"
                                        multiline
                                        error={error?.message}
                                    />
                                )}
                            />
                        </>
                    )}
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
                                disabled={!form.formState.isValid}
                                onClick={handleSubmit}
                                data-testid="submit-btn">
                                Save changes
                            </ModalToggleButton>
                        </>
                    ) : (
                        <>
                            <Button outline onClick={() => form.reset()} type={'button'}>
                                Cancel
                            </Button>
                            <Button
                                disabled={!form.formState.isValid}
                                onClick={handleSubmit}
                                type={'button'}
                                data-testid="submit-btn">
                                Save changes
                            </Button>
                        </>
                    )}
                </div>
            </Form>
        </div>
    );
};
