import { Controller, useFormContext } from 'react-hook-form';
import { EditPageQuestionForm } from './EditPageQuestion';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import styles from './edit-question-form.module.scss';

export const EditFields = () => {
    const form = useFormContext<EditPageQuestionForm>();
    return (
        <>
            <Controller
                control={form.control}
                name="visible"
                render={({ field: { onChange, value } }) => (
                    <>
                        <label htmlFor="visible" className={styles.toggleLabel}>
                            Visible? <span className={styles.mandatory}>*</span>
                        </label>
                        <div className={styles.toggleGroup}>
                            <div>Hidden</div>
                            <ToggleButton checked={value === true} name="visible" onChange={onChange} />
                            <div>Visible</div>
                        </div>
                    </>
                )}
            />
            <Controller
                control={form.control}
                name="enabled"
                render={({ field: { onChange, value } }) => (
                    <>
                        <label htmlFor="enabled" className={styles.toggleLabel}>
                            Enabled? <span className={styles.mandatory}>*</span>
                        </label>
                        <div className={styles.toggleGroup}>
                            <div>Disabled</div>
                            <ToggleButton checked={value === true} name="enabled" onChange={onChange} />
                            <div>Enabled</div>
                        </div>
                    </>
                )}
            />
            <Controller
                control={form.control}
                name="required"
                render={({ field: { onChange, value } }) => (
                    <>
                        <label htmlFor="required" className={styles.toggleLabel}>
                            Required? <span className={styles.mandatory}>*</span>
                        </label>
                        <div className={styles.toggleGroup}>
                            <div>Not required</div>
                            <ToggleButton checked={value === true} name="required" onChange={onChange} />
                            <div>Required</div>
                        </div>
                    </>
                )}
            />
        </>
    );
};
