import React, { useContext, useEffect, useRef, useState } from 'react';
import './CreateQuestion.scss';
import {
    Form,
    ModalToggleButton,
    Radio,
    ButtonGroup,
    Button,
    ModalRef,
    Textarea,
    Label,
    ErrorMessage
} from '@trussworks/react-uswds';
import { ValueSetControllerService, QuestionControllerService, UpdateQuestionRequest } from '../../generated';
import { useAlert } from 'alert';
import { ToggleButton } from '../ToggleButton';
import { coded, dateOrNumeric, text as textOption } from '../../constant/constant';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { ValuesetLibrary } from '../../pages/ValuesetLibrary/ValuesetLibrary';
import { Controller, useForm } from 'react-hook-form';
import { Input } from '../../../../components/FormInputs/Input';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { maxLengthRule } from '../../../../validation/entry';
import { CreateDateQuestion } from './CreateDateQuestion';
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
import { authorization } from 'authorization';
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
    minValue: 0,
    maxValue: 50,
    displayControl: 0,
    relatedUnitsLiteral: 'ML',
    relatedUnitsValueSet: 2,
    includedInMessage: false
};

export type QuestionFormType = {
    defaultLabelInReport?: string;
    datamartColName?: string;
    unitType?: string;
    dateFormat?: number;
    displayType?: string;
    includedInMessage?: boolean;
    requiredInMessage?: boolean;
    messageVariableId?: string;
    messageLabel?: string;
    hl7DataType?: string;
    HL7Segment?: string;
    relatedUnits?: string;
    allowFutureDates?: string;
    codeSet: any;
};
type CreateQuestionFormType = CreateNumericQuestionRequest &
    CreateCodedQuestionRequest &
    CreateDateQuestionRequest &
    CreateTextQuestionRequest &
    ReportingInfo &
    MessagingInfo &
    QuestionFormType;

export type optionsType = { name: string; value: string };

export const CreateQuestion = ({ question, onCloseModal }: any) => {
    const questionForm = useForm<CreateQuestionFormType, any>({
        defaultValues: { ...init }
    });
    const { handleSubmit, reset, control, watch } = questionForm;
    // Fields
    const [questionData, setQuestionData] = useState({ valueSet: 0 });
    const { showAlert } = useAlert();
    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([]);
    const [groupOptions, setGroupOptions] = useState([]);
    const [selectedFieldType, setSelectedFieldType] = useState('');
    const [codeSystemOptionList, setCodeSystemOptionList] = useState([]);
    const [maskOptions, setMaskOptions] = useState<optionsType[]>([]);
    const { searchValueSet } = useContext(QuestionsContext);
    useEffect(() => {
        if (question?.id) {
            const updatedQuestion = { ...question, ...question?.messagingInfo, ...question?.dataMartInfo };
            delete updatedQuestion.messagingInfo;
            delete updatedQuestion.dataMartInfo;
            setQuestionData(updatedQuestion);
            if (!question?.id) {
                QuestionControllerService.getQuestionUsingGet({
                    authorization: authorization(),
                    id: Number(question?.id)
                }).then((response: any) => {
                    return response;
                });
            }
            questionForm.setValue(
                'defaultLabelInReport',
                updatedQuestion.defaultLabelInReport || updatedQuestion.reportLabel
            );
            setSelectedFieldType(updatedQuestion.dataType);
            questionForm.setValue('dataMartColumnName', updatedQuestion.dataMartColumnName);
            questionForm.setValue('defaultRdbTableName', updatedQuestion.defaultRdbTableName);
            questionForm.setValue('rdbColumnName', updatedQuestion.rdbColumnName);
            questionForm.setValue('codeSystem', updatedQuestion.codeSystem);
            questionForm.setValue('conceptCode', updatedQuestion.conceptCode);
            questionForm.setValue('conceptName', updatedQuestion.conceptName);
            questionForm.setValue('preferredConceptName', updatedQuestion.preferredConceptName);
            questionForm.setValue('datamartColName', updatedQuestion.datamartColName);
            questionForm.setValue('messageVariableId', updatedQuestion.messageVariableId);
            questionForm.setValue('messageLabel', updatedQuestion.labelInMessage);
            questionForm.setValue('unitType', updatedQuestion.unitType);
            questionForm.setValue('dateFormat', updatedQuestion.dateFormat);
            questionForm.setValue('displayType', updatedQuestion.displayType);
            questionForm.setValue('includedInMessage', updatedQuestion.includedInMessage);
            questionForm.setValue('requiredInMessage', updatedQuestion.requiredInMessage);
            questionForm.setValue('hl7DataType', updatedQuestion.hl7DataType);
            questionForm.setValue('HL7Segment', updatedQuestion.HL7Segment);
            questionForm.setValue('relatedUnits', updatedQuestion.relatedUnits);
            questionForm.setValue('allowFutureDates', updatedQuestion.allowFutureDates);
            questionForm.setValue('adminComments', updatedQuestion.adminComments);
            questionForm.setValue('codeSet', updatedQuestion.codeSet);
            questionForm.setValue('description', updatedQuestion.description);
            questionForm.setValue('defaultValue', updatedQuestion.defaultValue);
            questionForm.setValue('displayControl', updatedQuestion.displayControl);
            questionForm.setValue('label', updatedQuestion.name || updatedQuestion.label);
            questionForm.setValue('mask', updatedQuestion.mask);
            questionForm.setValue('subgroup', updatedQuestion.subgroup);
            questionForm.setValue('tooltip', updatedQuestion.tooltip);
            questionForm.setValue('uniqueName', updatedQuestion.uniqueName);
            questionForm.setValue('uniqueId', updatedQuestion.uniqueId);
            questionForm.setValue('minValue', updatedQuestion.minValue);
            questionForm.setValue('maxValue', updatedQuestion.maxValue);
            questionForm.setValue('relatedUnits', updatedQuestion.relatedUnits);
            questionForm.setValue('unitType', updatedQuestion.unitType);
            questionForm.setValue('fieldLength', updatedQuestion.fieldLength);
        }
    }, [question]);

    useEffect(() => {
        if (searchValueSet) questionForm.setValue('valueSet', searchValueSet);
    }, [searchValueSet]);

    const fetchFamilyOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: authorization(),
            codeSetNm: 'CONDITION_FAMILY'
        }).then((response: any) => {
            const data = response || [];
            const familyList: never[] = [];
            data.map((each: { localCode: never }) => {
                familyList.push(each.localCode);
            });
            setFamilyOptions(familyList);
        });
    };
    const fetchMask = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: authorization(),
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
            authorization: authorization(),
            codeSetNm: 'COINFECTION_GROUP'
        }).then((response: any) => {
            const data = response || [];
            const coinfectionGroupList: never[] = [];
            data.map((each: { localCode: never }) => {
                coinfectionGroupList.push(each.localCode);
            });
            setGroupOptions(coinfectionGroupList);
        });
    };
    const fetchCodeSystemOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: authorization(),
            codeSetNm: 'CODE_SYSTEM'
        }).then((response: any) => {
            const data = response || [];
            const codeSystemOptionList: any = [];
            data.map((each: { localCode: string; description: string }) => {
                codeSystemOptionList.push({ label: each.localCode, value: each.description });
            });
            setCodeSystemOptionList(codeSystemOptionList);
        });
    };

    useEffect(() => {
        fetchFamilyOptions();
        fetchGroupOptions();
        fetchCodeSystemOptions();
        fetchMask();
    }, []);

    const onSubmit = handleSubmit(async (data) => {
        if (question?.id) return handleUpdateQuestion(data);
        const request = {
            dataMartInfo: {
                defaultLabelInReport: data.defaultLabelInReport,
                dataMartColumnName: data.dataMartColumnName,
                defaultRdbTableName: data.defaultRdbTableName,
                rdbColumnName: data.rdbColumnName,
                reportLabel: data.defaultLabelInReport
            },
            messagingInfo: {
                codeSystem: data.codeSystem ?? '',
                conceptCode: data.conceptCode,
                conceptName: data.conceptName,
                preferredConceptName: data.preferredConceptName,
                messageVariableId: data.messageVariableId,
                labelInMessage: data.messageLabel,
                hl7DataType: data.hl7DataType
            },
            adminComments: data.adminComments!,
            codeSet: data.codeSet,
            description: data.description,
            defaultValue: data.defaultValue, // text
            displayControl: parseInt(String(data.displayControl!), 10),
            label: data.label,
            mask: data.mask,
            subgroup: data.subgroup,
            tooltip: data.tooltip,
            uniqueName: data.uniqueName,
            uniqueId: data.uniqueId
        };
        if (selectedFieldType === 'TEXT') {
            QuestionControllerService.createTextQuestionUsingPost({
                authorization: authorization(),
                request: request
            }).then((response) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        } else if (selectedFieldType === 'DATE') {
            const dateRequest = { ...request, allowFutureDates: data.allowFutureDates === 'Yes' };
            QuestionControllerService.createDateQuestionUsingPost({
                authorization: authorization(),
                request: dateRequest
            }).then((response) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        } else if (selectedFieldType === 'NUMERIC') {
            const numericRequest = {
                ...request,
                minValue: parseInt(String(data.minValue!), 10),
                fieldLength: parseInt(String(data.fieldLength!), 10),
                maxValue: parseInt(String(data.maxValue!), 10),
                relatedUnits: data?.relatedUnits,
                relatedUnitsLiteral: data?.relatedUnitsLiteral,
                relatedUnitsValueSet: data?.relatedUnitsValueSet,
                defaultValue: data.defaultValue
            };
            QuestionControllerService.createNumericQuestionUsingPost({
                authorization: authorization(),
                request: numericRequest
            }).then((response) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        } else {
            const codeRequest = { ...request, valueSet: questionData.valueSet };
            QuestionControllerService.createCodedQuestionUsingPost({
                authorization: authorization(),
                request: codeRequest
            }).then((response: any) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        }
        onCloseModal && onCloseModal();
    });
    const handleUpdateQuestion = (request: any) => {
        QuestionControllerService.updateQuestionUsingPut({
            authorization: authorization(),
            id: question.id,
            request
        }).then(() => {
            showAlert({ type: 'success', header: 'Updated', message: 'Question updated successfully' });
            resetInput();
            onCloseModal && onCloseModal();
        });
    };
    const handleValidation = (unique = true) => {
        return unique ? /^[a-zA-Z0-9_]*$/ : /^[a-zA-Z0-9\s?!,-_]*$/;
    };

    const resetInput = () => {
        reset();
        onCloseModal && onCloseModal();
    };

    const fieldTypeTab = [
        { name: 'Value set', value: UpdateQuestionRequest.type.CODED },
        { name: 'Numeric entry', value: UpdateQuestionRequest.type.NUMERIC },
        { name: 'Text only', value: UpdateQuestionRequest.type.TEXT },
        { name: 'Date picker', value: UpdateQuestionRequest.type.DATE }
    ];
    const getDisplayType = () => {
        switch (selectedFieldType) {
            case UpdateQuestionRequest.type.CODED:
                return coded;
            case UpdateQuestionRequest.type.TEXT:
                return textOption;
            case UpdateQuestionRequest.type.NUMERIC:
            case UpdateQuestionRequest.type.DATE:
                return dateOrNumeric;
            default:
                return coded;
        }
    };

    const valueSetmodalRef = useRef<ModalRef>(null);
    const renderValueSet = (
        <div className="">
            <label>Value set</label>
            <ModalToggleButton modalRef={valueSetmodalRef} className="width-full margin-top-1em" type="submit" outline>
                Search value set
            </ModalToggleButton>
            <ModalComponent
                isLarge
                modalRef={valueSetmodalRef}
                modalHeading={'Add value set'}
                modalBody={<ValuesetLibrary modalRef={valueSetmodalRef} hideTabs types="recent" />}
            />
            <br></br>
        </div>
    );
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
                name="displayType"
                render={({ field: { onChange, value } }) => (
                    <SelectInput
                        label="Display Type"
                        id="displayType"
                        data-testid="displayType"
                        required
                        defaultValue={value}
                        onChange={onChange}
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
    const relatedUnits = watch('relatedUnits');
    const unitTypeValue = watch('unitType');
    const IsIncludedInMessage = !includedInMessage;
    const isDisableUnitType = relatedUnits !== 'Yes';

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
                                        onChange={(e) => onChange(e.target.value)}
                                        checked={value === 'LOCAL'}
                                    />
                                    <Radio
                                        id="codeSet_PHIN"
                                        name="codeSet"
                                        value={QuestionRequest.codeSet.PHIN}
                                        label="PHIN"
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
                                required: { value: true, message: 'Unique ID required' },
                                pattern: { value: handleValidation(), message: 'Unique ID invalid' },
                                maxLength: 50
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    label="Unique ID"
                                    type="text"
                                    error={error?.message}
                                    required
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
                                required: { value: true, message: 'Unique name required' },
                                pattern: { value: handleValidation(false), message: 'Unique name invalid' },
                                maxLength: 50
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    className="field-space"
                                    defaultValue={value}
                                    label="Unique name"
                                    type="text"
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="subgroup"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    className="field-space"
                                    label="Subgroup"
                                    required
                                    onChange={onChange}
                                    options={familyOptions.map((option) => {
                                        return {
                                            name: option!,
                                            value: option!
                                        };
                                    })}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="description"
                            rules={maxLengthRule(50)}
                            render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                                <>
                                    <Label htmlFor={name}>Description</Label>
                                    <Textarea
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        defaultValue={value}
                                        name={name}
                                        id={name}
                                    />
                                    {error?.message && (
                                        <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>
                                    )}
                                </>
                            )}
                        />
                        <br></br>
                        <label>Field type</label>
                        <br></br>
                        <ButtonGroup type="segmented">
                            {fieldTypeTab.map((field, index) => (
                                <Button
                                    key={index}
                                    type="button"
                                    outline={field.value !== selectedFieldType}
                                    onClick={() => setSelectedFieldType(field.value)}>
                                    {field.name}
                                </Button>
                            ))}
                        </ButtonGroup>
                        <br></br>
                        {selectedFieldType === UpdateQuestionRequest.type.CODED && renderValueSet}
                        {(selectedFieldType === UpdateQuestionRequest.type.NUMERIC ||
                            selectedFieldType === UpdateQuestionRequest.type.TEXT) && (
                            <CreateTextQuestion
                                isText={selectedFieldType === UpdateQuestionRequest.type.TEXT}
                                control={control}
                                options={maskOptions}
                            />
                        )}
                        {selectedFieldType === UpdateQuestionRequest.type.NUMERIC && (
                            <CreateNumericQuestion
                                control={control!}
                                isDisableUnitType={isDisableUnitType}
                                unitType={unitTypeValue}
                            />
                        )}
                        {selectedFieldType === UpdateQuestionRequest.type.DATE && (
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
                                required: { value: true, message: 'Default label in report required' },
                                pattern: { value: /^\w*$/, message: 'Default label in report invalid' },
                                maxLength: 50
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    className="field-space"
                                    defaultValue={value}
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
                                required: { value: true, message: 'Default RDB table name required' },
                                pattern: { value: /^\w*$/, message: 'Default RDB table name invalid' },
                                maxLength: 50
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    className="field-space"
                                    defaultValue={value}
                                    label="Default RDB table name"
                                    type="text"
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="rdbColumnName"
                            rules={{
                                required: { value: true, message: 'RDB column name required' },
                                pattern: { value: handleValidation(), message: 'RDB column name invalid' },
                                maxLength: 50
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    label="RDB column name"
                                    type="text"
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="datamartColName"
                            rules={{
                                pattern: { value: /^\w*$/, message: 'Data mart column name invalid' },
                                maxLength: 50
                            }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <Input
                                    onChange={onChange}
                                    className="field-space"
                                    defaultValue={value}
                                    label="Data mart column name"
                                    type="text"
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
                                <>
                                    <ToggleButton
                                        className="margin-bottom-1em"
                                        checked={value}
                                        name="includedInMessage"
                                        onChange={onChange}
                                    />
                                </>
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
                            name="messageLabel"
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
                                    options={codeSystemOptionList.map(({ label }) => {
                                        return {
                                            name: label!,
                                            value: label!
                                        };
                                    })}
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
                                <>
                                    <ToggleButton
                                        className="requiredInMessage"
                                        checked={value}
                                        name="includedInMessage"
                                        onChange={onChange}
                                    />
                                </>
                            )}
                        />
                        <br></br>
                        <Controller
                            control={control}
                            name="hl7DataType"
                            rules={{
                                required: {
                                    value: !IsIncludedInMessage,
                                    message: 'HL7 data type required'
                                }
                            }}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    label="HL7 data type"
                                    defaultValue={value}
                                    disabled={IsIncludedInMessage}
                                    onChange={onChange}
                                    required
                                    options={groupOptions.map((option) => {
                                        return {
                                            name: option!,
                                            value: option!
                                        };
                                    })}
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
                                    options={groupOptions.map((option) => {
                                        return {
                                            name: option!,
                                            value: option!
                                        };
                                    })}
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
