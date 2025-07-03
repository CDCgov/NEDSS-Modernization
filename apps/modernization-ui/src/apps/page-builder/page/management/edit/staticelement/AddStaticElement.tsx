import { Form, ModalRef, Button, ModalToggleButton } from '@trussworks/react-uswds';
import {
    AddDefault,
    AddHyperlink,
    AddReadOnlyComments,
    PageStaticControllerService
} from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { RefObject } from 'react';
import { Controller, FormProvider, useForm, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry/maxLengthRule';
import { HyperlinkFields } from './HyperlinkFields';
import { CommentsFields } from './CommentsFields';
import { usePageManagement } from '../../usePageManagement';
import styles from './staticelement.module.scss';
import { useAlert } from 'libs/alert';

const staticType = [
    { value: 'LIN', name: 'Line separator' },
    { value: 'HYP', name: 'Hyperlink' },
    { value: 'COM', name: 'Comments (read-only)' }
];

type AddStaticElementModalProps = {
    modalRef?: RefObject<ModalRef>;
    subsectionId?: number;
};

type StaticElementType = {
    type: 'HYP' | 'COM' | 'LIN' | 'PAR' | 'ELE' | '';
};

type StaticElementFormValues = (AddReadOnlyComments | AddHyperlink | AddDefault) & StaticElementType;

export const AddStaticElement = ({ modalRef, subsectionId }: AddStaticElementModalProps) => {
    const form = useForm<StaticElementFormValues>({
        mode: 'onBlur'
    });
    const watch = useWatch({ control: form.control });
    const { page, refresh } = usePageManagement();
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
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticHyperLink({
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The element ${(data as AddHyperlink).label} has been successfully added.`);
                    refresh();
                });
                break;
            }
            case 'COM': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticReadOnlyComments({
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The comment element has been successfully added.`);
                    refresh();
                });
                break;
            }
            case 'LIN': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticLineSeparator({
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The line separator element has been successfully added.`);
                    refresh();
                });
                break;
            }
            case 'ELE': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticOriginalElectronicDocList({
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The electronic document list has been successfully added.`);
                    refresh();
                });
                break;
            }
            case 'PAR': {
                data.subSectionId = subsectionId;
                PageStaticControllerService.addStaticReadOnlyParticipantsList({
                    page: page.id,
                    requestBody: data
                }).then(() => {
                    form.reset();
                    handleAlert(`The participant list has been successfully added.`);
                    refresh();
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
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    label="Choose a static element"
                                    options={staticType}
                                    required
                                    defaultValue={value}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    error={error?.message}
                                    data-testid="staticType"
                                    className={styles.select_input}></SelectInput>
                            )}
                        />
                    </div>
                    {watch.type && (
                        <>
                            {watch.type === 'HYP' && (
                                <FormProvider {...form}>
                                    <HyperlinkFields />
                                </FormProvider>
                            )}
                            {watch.type === 'COM' && (
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
