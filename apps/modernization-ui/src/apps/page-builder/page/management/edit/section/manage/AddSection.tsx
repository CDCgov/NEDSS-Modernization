import { Button, Form } from '@trussworks/react-uswds';
import { CreateSectionRequest, SectionControllerService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { Input } from 'components/FormInputs/Input';
import { Controller, useForm } from 'react-hook-form';
import { usePageManagement } from '../../../usePageManagement';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import styles from './addsection.module.scss';

type sectionProps = {
    tabId?: number;
    onCloseModal: () => void;
    refresh?: () => void;
};

export const AddSection = ({ onCloseModal, tabId, refresh }: sectionProps) => {
    const form = useForm<CreateSectionRequest>();
    const { page } = usePageManagement();

    const onSubmit = form.handleSubmit((data) => {
        data.tabId = tabId;
        SectionControllerService.createSectionUsingPost({
            authorization: authorization(),
            page: page.id,
            request: data
        }).then(() => {
            form.reset();
            // handle alert here
            refresh && refresh();
            onCloseModal && onCloseModal();
        });
    });

    const onClose = () => {
        form.reset();
        onCloseModal && onCloseModal();
    };

    return (
        <div className={styles.addSection}>
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
            </Form>
        </div>
    );
};
