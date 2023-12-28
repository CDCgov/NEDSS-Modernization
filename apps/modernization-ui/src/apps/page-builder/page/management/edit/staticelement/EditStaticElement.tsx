import { Button, Form, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { AddDefault, AddHyperlink, AddReadOnlyComments, PagesQuestion } from 'apps/page-builder/generated';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { RefObject } from 'react';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import styles from './staticelement.module.scss';
import { maxLengthRule } from 'validation/entry';
import { CommentsFields } from './CommentsFields';
import { HyperlinkFields } from './HyperlinkFields';

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

type StaticElementType = {
    type: 'HYP' | 'COM' | 'LIN' | 'PAR' | 'ELE' | '';
};

type StaticElementFormValues = (AddReadOnlyComments | AddHyperlink | AddDefault) & StaticElementType;

export const EditStaticElement = ({ modalRef, question }: EditStaticProps) => {
    const form = useForm<StaticElementFormValues>({
        mode: 'onBlur'
    });
    // const { page, fetch } = usePageManagement();
    // const { showAlert } = useAlert();

    const handleSubmit = () => {
        onSubmit();
    };

    // const handleAlert = (message: string) => {
    //     showAlert({ message: message, type: 'success' });
    // };

    const onSubmit = form.handleSubmit((data) => {
        switch (data.type) {
            case 'HYP':
                console.log(`hi`);
                break;
        }
    });

    const checkStaticType = (): string => {
        if (question.displayComponent === 1003) {
            return `HYP`;
        }
        return `something`;
    };

    return (
        <div className={styles.static_element}>
            <Form onSubmit={onSubmit} className={styles.form}>
                <div className={styles.container}>
                    <div className={styles.staticType}>
                        <Controller
                            control={form.control}
                            name="type"
                            render={({ fieldState: { error } }) => (
                                <SelectInput
                                    label="Choose a static element"
                                    options={staticType}
                                    required
                                    defaultValue={checkStaticType()}
                                    error={error?.message}
                                    data-testid="staticType"
                                    disabled
                                    className={styles.select_input}></SelectInput>
                            )}
                        />
                    </div>
                    <>
                        {question.displayComponent === 1003 && (
                            <FormProvider {...form}>
                                <HyperlinkFields />
                            </FormProvider>
                        )}
                        {question.displayComponent === 1014 && (
                            <FormProvider {...form}>
                                <CommentsFields question={question} />
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
