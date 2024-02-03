import { Heading } from 'components/heading';
import styles from '../question-form.module.scss';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import { ToggleButton } from '../../ToggleButton';
import { Input } from 'components/FormInputs/Input';
import { useEffect, useState } from 'react';
import { maxLengthRule } from 'validation/entry';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Option } from 'generated';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';

type Props = {
    editing?: boolean;
};
export const MessagingFields = ({ editing = false }: Props) => {
    const [codeSystems, setCodeSystems] = useState<Option[]>([]);
    const [hl7Options, setHl7Options] = useState<Option[]>([]);
    const form = useFormContext<CreateQuestionForm>();
    const watch = useWatch(form);

    useEffect(() => {
        useOptions('CODE_SYSTEM').then((response) => setCodeSystems(response.options));
        useOptions('NBS_HL7_DATA_TYPE').then((response) => setHl7Options(response.options));
    }, []);

    useEffect(() => {
        if (!watch.messagingInfo?.includedInMessage) {
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
    }, [watch.messagingInfo?.includedInMessage]);

    return (
        <>
            <Heading className={styles.heading} level={4}>
                Messaging
            </Heading>
            <div className={styles.fieldInfo}>These fields will not be displayed to your users</div>
            <label className={styles.toggleLabel}>
                Included in message? <span className={styles.mandatory}>*</span>
            </label>
            <Controller
                control={form.control}
                name="messagingInfo.includedInMessage"
                render={({ field: { onChange, name, value } }) => (
                    <div className={styles.toggleGroup}>
                        <div>Not included</div>
                        <ToggleButton checked={value} disabled={editing} name={name} id={name} onChange={onChange} />
                        <div>Included</div>
                    </div>
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.messageVariableId"
                rules={{
                    required: {
                        value: watch.messagingInfo?.includedInMessage ?? false,
                        message: 'Message ID is required'
                    },
                    ...maxLengthRule(50)
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Message ID"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={name}
                        id={name}
                        htmlFor={name}
                        disabled={!watch.messagingInfo?.includedInMessage}
                        required={watch.messagingInfo?.includedInMessage}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.labelInMessage"
                rules={{
                    required: {
                        value: watch.messagingInfo?.includedInMessage ?? false,
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
                        required={watch.messagingInfo?.includedInMessage}
                        disabled={!watch.messagingInfo?.includedInMessage}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.codeSystem"
                rules={{
                    required: {
                        value: watch.messagingInfo?.includedInMessage ?? false,
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
                        disabled={!watch.messagingInfo?.includedInMessage}
                        required={watch.messagingInfo?.includedInMessage}
                    />
                )}
            />
            <label className={styles.toggleLabel}>
                Required in message? <span className={styles.mandatory}>*</span>
            </label>
            <Controller
                control={form.control}
                name="messagingInfo.requiredInMessage"
                render={({ field: { onChange, value } }) => (
                    <div className={styles.toggleGroup}>
                        <div>Not required</div>
                        <ToggleButton
                            className="requiredInMessage"
                            checked={value}
                            name="includedInMessage"
                            disabled={!watch.messagingInfo?.includedInMessage}
                            onChange={onChange}
                        />
                        <div>Required</div>
                    </div>
                )}
            />
            <Controller
                control={form.control}
                name="messagingInfo.hl7DataType"
                rules={{
                    required: {
                        value: watch.messagingInfo?.includedInMessage ?? false,
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
                        disabled={!watch.messagingInfo?.includedInMessage}
                        required={watch.messagingInfo?.includedInMessage}
                        options={hl7Options}
                    />
                )}
            />
            <SelectInput
                label="HL7 Segment"
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
