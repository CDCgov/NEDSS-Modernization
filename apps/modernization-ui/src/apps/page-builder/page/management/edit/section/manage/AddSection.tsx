import { Button, Form } from '@trussworks/react-uswds';
import {
    CreateSectionRequest,
    PagesSection,
    SectionControllerService,
    UpdateSectionRequest
} from 'apps/page-builder/generated';
import { Controller, useForm } from 'react-hook-form';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import styles from './addsection.module.scss';
import { useEffect } from 'react';
import { maxLengthRule, validPageNameRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { notEmptyRule } from 'validation/entry/notEmptyRule';

type sectionProps = {
    tabId?: number;
    pageId?: number;
    onCancel?: () => void;
    onSectionTouched?: () => void;
    onAddSection?: (section: string) => void;
    section?: PagesSection;
    isEdit: boolean;
};

export const AddSection = ({
    onSectionTouched,
    tabId,
    onCancel,
    pageId,
    section,
    isEdit,
    onAddSection
}: sectionProps) => {
    const form = useForm<CreateSectionRequest | UpdateSectionRequest>({
        mode: 'onBlur',
        defaultValues: { visible: true }
    });

    useEffect(() => {
        if (section && isEdit) {
            form.reset({ name: section.name, tabId: tabId, visible: section.visible });
        }
    }, [section]);
    const onSubmit = form.handleSubmit((data) => {
        if (isEdit) {
            SectionControllerService.updateSection({
                page: pageId ?? 0,
                section: section?.id ?? 0,
                requestBody: { name: data.name, visible: data.visible }
            }).then(() => {
                form.reset();
                onSectionTouched?.();
            });
        } else {
            SectionControllerService.createSection({
                page: pageId ?? 0,
                requestBody: { name: data.name, visible: data.visible, tabId: tabId }
            }).then(() => {
                form.reset();
                onAddSection?.(data.name ?? '');
                onSectionTouched?.();
            });
        }
    });

    const onClose = () => {
        form.reset();
        onCancel && onCancel();
    };

    return (
        <div className={styles.addSection}>
            <div className={styles.header}>{isEdit ? <h2>Edit section</h2> : <h2>Add a section</h2>}</div>
            <Form onSubmit={onSubmit} className={styles.form}>
                <div className={styles.content}>
                    <Controller
                        control={form.control}
                        name="name"
                        rules={{
                            required: { value: true, message: 'Section name is required' },
                            ...maxLengthRule(50),
                            ...validPageNameRule,
                            ...notEmptyRule
                        }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <Input
                                onChange={onChange}
                                onBlur={onBlur}
                                defaultValue={value}
                                label="Section Name"
                                type="text"
                                error={error?.message}
                                required
                                className={`${styles.inputField} sectionName`}
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
                                    defaultChecked={section ? section.visible : value}
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
                    <Button type="button" outline onClick={onClose}>
                        Cancel
                    </Button>
                    {isEdit ? (
                        <Button
                            type="button"
                            onClick={onSubmit}
                            disabled={!form.formState.isDirty || !form.formState.isValid}
                            className="saveChangesBtn">
                            Save changes
                        </Button>
                    ) : (
                        <Button
                            className="addSectionBtn"
                            type="button"
                            onClick={onSubmit}
                            disabled={!form.formState.isValid}>
                            Add section
                        </Button>
                    )}
                </div>
            </div>
        </div>
    );
};
