import { CreateTabRequest } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { ToggleButton } from '../../../../../components/ToggleButton';
import styles from './addedittab.module.scss';

type TabEntry = { name: string | undefined; visible: boolean; order: number };

export const AddEditTab = () => {
    const { control } = useFormContext<CreateTabRequest>();

    return (
        <div className={styles.addEditTab}>
            <Controller
                control={control}
                name="name"
                rules={{
                    ...maxLengthRule(50),
                    required: { value: true, message: 'Tab name is required' }
                }}
                defaultValue={control._formValues.name}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Tab name"
                        className={styles.inputField}
                        type="text"
                        id="tab-name"
                        data-testid="tab-name"
                        name={name}
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="visible"
                render={({ field: { onChange, value } }) => (
                    <div className={styles.toggle}>
                        <label> Not Visible</label>
                        <ToggleButton checked={value === true} name="visible" onChange={onChange} />
                        <label> Visible</label>
                    </div>
                )}
            />
        </div>
    );
};

export type { TabEntry };
