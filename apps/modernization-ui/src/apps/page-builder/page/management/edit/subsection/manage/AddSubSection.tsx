import {
    CreateSubSectionRequest,
    PagesSubSection,
    SubSectionControllerService,
    UpdateSubSectionRequest
} from 'apps/page-builder/generated';
import { Controller, useForm } from 'react-hook-form';
import styles from './addsubsection.module.scss';
import { Button, Form, Icon } from '@trussworks/react-uswds';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import { maxLengthRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { useEffect } from 'react';
import { validSubsectionNameRule } from 'validation/entry/validSubsectionNameRule';

type subSectionProps = {
    sectionId?: number;
    pageId?: number;
    onCancel?: () => void;
    onSubSectionTouched?: (section: string) => void;
    subsectionEdit?: PagesSubSection;
    isEdit?: boolean;
};

export const AddSubSection = ({
    sectionId,
    pageId,
    onCancel,
    onSubSectionTouched,
    subsectionEdit,
    isEdit
}: subSectionProps) => {
    const form = useForm<CreateSubSectionRequest | UpdateSubSectionRequest>({
        mode: 'onBlur',
        defaultValues: { visible: true }
    });

    useEffect(() => {
        if (subsectionEdit && isEdit) {
            form.reset({ name: subsectionEdit.name, visible: subsectionEdit.visible, sectionId: sectionId });
        }
    }, [subsectionEdit]);

    const onSubmit = form.handleSubmit((data) => {
        if (isEdit) {
            SubSectionControllerService.updateSubSection({
                page: pageId ?? 0,
                subSectionId: subsectionEdit?.id ?? 0,
                requestBody: { name: data.name, visible: data.visible }
            }).then(() => {
                onSubSectionTouched?.('');
            });
        } else {
            SubSectionControllerService.createSubsection({
                page: pageId ?? 0,
                requestBody: { name: data.name, visible: data.visible, sectionId: sectionId }
            }).then(() => {
                onSubSectionTouched?.(data.name ?? '');
                form.reset();
            });
        }
    });

    return (
        <div className={styles.subSection}>
            <div className={styles.header}>
                <div className={styles.headerContent}>
                    {isEdit ? <h2>Edit subsection</h2> : <h2>Add subsection</h2>}
                </div>
                <Icon.Close
                    size={3}
                    onClick={() => {
                        form.reset();
                        onCancel?.();
                    }}
                    className={styles.closeBtn}
                />
            </div>
            <Form onSubmit={onSubmit} className={styles.form}>
                <div className={styles.content}>
                    <Controller
                        control={form.control}
                        name="name"
                        rules={{
                            required: { value: true, message: 'Subsection name is required' },
                            validate: (v) => validSubsectionNameRule(v ?? ''),
                            ...maxLengthRule(50)
                        }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <Input
                                onChange={onChange}
                                onBlur={onBlur}
                                defaultValue={value}
                                label="Subsection Name"
                                type="text"
                                error={error?.message}
                                required
                                data-testid="subsectionName"
                                className={styles.inputField}
                            />
                        )}
                    />

                    <Controller
                        control={form.control}
                        name="visible"
                        render={({ field: { onChange, value } }) => (
                            <div className={styles.visibleToggle}>
                                Not visible
                                <ToggleButton
                                    defaultChecked={isEdit ? subsectionEdit?.visible : value}
                                    className={styles.toggleBtn}
                                    onChange={onChange}
                                />
                                Visible
                            </div>
                        )}
                    />
                </div>
            </Form>
            <div className={styles.footer}>
                <div className={styles.footerBtns}>
                    {isEdit ? (
                        <>
                            <Button
                                type="button"
                                outline
                                onClick={() => {
                                    form.reset();
                                    onCancel?.();
                                }}>
                                Cancel
                            </Button>
                            <Button
                                type="button"
                                onClick={onSubmit}
                                data-testid="confirmation-btn"
                                disabled={!form.formState.isDirty || !form.formState.isValid}>
                                Save changes
                            </Button>
                        </>
                    ) : (
                        <>
                            <Button
                                type="button"
                                outline
                                onClick={() => {
                                    form.reset();
                                    onCancel?.();
                                }}>
                                Cancel
                            </Button>
                            <Button
                                type="button"
                                onClick={onSubmit}
                                disabled={!form.formState.isValid}
                                data-testid="addOrEditSubsectionBtn">
                                Add subsection
                            </Button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};
