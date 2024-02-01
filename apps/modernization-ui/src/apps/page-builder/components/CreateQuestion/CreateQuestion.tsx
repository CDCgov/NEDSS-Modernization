import React, { useContext, useEffect, useState } from 'react';
import './CreateQuestion.scss';
import { Form, Radio, ButtonGroup, Button, Textarea, Label, ErrorMessage } from '@trussworks/react-uswds';
import { ValueSetControllerService, QuestionControllerService, UpdateDateQuestionRequest } from '../../generated';
import { useAlert } from 'alert';
import { ToggleButton } from '../ToggleButton';
import { coded, dateOrNumeric, text as textOption } from '../../constant/constant';
import { Controller, useForm } from 'react-hook-form';
import { Input } from '../../../../components/FormInputs/Input';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { maxLengthRule } from '../../../../validation/entry';
import { CreateDateQuestion } from './CreateDateQuestion';
import { CreateCodedQuestion } from './CreateCodedQuestion';
import { CreateNumericQuestion } from './CreateNumericQuestion';
import { CreateTextQuestion } from './CreateTextQuestion';
import {
    CreateNumericQuestionRequest,
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateTextQuestionRequest,
    MessagingInfo,
    ReportingInfo
} from '../../generated';
import { authorization as fetchToken } from 'authorization';
import { QuestionsContext } from '../../context/QuestionsContext';

namespace QuestionRequest {
    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN'
    }
}

const init = {
    codeSet: 'LOCAL',
    label: '',
    description: '',
    includedInMessage: false
};

export type QuestionFormType = {
    defaultLabelInReport?: string;
    dataMartColumnName?: string;
    unitType?: string;
    groupNumber?: string;
    dateFormat?: number;
    includedInMessage?: boolean;
    requiredInMessage?: boolean;
    messageVariableId?: string;
    labelInMessage?: string;
    hl7DataType?: string;
    HL7Segment?: string;
    relatedUnits?: string;
    allowFutureDates?: string;
    dataType?: string;
    codeSet: any;
    mask: string | any;
};
type CreateQuestionFormType = CreateNumericQuestionRequest &
    CreateCodedQuestionRequest &
    CreateDateQuestionRequest &
    CreateTextQuestionRequest &
    ReportingInfo &
    MessagingInfo &
    QuestionFormType;

export type optionsType = { name: string; value: string };

export const CreateQuestion = ({ onAddQuestion, question, onCloseModal, addValueModalRef }: any) => {
    const questionForm = useForm<CreateQuestionFormType, any>({
        defaultValues: { ...init }
    });
    const { handleSubmit, reset, control, watch } = questionForm;
    const { showAlert } = useAlert();
    // DropDown Options
    const [subGroupOptions, setSubGroupOptions] = useState<optionsType[]>([]);
    const [groupOptions, setGroupOptions] = useState<optionsType[]>([]);
    const [selectedFieldType, setSelectedFieldType] = useState('');
    const [codeSystemOptionList, setCodeSystemOptionList] = useState<optionsType[]>([]);
    const [maskOptions, setMaskOptions] = useState<optionsType[]>([]);
    const { searchValueSet } = useContext(QuestionsContext);
    const authorization = fetchToken();

    useEffect(() => {
        fetchSubGroupOptions();
        fetchGroupOptions();
        fetchCodeSystemOptions();
        fetchMask();
    }, []);

    useEffect(() => {
        if (question?.id) {
            const updatedQuestion = { ...question, ...question?.messagingInfo, ...question?.dataMartInfo };
            delete updatedQuestion.messagingInfo;
            delete updatedQuestion.dataMartInfo;
            questionForm.setValue(
                'defaultLabelInReport',
                updatedQuestion.defaultLabelInReport || updatedQuestion.reportLabel
            );
            setSelectedFieldType(updatedQuestion.dataType);

            questionForm.setValue('codeSet', updatedQuestion.codeSet || updatedQuestion.standard);
            questionForm.setValue('uniqueName', updatedQuestion.uniqueName || updatedQuestion.name);
            questionForm.setValue('uniqueId', updatedQuestion.uniqueId || updatedQuestion.question);
            questionForm.setValue('description', updatedQuestion.description);
            questionForm.setValue('subgroup', updatedQuestion.subgroup || updatedQuestion.subGroup);
            questionForm.setValue('dataType', updatedQuestion.dataType);
            questionForm.setValue('valueSet', updatedQuestion.valueSet);

            questionForm.setValue('displayControl', updatedQuestion.displayControl || updatedQuestion.displayComponent);

            questionForm.setValue('dataMartColumnName', updatedQuestion.dataMartColumnName);
            questionForm.setValue('defaultRdbTableName', updatedQuestion.defaultRdbTableName);
            questionForm.setValue('rdbColumnName', updatedQuestion.rdbColumnName);
            questionForm.setValue('codeSystem', updatedQuestion.codeSystem);
            questionForm.setValue('conceptCode', updatedQuestion.conceptCode);
            questionForm.setValue('conceptName', updatedQuestion.conceptName);
            questionForm.setValue('preferredConceptName', updatedQuestion.preferredConceptName);
            questionForm.setValue('messageVariableId', updatedQuestion.messageVariableId);
            questionForm.setValue('labelInMessage', updatedQuestion.labelInMessage);
            questionForm.setValue('unitType', updatedQuestion.unitType);
            questionForm.setValue('dateFormat', updatedQuestion.dateFormat);
            questionForm.setValue('includedInMessage', updatedQuestion.includedInMessage);
            questionForm.setValue('requiredInMessage', updatedQuestion.requiredInMessage);
            questionForm.setValue('hl7DataType', updatedQuestion.hl7DataType);
            questionForm.setValue('HL7Segment', updatedQuestion.HL7Segment);
            questionForm.setValue('relatedUnits', updatedQuestion.relatedUnits);
            questionForm.setValue('allowFutureDates', updatedQuestion.allowFutureDates);
            questionForm.setValue('adminComments', updatedQuestion.adminComments);
            questionForm.setValue('defaultValue', updatedQuestion.defaultValue);
            questionForm.setValue('label', updatedQuestion.name || updatedQuestion.label);
            questionForm.setValue('mask', updatedQuestion.mask);
            questionForm.setValue('tooltip', updatedQuestion.tooltip);
            questionForm.setValue('minValue', updatedQuestion.minValue);
            questionForm.setValue('maxValue', updatedQuestion.maxValue);
            questionForm.setValue('relatedUnits', updatedQuestion.relatedUnits);
            questionForm.setValue('unitType', updatedQuestion.unitType);
            questionForm.setValue('fieldLength', updatedQuestion.fieldLength);
        }
    }, [question]);

    const valueSetName = searchValueSet?.valueSetName || searchValueSet?.valueSetNm || watch('valueSet') || '';
    const valueSetCode = searchValueSet?.valueSetCode || valueSetName;
    useEffect(() => {
        if (searchValueSet) questionForm.setValue('valueSet', searchValueSet.codeSetGroupId);
    }, [searchValueSet]);

    const fetchSubGroupOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization,
            codeSetNm: 'NBS_QUES_SUBGROUP'
        }).then((response: any) => {
            const data = response || [];
            const subgroupList: optionsType[] = [];
            data.map((each: { display: string; conceptCode: string }) => {
                subgroupList.push({ name: each.display, value: each.conceptCode });
            });
            setSubGroupOptions(subgroupList);
        });
    };
    const fetchMask = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization,
            codeSetNm: 'NBS_MASK_TYPE'
        }).then((response: any) => {
            const data = response || [];
            const mask: optionsType[] = [];
            data.map((each: { display: string; conceptCode: string }) => {
                mask.push({ name: each.display, value: each.conceptCode });
            });
            setMaskOptions(mask);
        });
    };

    const fetchGroupOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization,
            codeSetNm: 'COINFECTION_GROUP'
        }).then((response: any) => {
            const data = response || [];
            const coinfectionGroupList: optionsType[] = [];
            data.map((each: { display: string; conceptCode: string }) => {
                coinfectionGroupList.push({ name: each.display, value: each.conceptCode });
            });
            setGroupOptions(coinfectionGroupList);
        });
    };
    const fetchCodeSystemOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization,
            codeSetNm: 'CODE_SYSTEM'
        }).then((response: any) => {
            const data = response || [];
            const codeSystemOptionList: optionsType[] = [];
            data.map((each: { display: string; conceptCode: string }) => {
                codeSystemOptionList.push({ name: each.display, value: each.conceptCode });
            });
            setCodeSystemOptionList(codeSystemOptionList);
        });
    };

    const onSubmit = handleSubmit(async (data) => {
        const { id } = question ?? {};
        const request = {
            dataMartInfo: {
                dataMartColumnName: data.dataMartColumnName,
                defaultRdbTableName: data.defaultRdbTableName,
                rdbColumnName: data.rdbColumnName,
                reportLabel: data.defaultLabelInReport
            },
            messagingInfo: {
                codeSystem: data.codeSystem ?? '',
                hl7DataType: data.hl7DataType,
                labelInMessage: data.labelInMessage,
                includedInMessage: data.includedInMessage,
                requiredInMessage: data.requiredInMessage,
                messageVariableId: data.messageVariableId
            },
            adminComments: data.adminComments!,
            codeSet: data.codeSet,
            description: data.description,
            defaultValue: data.defaultValue, // text
            displayControl: data.displayControl!,
            label: data.label,
            mask: data.mask,
            subgroup: data.subgroup,
            tooltip: data.tooltip,
            uniqueName: data.uniqueName,
            uniqueId: data.uniqueId
        };

        if (selectedFieldType === 'TEXT') {
            const payload = { authorization, request };
            if (id)
                return QuestionControllerService.updateTextQuestionUsingPut({ ...payload, id }).then(() => {
                    handleResponse(0);
                });
            QuestionControllerService.createTextQuestionUsingPost(payload).then((response) => {
                handleResponse(response.id!, 'created');
            });
        } else if (selectedFieldType === 'DATE') {
            const payload = {
                authorization,
                request: { ...request, allowFutureDates: data.allowFutureDates === 'Yes' }
            };
            if (id) return QuestionControllerService.updateDateQuestionUsingPut({ ...payload, id }).then(() => {});
            QuestionControllerService.createDateQuestionUsingPost(payload).then((response) => {
                handleResponse(response.id!, 'created');
            });
        } else if (selectedFieldType === 'NUMERIC') {
            const payload = {
                authorization,
                request: {
                    ...request,
                    minValue: parseInt(String(data.minValue!), 10),
                    fieldLength: parseInt(String(data.fieldLength!), 10),
                    maxValue: parseInt(String(data.maxValue!), 10),
                    relatedUnits: data?.relatedUnits,
                    relatedUnitsLiteral: data?.relatedUnitsLiteral,
                    relatedUnitsValueSet: data?.relatedUnitsValueSet,
                    defaultValue: data.defaultValue
                }
            };
            if (id)
                return QuestionControllerService.updateNumericQuestionUsingPut({ ...payload, id }).then(() => {
                    handleResponse(0);
                });
            QuestionControllerService.createNumericQuestionUsingPost(payload).then((response) => {
                handleResponse(response.id!, 'created');
            });
        } else {
            const payload = {
                authorization,
                request: { ...request, valueSet: data.valueSet }
            };
            if (id)
                return QuestionControllerService.updateCodedQuestionUsingPut({ ...payload, id }).then(() => {
                    handleResponse(0);
                });
            QuestionControllerService.createCodedQuestionUsingPost(payload).then((response) => {
                handleResponse(response.id!, 'created');
            });
        }
    });
    const handleResponse = (id: number, msg = 'updated') => {
        showAlert({ type: 'success', header: 'Created', message: `Question ${msg} successfully` });
        resetInput();
        if (id) onAddQuestion?.(id);
    };
    const handleValidation = (unique = true) => {
        return unique ? /^[a-zA-Z0-9_]*$/ : /^[a-zA-Z0-9\s?!,-_]*$/;
    };
    const startWithNonInteger = /^\D[a-zA-Z0-9_]*$/;

    const resetInput = () => {
        reset();
        onCloseModal && onCloseModal();
    };

    const fieldTypeTab = [
        { name: 'Value set', value: UpdateDateQuestionRequest.type.CODED },
        { name: 'Numeric entry', value: UpdateDateQuestionRequest.type.NUMERIC },
        { name: 'Text only', value: UpdateDateQuestionRequest.type.TEXT },
        { name: 'Date picker', value: UpdateDateQuestionRequest.type.DATE }
    ];
    const getDisplayType = () => {
        switch (selectedFieldType) {
            case UpdateDateQuestionRequest.type.CODED:
                return coded;
            case UpdateDateQuestionRequest.type.TEXT:
                return textOption;
            case UpdateDateQuestionRequest.type.NUMERIC:
                return dateOrNumeric;
            case UpdateDateQuestionRequest.type.DATE:
                return dateOrNumeric;
            default:
                return coded;
        }
    };
    const getDefaultSelection = () => {
        switch (selectedFieldType) {
            case UpdateDateQuestionRequest.type.TEXT:
                questionForm.setValue('mask', 'TXT');
                break;
            case UpdateDateQuestionRequest.type.NUMERIC:
                questionForm.setValue('mask', 'NUM');
                break;
            case UpdateDateQuestionRequest.type.DATE:
                questionForm.setValue('mask', 'DATE');
                break;
            default:
                questionForm.setValue('mask', '');
        }
    };

    useEffect(() => {
        if (!editDisabledFields) getDefaultSelection();
    }, [selectedFieldType]);

    const renderUserInterface = (
        <>
            <h4>User Interface</h4>
            <Controller
                control={control}
                name="label"
                rules={{
                    required: { value: true, message: 'Question label required' },
                    pattern: { value: handleValidation(false), message: 'Question label not valid' },
                    maxLength: 50
                }}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        id={name}
                        name={name}
                        type="text"
                        data-testid="questionLabel"
                        className="field-space"
                        label="Question label"
                        defaultValue={value}
                        error={error?.message}
                        onChange={onChange}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="tooltip"
                rules={maxLengthRule(2000)}
                render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                    <>
                        <Label htmlFor={name}>
                            Tooltip <span className="mandatory-indicator">*</span>
                        </Label>
                        <Textarea
                            data-testid="tooltip"
                            rows={2}
                            defaultValue={value}
                            className="field-space"
                            onChange={onChange}
                            onBlur={onBlur}
                            name={name}
                            id={name}
                        />
                        {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                    </>
                )}
            />
            <Controller
                control={control}
                name="displayControl"
                rules={{ required: { value: true, message: 'Display Type required' } }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Display Type"
                        data-testid="displayType"
                        required
                        defaultValue={value}
                        onChange={onChange}
                        error={error?.message}
                        options={getDisplayType().map((option) => {
                            return {
                                name: option.label!,
                                value: option.value.toString()!
                            };
                        })}
                    />
                )}
            />
            <hr className="divider data-mart" />
        </>
    );
    const includedInMessage = watch('includedInMessage');
    const IsIncludedInMessage = !includedInMessage;
    const editDisabledFields = question?.id !== undefined;
    const readOnlyControl = watch('displayControl') == 1026;

    return (
        <div className="create-question">
            <div className="create-question__container">
                <Form onSubmit={onSubmit}>
                    <div>
                        <div className="text-align-center margin-bottom-2em">
                            <h3 className="header-title margin-2px" data-testid="header-title">
                                {question?.id ? `Edit question` : `Let's create a new question`}
                            </h3>
                            <label className="sub-header-info">
                                All fields with <span className="mandatory-indicator">*</span> are required
                            </label>
                        </div>
                        <h4>Basic information</h4>
                        <Controller
                            control={control}
                            name="codeSet"
                            defaultValue={QuestionRequest.codeSet.LOCAL}
                            render={({ field: { onChange, value } }) => (
                                <div className="radio-group">
                                    <Radio
                                        id="codeSet_LOCAL"
                                        name="codeSet"
                                        value={QuestionRequest.codeSet.LOCAL}
                                        label="LOCAL"
                                        disabled={editDisabledFields}
                                        onChange={(e) => onChange(e.target.value)}
                                        checked={value === 'LOCAL'}
                                    />
                                    <Radio
                                        id="codeSet_PHIN"
                                        name="codeSet"
                                        value={QuestionRequest.codeSet.PHIN}
                                        label="PHIN"
                                        disabled={editDisabledFields}
                                        onChange={(e) => onChange(e.target.value)}
                                        checked={value === 'PHIN'}
                                    />
                                </div>
                            )}
                        />
                        <Controller
                            control={control}
                            name="uniqueId"
                            rules={{
                                pattern: { value: handleValidation(), message: 'Unique ID invalid' },
                                ...maxLengthRule(50)
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    label="Unique ID"
                                    type="text"
                                    disabled={editDisabledFields}
                                    error={error?.message}
                                />
                            )}
                        />
                        <p className="fields-help-text margin-bottom-1em margin-top-0">
                            If you decide not to provide a UNIQUE ID the system will generate one for you.
                        </p>
                        <Controller
                            control={control}
                            name="uniqueName"
                            rules={{
                                required: { value: !editDisabledFields, message: 'Unique name required' },
                                pattern: { value: handleValidation(false), message: 'Unique name invalid' },
                                ...maxLengthRule(50)
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    className="field-space"
                                    defaultValue={value}
                                    label="Unique name"
                                    type="text"
                                    disabled={editDisabledFields}
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="subgroup"
                            rules={{ required: { value: true, message: 'Subgroup required' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    className="field-space"
                                    disabled={editDisabledFields}
                                    label="Subgroup"
                                    required
                                    onChange={onChange}
                                    error={error?.message}
                                    options={subGroupOptions}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="description"
                            rules={maxLengthRule(2000)}
                            render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                                <>
                                    <Label htmlFor={name}>Description</Label>
                                    <Textarea
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        defaultValue={value}
                                        name={name}
                                        disabled={editDisabledFields}
                                        id={name}
                                    />
                                    {error?.message && (
                                        <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>
                                    )}
                                </>
                            )}
                        />
                        <br></br>
                        <Controller
                            control={control}
                            name="dataType"
                            rules={{ required: { value: true, message: 'Field type required' } }}
                            render={({ field: { onChange, name }, fieldState: { error } }) => (
                                <>
                                    <Label htmlFor={name}>Field type</Label>
                                    <ButtonGroup type="segmented">
                                        {fieldTypeTab.map((field, index) => (
                                            <Button
                                                key={index}
                                                type="button"
                                                outline={field.value !== selectedFieldType}
                                                disabled={editDisabledFields}
                                                onClick={() => {
                                                    setSelectedFieldType(field.value);
                                                    onChange(field.value);
                                                }}>
                                                {field.name}
                                            </Button>
                                        ))}
                                    </ButtonGroup>
                                    {error?.message && (
                                        <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>
                                    )}
                                </>
                            )}
                        />
                        <br></br>
                        {selectedFieldType === UpdateDateQuestionRequest.type.CODED && (
                            <CreateCodedQuestion
                                control={control}
                                addValueModalRef={addValueModalRef}
                                valueSetName={valueSetName.toString()}
                                valueSetCode={valueSetCode.toString()}
                            />
                        )}
                        {(selectedFieldType === UpdateDateQuestionRequest.type.NUMERIC ||
                            selectedFieldType === UpdateDateQuestionRequest.type.TEXT) && (
                            <CreateTextQuestion
                                isText={selectedFieldType === UpdateDateQuestionRequest.type.TEXT}
                                control={control}
                                options={maskOptions}
                            />
                        )}
                        {selectedFieldType === UpdateDateQuestionRequest.type.NUMERIC && (
                            <CreateNumericQuestion control={control} />
                        )}
                        {selectedFieldType === UpdateDateQuestionRequest.type.DATE && (
                            <CreateDateQuestion control={control} options={maskOptions} />
                        )}
                        <hr className="divider" />
                        {renderUserInterface}
                        <h4>Data mart</h4>
                        <p className="fields-info">Data mart - these fields will not be displayed to your users</p>
                        <Controller
                            control={control}
                            name="defaultLabelInReport"
                            rules={{
                                required: { value: !readOnlyControl, message: 'Default label in report required' },
                                pattern: { value: /^\w*$/, message: 'Default label in report invalid' },
                                ...maxLengthRule(50)
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    className="field-space"
                                    defaultValue={value}
                                    disabled={readOnlyControl}
                                    label="Default label in report"
                                    type="text"
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="defaultRdbTableName"
                            rules={{
                                pattern: { value: /^\w*$/, message: 'Default RDB table name invalid' },
                                ...maxLengthRule(50)
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    className="field-space"
                                    defaultValue={value}
                                    disabled={readOnlyControl}
                                    label="Default RDB table name"
                                    type="text"
                                    error={error?.message}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="rdbColumnName"
                            rules={{
                                required: { value: !editDisabledFields, message: 'RDB column name required' },
                                pattern: { value: startWithNonInteger, message: 'RDB column name invalid' },
                                ...maxLengthRule(20)
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    label="RDB column name"
                                    onBlur={() => {
                                        questionForm.setValue('dataMartColumnName', value);
                                    }}
                                    disabled={editDisabledFields || readOnlyControl}
                                    type="text"
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="dataMartColumnName"
                            rules={{
                                pattern: { value: startWithNonInteger, message: 'Data mart column name invalid' },
                                ...maxLengthRule(20)
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    className="field-space"
                                    defaultValue={value}
                                    label="Data mart column name"
                                    type="text"
                                    disabled={readOnlyControl}
                                    error={error?.message}
                                />
                            )}
                        />
                        <hr className="divider" />
                        <h4>Messaging</h4>
                        <p className="fields-info">Messaging - these fields will not be displayed to your users</p>
                        <p className="fields-info">
                            Included in message? <span className="mandatory-indicator">*</span>
                        </p>
                        <Controller
                            control={control}
                            name="includedInMessage"
                            render={({ field: { onChange, value } }) => (
                                <div className="create-question-toggle-group">
                                    <div>Not required</div>
                                    <ToggleButton
                                        checked={value}
                                        disabled={readOnlyControl}
                                        name="includedInMessage"
                                        onChange={onChange}
                                    />
                                    <div>Required</div>
                                </div>
                            )}
                        />
                        <Controller
                            control={control}
                            name="messageVariableId"
                            rules={{
                                required: { value: !IsIncludedInMessage, message: 'Message ID required' },
                                pattern: { value: handleValidation(false), message: 'Message ID invalid' }
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    disabled={IsIncludedInMessage}
                                    label="Message ID"
                                    type="text"
                                    required
                                    error={error?.message}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="labelInMessage"
                            rules={{
                                required: { value: !IsIncludedInMessage, message: 'Message label required' },
                                pattern: { value: handleValidation(false), message: 'Message label invalid' }
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    disabled={IsIncludedInMessage}
                                    defaultValue={value!}
                                    label="Message label"
                                    type="text"
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="codeSystem"
                            rules={{
                                required: {
                                    value: !IsIncludedInMessage,
                                    message: 'Code system name required'
                                }
                            }}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    label="Code system name"
                                    defaultValue={value}
                                    onChange={onChange}
                                    disabled={IsIncludedInMessage}
                                    required
                                    options={codeSystemOptionList}
                                />
                            )}
                        />
                        <p className="fields-info">
                            Required in message? <span className="mandatory-indicator">*</span>
                        </p>
                        <Controller
                            control={control}
                            name="requiredInMessage"
                            render={({ field: { onChange, value } }) => (
                                <div className="create-question-toggle-group">
                                    <div>Not required</div>
                                    <ToggleButton
                                        className="requiredInMessage"
                                        checked={value}
                                        name="includedInMessage"
                                        disabled={IsIncludedInMessage}
                                        onChange={onChange}
                                    />
                                    <div>Required</div>
                                </div>
                            )}
                        />
                        <br></br>
                        <Controller
                            control={control}
                            name="hl7DataType"
                            rules={{ required: { value: !IsIncludedInMessage, message: 'HL7 data type required' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    label="HL7 data type"
                                    defaultValue={value}
                                    name="hl7DataType"
                                    disabled={IsIncludedInMessage}
                                    onChange={onChange}
                                    error={error?.message}
                                    required
                                    options={groupOptions}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="HL7Segment"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    label="HL7 Segment"
                                    defaultValue={value}
                                    className="hl7-segment"
                                    disabled
                                    onChange={onChange}
                                    options={groupOptions}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="groupNumber"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    disabled
                                    className="field-space"
                                    label="Group Number (Order Group ID)"
                                    type="text"
                                />
                            )}
                        />
                    </div>
                    <hr className="divider" />
                    <h4 className="margin-bottom-0">Administrative</h4>
                    <p className="fields-info margin-bottom-2em">
                        Administrative - these fields will not be displayed to your users
                    </p>
                    <Controller
                        control={control}
                        name="adminComments"
                        rules={{ maxLength: 2000 }}
                        render={({ field: { onChange, value }, fieldState: { error } }) => (
                            <Input
                                onChange={onChange}
                                defaultValue={value}
                                className="field-space"
                                label="Administrative comments"
                                type="text"
                                multiline
                                error={error?.message}
                            />
                        )}
                    />
                    <div className="add-question-footer">
                        <Button type="submit" className="submit-btn">
                            {question?.id ? 'Save' : 'Create and add to page'}
                        </Button>
                        <Button className="cancel-btn" onClick={resetInput} type={'button'}>
                            Cancel
                        </Button>
                    </div>
                </Form>
            </div>
        </div>
    );
};
