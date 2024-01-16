import { Button, Form } from '@trussworks/react-uswds';
import { CreateSectionRequest, SectionControllerService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { Input } from 'components/FormInputs/Input';
import { Controller, useForm } from 'react-hook-form';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import styles from './addsection.module.scss';
import { Heading } from 'components/heading';
import { useAlert } from 'alert';

type sectionProps = {
    tabId?: number;
    pageId?: number;
    onCancel?: () => void;
    onAddSectionCreated?: () => void;
};

export const AddSection = ({ onAddSectionCreated, tabId, onCancel, pageId }: sectionProps) => {
    const form = useForm<CreateSectionRequest>();
    const { showAlert } = useAlert();

    const onSubmit = form.handleSubmit((data) => {
        data.tabId = tabId;
        SectionControllerService.createSectionUsingPost({
            authorization: authorization(),
            page: pageId!,
            request: data
        }).then(() => {
            form.reset();
            showAlert({ type: 'success', message: `You've successfully Added a new section` });
            onAddSectionCreated?.();
        });
    });

    const onClose = () => {
        form.reset();
        onCancel && onCancel();
    };

    return (
        <div className={styles.addSection}>
            <div className={styles.header}>
                <Heading level={4}>Add a section</Heading>
            </div>
            <Form onSubmit={onSubmit} className={styles.form}>
                <div className={styles.content}>
                    <Controller
                        control={form.control}
                        name="name"
                        rules={{ required: { value: true, message: 'Section name is required' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <Input
                                onChange={onChange}
                                onBlur={onBlur}
                                defaultValue={value}
                                label="Section Name"
                                type="text"
                                error={error?.message}
                                required
                                className={styles.inputField}
                            />
                        )}
                    />

                    <Controller
                        control={form.control}
                        name="visible"
                        render={() => (
                            <div className={styles.visibleToggle}>
                                Not visible
                                <ToggleButton defaultChecked className={styles.toggleBtn} />
                                Visible
                            </div>
                        )}
                    />
                </div>
            </Form>
            <div className={styles.footer}>
                <div className={styles.footerBtns}>
                    <Button type="button" onClick={onClose}>
                        Cancel
                    </Button>
                    <Button type="button" onClick={onSubmit} disabled={!form.formState.isValid}>
                        Add section
                    </Button>
                </div>
            </div>
        </div>
    );
};
