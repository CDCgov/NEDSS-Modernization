import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { ToggleButton } from '../../ToggleButton';
import { CreateQuestionForm } from '../QuestionForm';
import styles from '../question-form.module.scss';

export const MessagingFields = () => {
    const { options: codeSystems } = useOptions('CODE_SYSTEM');
    const { options: hl7Options } = useOptions('NBS_HL7_DATA_TYPE');
    const form = useFormContext<CreateQuestionForm>();
    const includedInMessage = useWatch({ control: form.control, name: 'messagingInfo.includedInMessage', exact: true });

    useEffect(() => {
        if (!includedInMessage) {
            form.reset({
                ...form.getValues(),
                messagingInfo: {
                    messageVariableId: undefined,
                    labelInMessage: undefined,
                    requiredInMessage: undefined,
                    hl7DataType: undefined
                }
            });
        }
    }, [includedInMessage]);

    return (
        <>
            <h4>Messaging</h4>
            <label htmlFor="messagingInfo.includedInMessage" className={styles.toggleLabel}>
                Included in message? <span className={styles.mandatory}>*</span>
            </label>
            <Controller
                control={form.control}
                name="messagingInfo.includedInMessage"
                render={({ field: { onChange, name, value } }) => (
                    <div className={styles.toggleGroup}>
                        <div>Not included</div>
                        <ToggleButton checked={value === true} name={name} id={name} onChange={onChange} />
                        <div>Included</div>
                    </div>
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.messageVariableId"
                rules={{
                    required: {
                        value: includedInMessage ?? false,
                        message: 'Message variable ID is required'
                    },
                    ...maxLengthRule(50)
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Message variable ID"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        htmlFor={name}
                        disabled={!includedInMessage}
                        required={includedInMessage}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.labelInMessage"
                rules={{
                    required: {
                        value: includedInMessage ?? false,
                        message: 'Message label is required'
                    },
                    ...maxLengthRule(100)
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Message label"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        htmlFor={name}
                        required={includedInMessage}
                        disabled={!includedInMessage}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.codeSystem"
                rules={{
                    required: {
                        value: includedInMessage ?? false,
                        message: 'Code system name is required'
                    }
                }}
                render={({ field: { onChange, onBlur, name, value } }) => (
                    <SelectInput
                        label="Code system name"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        id={name}
                        htmlFor={name}
                        options={codeSystems}
                        disabled={!includedInMessage}
                        required={includedInMessage}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.requiredInMessage"
                render={({ field: { onChange, value } }) => (
                    <>
                        <label htmlFor="includedInMessage" className={styles.toggleLabel}>
                            Required in message? <span className={styles.mandatory}>*</span>
                        </label>
                        <div className={styles.toggleGroup}>
                            <div>Not required</div>
                            <ToggleButton
                                className="requiredInMessage"
                                checked={value === true}
                                name="includedInMessage"
                                id="includedInMessage"
                                disabled={!includedInMessage}
                                onChange={onChange}
                            />
                            <div>Required</div>
                        </div>
                    </>
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.hl7DataType"
                rules={{
                    required: {
                        value: includedInMessage ?? false,
                        message: 'HL7 data type required'
                    }
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="HL7 data type"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                        disabled={!includedInMessage}
                        required={includedInMessage}
                        options={hl7Options}
                    />
                )}
            />
            <SelectInput
                label="HL7 segment"
                value={'OBX-3.0'}
                disabled
                options={[{ name: 'OBX-3.0', value: 'OBX-3.0' }]}
            />
            <Input
                label="Group number (Order group ID)"
                defaultValue={'2'}
                type="text"
                name="Group number"
                id="Group number"
                htmlFor="Group number"
                disabled={true}
            />
        </>
    );
};
