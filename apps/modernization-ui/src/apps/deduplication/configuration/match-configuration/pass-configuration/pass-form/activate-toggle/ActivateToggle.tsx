import { Pass } from 'apps/deduplication/api/model/Pass';
import { Toggle } from 'design-system/toggle/Toggle';
import { Controller, useFormContext } from 'react-hook-form';
import styles from './activate-toggle.module.scss';

export const ActivateToggle = () => {
    const form = useFormContext<Pass>();

    return (
        <div className={styles.activateToggle}>
            <Controller
                control={form.control}
                name="active"
                render={({ field: { value, name, onChange } }) => (
                    <Toggle name={name} value={value} onChange={onChange} label="Activate this pass configuration" />
                )}
            />
        </div>
    );
};
