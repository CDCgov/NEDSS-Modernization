import { CreateSubSectionRequest, SubSectionControllerService } from 'apps/page-builder/generated';
import { Controller, useForm } from 'react-hook-form';
import styles from './addsubsection.module.scss';
import { Heading } from 'components/heading';
import { Button, Form, Icon } from '@trussworks/react-uswds';
import { authorization } from 'authorization';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import { maxLengthRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';

type subSectionProps = {
    sectionId?: number;
    pageId?: number;
    onCancel?: () => void;
    onSubSectionTouched?: (section: string) => void;
};

export const AddSubSection = ({ sectionId, pageId, onCancel, onSubSectionTouched }: subSectionProps) => {
    const form = useForm<CreateSubSectionRequest>();

    const onSubmit = form.handleSubmit((data) => {
        data.sectionId = sectionId;
        SubSectionControllerService.createSubsectionUsingPost({
            authorization: authorization(),
            page: pageId ?? 0,
            request: data
        }).then(() => {
            onSubSectionTouched?.(data.name ?? '');
            form.reset();
        });
    });

    return (
        <div className={styles.subSection}>
            <div className={styles.header}>
                <div className={styles.headerContent}>
                    <Heading level={4}>Add subsection</Heading>
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
                            ...maxLengthRule(50)
                        }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <>
                                <Input
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    defaultValue={value}
                                    label="Subsection Name"
                                    type="text"
                                    error={error?.message}
                                    required
                                    className={styles.inputField}
                                />
                            </>
                        )}
                    />

                    <Controller
                        control={form.control}
                        name="visible"
                        render={({ field: { onChange, value } }) => (
                            <div className={styles.visibleToggle}>
                                Not visible
                                <ToggleButton defaultChecked={value} className={styles.toggleBtn} onChange={onChange} />
                                Visible
                            </div>
                        )}
                    />
                </div>
            </Form>
            <div className={styles.footer}>
                <div className={styles.footerBtns}>
                    <Button
                        type="button"
                        onClick={() => {
                            form.reset();
                            onCancel?.();
                        }}>
                        Cancel
                    </Button>
                    <Button type="button" onClick={onSubmit} disabled={!form.formState.isValid}>
                        Add subsection
                    </Button>
                </div>
            </div>
        </div>
    );
};
