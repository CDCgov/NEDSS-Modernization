import { Button, Form } from '@trussworks/react-uswds';
import { useAlert } from 'libs/alert';
import {
    PageStaticControllerService,
    PagesQuestion,
    UpdateDefault,
    UpdateHyperlink,
    UpdateReadOnlyComments
} from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { usePageManagement } from '../../usePageManagement';
import { CommentsFields } from './CommentsFields';
import { HyperlinkFields } from './HyperlinkFields';
import styles from './staticelement.module.scss';

const staticType = [
    { value: 'LIN', name: 'Line separator' },
    { value: 'HYP', name: 'Hyperlink' },
    { value: 'COM', name: 'Comments (read-only)' },
    { value: 'PAR', name: 'Participant list (read-only)' },
    { value: 'ELE', name: 'Electronic document list (read-only)' }
];

type EditStaticProps = {
    question: PagesQuestion;
    onCloseModal?: () => void;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const originalElecDocId = 1036;
const readOnlyPartId = 1030;

export const staticElementTypes = [hyperlinkId, lineSeparatorId, readOnlyPartId, commentsReadOnlyId, originalElecDocId];

type StaticElementFormValues = UpdateReadOnlyComments | UpdateHyperlink | UpdateDefault;

export const EditStaticElement = ({ question, onCloseModal }: EditStaticProps) => {
    const { refresh } = usePageManagement();
    const form = useForm<StaticElementFormValues>({
        mode: 'onBlur',
        defaultValues: {
            adminComments: question.adminComments,
            label: question.name,
            commentsText: question.name,
            linkUrl: question.defaultValue
        }
    });

    const { page } = usePageManagement();
    const { showAlert } = useAlert();

    const handleSubmit = () => {
        onSubmit();
        onCloseModal?.();
    };

    const handleAlert = (message: string) => {
        showAlert({ message: message, type: 'success' });
    };

    const onSubmit = form.handleSubmit((data) => {
        switch (question.displayComponent) {
            case hyperlinkId:
                PageStaticControllerService.updateHyperlink({
                    id: question.id,
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The element ${(data as UpdateHyperlink).label} has been successfully updated.`);
                    refresh();
                });
                break;
            case commentsReadOnlyId:
                PageStaticControllerService.updateReadOnlyComments({
                    id: question.id,
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The comment element has been successfully updated.`);
                    refresh();
                });
                break;
            case lineSeparatorId:
                PageStaticControllerService.updateDefaultStaticElement({
                    id: question.id,
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The line separator element has been successfully updated.`);
                    refresh();
                });
                break;
            case readOnlyPartId:
                PageStaticControllerService.updateDefaultStaticElement({
                    id: question.id,
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The participant list has been successfully updated.`);
                    refresh();
                });
                break;
            case originalElecDocId:
                PageStaticControllerService.updateDefaultStaticElement({
                    id: question.id,
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The electronic document list has been successfully updated.`);
                    refresh();
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

    const onCancel = () => {
        form.reset();
        onCloseModal?.();
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
                </div>

                <div className={styles.footer_buttons}>
                    <Button outline onClick={onCancel} type={'button'}>
                        Cancel
                    </Button>
                    <Button
                        disabled={!form.formState.isDirty || !form.formState.isValid}
                        onClick={handleSubmit}
                        type={'button'}
                        aria-label="submit-btn">
                        Save changes
                    </Button>
                </div>
            </Form>
        </div>
    );
};
