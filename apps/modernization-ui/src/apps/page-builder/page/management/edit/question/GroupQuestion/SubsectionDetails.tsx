import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { Radio } from '@trussworks/react-uswds';
import styles from './subsection-details.module.scss';
import { GroupRequest } from 'apps/page-builder/hooks/api/useGroupSubsection';
import { useEffect, useState } from 'react';

export const SubsectionDetails = () => {
    const { control, setValue } = useFormContext<GroupRequest>();
    const [visibleToggle, setVisibleToggle] = useState(control._formValues.visible ? 'true' : 'false');

    useEffect(() => {
        setValue('visible', visibleToggle === 'true' ? true : false);
    }, [visibleToggle]);

    return (
        <div className={styles.details}>
            <div className={styles.header}>
                <h4>Subsection details</h4>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>
                    <p>Subsection name</p>
                </div>
                <div className={styles.field}>
                    <Controller
                        control={control}
                        name="name"
                        rules={{
                            required: { value: true, message: 'Subsection name required.' }
                        }}
                        render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                type="text"
                                name={name}
                                data-testid="editSubsectionModalSubsectionName"
                                defaultValue={value}
                                onChange={onChange}
                                required
                                error={error?.message}
                            />
                        )}
                    />
                </div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>
                    <p>Visible</p>
                </div>
                <div className={styles.field}>
                    <Controller
                        control={control}
                        name="visible"
                        render={({ field: { name } }) => (
                            <div className={styles.radio}>
                                <Radio
                                    name={name}
                                    value="true"
                                    id="visible"
                                    checked={control._formValues.visible}
                                    onChange={() => setVisibleToggle('true')}
                                    label="Yes"
                                />
                                <Radio
                                    id="notvisible"
                                    name={name}
                                    value={false.toString()}
                                    checked={!control._formValues.visible}
                                    onChange={() => setVisibleToggle('false')}
                                    label="No"
                                />
                            </div>
                        )}
                    />
                </div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>
                    <p>Block name</p>
                </div>
                <div className={styles.field}>
                    <Controller
                        control={control}
                        name="blockName"
                        rules={{
                            required: { value: true, message: 'Block name required.' },
                            pattern: { value: /^\w*$/, message: 'Valid characters are A-Z, 0-9, or _' }
                        }}
                        render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                type="text"
                                name={name}
                                data-testid="editSubsectionModalBlockName"
                                defaultValue={value}
                                onChange={onChange}
                                required
                                error={error?.message}
                                className="text-uppercase"
                            />
                        )}
                    />
                </div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>
                    <p>Data mart repeat number</p>
                </div>
                <div className={styles.field}>
                    <Controller
                        control={control}
                        name="repeatingNbr"
                        rules={{
                            required: { value: true, message: 'Repeat number required.' },
                            max: { value: 5, message: 'Must be between 0 and 5' },
                            min: { value: 0, message: 'Must be between 0 and 5' }
                        }}
                        render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                            <Input
                                type="number"
                                name={name}
                                data-testid="editSubsectionModalDataMart"
                                defaultValue={value?.toString()}
                                onChange={onChange}
                                onBlur={onBlur}
                                required
                                min={0}
                                max={5}
                                error={error?.message}
                            />
                        )}
                    />
                    <p>
                        Data mart repeat number is used to indicate the number of times the repeating block data should
                        be pivoted in the creation of dynamic data marts in the reporting database. Valid values include
                        0-5. A value of 0 indicates that only columns with ALL data for a block element will appear in
                        the data mart.
                    </p>
                </div>
            </div>
        </div>
    );
};
