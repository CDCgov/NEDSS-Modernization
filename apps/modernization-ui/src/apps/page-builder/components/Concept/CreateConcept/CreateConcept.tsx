import { AddConceptRequest } from 'apps/page-builder/generated';
import { useState, ReactNode } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { Button, FormGroup, Grid, Radio, ComboBox, ErrorMessage } from '@trussworks/react-uswds';
import { ValueSetControllerService, ValueSet } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { Heading } from 'components/heading';
import { Input } from 'components/FormInputs/Input';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { initialEntry } from 'apps/patient/add';
import { externalizeDateTime } from 'date';

interface CodeSystemOption {
    label: string;
    value: string;
}

const messagingInfo: any = {
    codeSystem: '',
    conceptCode: '',
    conceptName: '',
    preferredConceptName: ''
};

const init = {
    adminComments: undefined,
    code: undefined,
    displayName: undefined,
    effectiveFromTime: initialEntry().asOf,
    effectiveToTime: undefined,
    messagingInfo: messagingInfo,
    shortDisplayName: undefined,
    statusCode: AddConceptRequest.statusCode.A
};
type AlertMessage = { type: 'error' | 'success'; message: string | ReactNode; expiration: number | undefined };

type Props = {
    valueset: ValueSet;
    codeSystemOptionList: CodeSystemOption[];
    setShowForm: () => void;
    updateCallback: () => void;
    setAlertMessage: (message: AlertMessage) => void;
    closeForm: () => void;
};

export const CreateConcept = ({
    valueset,
    codeSystemOptionList,
    setShowForm,
    updateCallback,
    setAlertMessage,
    closeForm
}: Props) => {
    const [duration, setDuration] = useState(false);
    const conceptForm = useForm<AddConceptRequest>({
        mode: 'onBlur',
        defaultValues: { ...init }
    });
    const { control, handleSubmit, reset } = conceptForm;

    const resetForm = () => {
        setShowForm();
        reset();
        setShowForm();
    };

    const onSubmit = handleSubmit((data) => {
        handleCreateConcept(data);
    });

    const handleCreateConcept = async (data: AddConceptRequest) => {
        const effectiveFromTime = new Date(data.effectiveFromTime).toISOString();
        const request: AddConceptRequest = {
            code: data.messagingInfo.conceptCode,
            displayName: data.displayName,
            shortDisplayName: data.displayName,
            effectiveFromTime,
            effectiveToTime: duration ? externalizeDateTime(data.effectiveToTime) ?? undefined : undefined,
            statusCode: data.statusCode,
            messagingInfo: {
                codeSystem: data.messagingInfo.codeSystem,
                conceptCode: data.code,
                conceptName: data.messagingInfo.conceptName,
                preferredConceptName: data.messagingInfo.preferredConceptName
            }
        };
        await ValueSetControllerService.addConceptUsingPost({
            authorization: authorization(),
            codeSetNm: valueset!.valueSetCode!,
            request
        })
            .then((response: any) => {
                setAlertMessage({
                    type: 'success',
                    expiration: 3000,
                    message: (
                        <p>
                            You've successfully added <span>{data.messagingInfo.conceptName}</span>
                        </p>
                    )
                });
                updateCallback!();
                return response;
            })
            .catch((error: any) => {
                setAlertMessage({
                    type: 'error',
                    expiration: undefined,
                    message: <p>{error.body.message}</p>
                });
                closeForm();
            });
    };

    const handleValidation = (unique = true) => {
        return unique ? /^[a-zA-Z0-9_]*$/ : /^[a-zA-Z0-9\s?!,-_]*$/;
    };

    return (
        <div className="concept__form" onSubmit={onSubmit}>
            <p className="instruction">Please fill out the forms to add new value set concept.</p>
            <div className="concept__add-concept--local">
                <Heading level={3}>Local concept code</Heading>
                <div className="concept__fields">
                    <label className="fields-info">
                        All fields with <span className="mandatory-indicator">*</span> are required
                    </label>
                    <Controller
                        control={control}
                        name="displayName"
                        rules={{
                            required: { value: true, message: 'Display name is required' },
                            pattern: { value: handleValidation(false), message: 'Question label not valid' },
                            maxLength: 50
                        }}
                        render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                className="field-space"
                                type="text"
                                name={name}
                                id={name}
                                label="UI Display name"
                                defaultValue={value}
                                value={value}
                                onChange={onChange}
                                error={error?.message}
                                required
                            />
                        )}
                    />
                    <Grid row className="inline-field">
                        <Grid tablet={{ col: true }}>
                            <Controller
                                control={control}
                                name={'code'}
                                rules={{
                                    required: { value: true, message: 'Local code is required' },
                                    maxLength: 50
                                }}
                                render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                    <Input
                                        type="text"
                                        name={name}
                                        label={'Local code'}
                                        onChange={onChange}
                                        defaultValue={value}
                                        required
                                        error={error?.message}
                                    />
                                )}
                            />
                        </Grid>
                        <Grid tablet={{ col: true }}>
                            <Controller
                                control={control}
                                name={'messagingInfo.conceptCode'}
                                rules={{
                                    required: { value: true, message: 'Concept code is required' }
                                }}
                                render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                    <Input
                                        type="text"
                                        name={name}
                                        label={'Concept code'}
                                        onChange={onChange}
                                        defaultValue={value}
                                        required
                                        error={error?.message}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row className="concept__date">
                        <Grid col={6} className="effective-radio">
                            <Radio
                                type="radio"
                                name="duration"
                                value="always"
                                id="eAlways"
                                checked={!duration}
                                onChange={() => setDuration(false)}
                                label="Always Effective"
                            />
                            <Radio
                                id="eUntil"
                                name="duration"
                                value="until"
                                checked={duration}
                                onChange={() => setDuration(true)}
                                label="Effective Until"
                            />
                        </Grid>
                        <Grid col={5}>
                            <Controller
                                control={control}
                                name={'effectiveToTime'}
                                rules={{
                                    required: { value: duration ? true : false, message: 'End date required' }
                                }}
                                render={({ field: { onChange, value }, fieldState: { error } }) => (
                                    <FormGroup error={false}>
                                        <DatePickerInput
                                            id="effectivDate"
                                            name="effectivDate"
                                            defaultValue={!duration ? undefined : value}
                                            flexBox
                                            onChange={onChange}
                                            disableFutureDates={false}
                                            required={duration}
                                            errorMessage={error?.message}
                                        />
                                    </FormGroup>
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row className="concept__date">
                        <Controller
                            control={control}
                            name={'statusCode'}
                            render={({ field: { name, onChange, value } }) => (
                                <Grid col={6} className="effective-radio">
                                    <Radio
                                        name={name}
                                        value={AddConceptRequest.statusCode.A}
                                        id="cstatus"
                                        checked={value === AddConceptRequest.statusCode.A}
                                        onChange={onChange}
                                        label="Active"
                                    />
                                    <Radio
                                        id="incstatus"
                                        name={name}
                                        value={AddConceptRequest.statusCode.I}
                                        checked={value === AddConceptRequest.statusCode.I}
                                        onChange={onChange}
                                        label="Inactive"
                                    />
                                </Grid>
                            )}
                        />
                    </Grid>
                </div>
            </div>
            <div className="concept__add-concept--messaging">
                <Heading level={3}>Messaging concept code</Heading>
                <div className="concept__fields">
                    <label className="fields-info">
                        All fields with <span className="mandatory-indicator">*</span> are required
                    </label>
                    <Controller
                        control={control}
                        name={'messagingInfo.conceptCode'}
                        rules={{
                            required: { value: true, message: 'Concept code is required' }
                        }}
                        render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                            <Input
                                type="text"
                                name={name}
                                label={'Concept code'}
                                onChange={onChange}
                                defaultValue={value}
                                required
                                error={error?.message}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name={'messagingInfo.conceptName'}
                        rules={{
                            required: { value: true, message: 'Concept name is required' }
                        }}
                        render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                            <Input
                                type="text"
                                name={name}
                                label={'Concept name'}
                                onChange={onChange}
                                defaultValue={value}
                                required
                                error={error?.message}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name={'messagingInfo.preferredConceptName'}
                        rules={{
                            required: { value: true, message: 'Preferred concept name is required' }
                        }}
                        render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                            <Input
                                type="text"
                                name={name}
                                label={'Preferred concept name'}
                                onChange={onChange}
                                defaultValue={value}
                                required
                                error={error?.message}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="messagingInfo.codeSystem"
                        rules={{
                            required: { value: true, message: 'Code system name is required' }
                        }}
                        render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                            <>
                                <label>
                                    Code system name <span className="mandatory-indicator">*</span>
                                </label>
                                <ComboBox
                                    id={'codeSystem'}
                                    onChange={onChange}
                                    name={name}
                                    defaultValue={value}
                                    options={codeSystemOptionList.map((option: CodeSystemOption) => {
                                        return {
                                            label: option.label,
                                            value: option.value
                                        };
                                    })}
                                />
                                {error?.message ? <ErrorMessage>{error?.message}</ErrorMessage> : null}
                            </>
                        )}
                    />
                    <div className="concept__buttons">
                        <Button type="submit" outline onClick={resetForm}>
                            <span> Cancel</span>
                        </Button>
                        <Button type="submit" onClick={() => onSubmit()} disabled={!conceptForm.formState.isValid}>
                            <span>Add concept</span>
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
};
