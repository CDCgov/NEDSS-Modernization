import { Label } from '@trussworks/react-uswds';
import { ToggleButton } from '../../../../../components/ToggleButton';
import styles from './addedittab.module.scss';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';

type TabEntry = { name: string | undefined; visible: boolean; order: number };

export const AddEditTab = () => {
    const { control } = useFormContext();

    return (
        <div className={styles.addEditTab}>
            <Controller
                control={control}
                name="name"
                rules={{
                    ...maxLengthRule(50)
                }}
                defaultValue={control._formValues.name}
                render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                    <>
                        <Label htmlFor="tabName" aria-required>
                            Tab name
                        </Label>
                        <Input
                            className="field-space"
                            type="text"
                            id="tab-name"
                            data-testid="tab-name"
                            name={name}
                            defaultValue={value}
                            onChange={onChange}
                            error={error?.message}
                        />
                    </>
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
