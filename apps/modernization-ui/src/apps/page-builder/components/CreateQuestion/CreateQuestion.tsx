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
import { UserContext } from 'user';
import { useAlert } from 'alert';
import { ToggleButton } from '../ToggleButton';
import { text as textOption } from '../../constant/constant';
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
import { PageBuilder } from '../../pages/PageBuilder/PageBuilder';
import { Breadcrumb } from '../Breadcrumb/Breadcrumb';

namespace QuestionRequest {
    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN'
    }
}

const init = {
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
};
type CreateQuestionFormType = CreateNumericQuestionRequest &
    CreateCodedQuestionRequest &
    CreateDateQuestionRequest &
    CreateTextQuestionRequest &
    ReportingInfo &
    MessagingInfo &
    QuestionFormType;
export const CreateQuestion = ({ modalRef, question }: any) => {
    const questionForm = useForm<CreateQuestionFormType, any>({
        defaultValues: { ...init }
    });
    const { handleSubmit, reset, control, watch } = questionForm;
    // Fields
    const [questionData, setQuestionData] = useState({ valueSet: 0 });
    const { state } = useContext(UserContext);
    const { showAlert } = useAlert();
    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([]);
    const [groupOptions, setGroupOptions] = useState([]);
    const [selectedFieldType, setSelectedFieldType] = useState('');
    const [codeSystemOptionList, setCodeSystemOptionList] = useState([]);
    const authorization = `Bearer ${state.getToken()}`;

    useEffect(() => {
        if (question?.id) {
            const updatedQuestion = { ...question, ...question?.messagingInfo, ...question?.dataMartInfo };
            delete updatedQuestion.messagingInfo;
            delete updatedQuestion.dataMartInfo;
            setQuestionData(updatedQuestion);
        }
    }, [question]);
    const fetchFamilyOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization,
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
    const fetchGroupOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization,
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
            authorization,
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
                authorization,
                request: request
            }).then((response) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        } else if (selectedFieldType === 'DATE') {
            const dateRequest = { ...request, allowFutureDates: data.allowFutureDates === 'Yes' };
            QuestionControllerService.createDateQuestionUsingPost({
                authorization,
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
                authorization,
                request: numericRequest
            }).then((response) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        } else {
            const codeRequest = { ...request, valueSet: questionData.valueSet };
            QuestionControllerService.createCodedQuestionUsingPost({
                authorization,
                request: codeRequest
            }).then((response: any) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        }
    });
    const handleUpdateQuestion = (request: any) => {
        QuestionControllerService.updateQuestionUsingPut({
            authorization,
            id: question.id,
            request
        }).then((response: any) => {
            showAlert({ type: 'success', header: 'Updated', message: 'Question updated successfully' });
            resetInput();
            return response;
        });
    };
    const handleValidation = (unique = true) => {
        return unique ? /^[a-zA-Z0-9_]*$/ : /^[a-zA-Z0-9\s?!,-_]*$/;
    };

    const resetInput = () => {
        reset();
    };

    const fieldTypeTab = [
        { name: 'Value set', value: UpdateQuestionRequest.type.CODED },
        { name: 'Numerics entry', value: UpdateQuestionRequest.type.NUMERIC },
        { name: 'Text only', value: UpdateQuestionRequest.type.TEXT },
        { name: 'Date picker', value: UpdateQuestionRequest.type.DATE }
    ];

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
                modalBody={<ValuesetLibrary modalRef={modalRef} hideTabs types="recent" />}
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
                render={({ field: { onChange, name, onBlur }, fieldState: { error } }) => (
                    <>
                        <Label htmlFor={name}>
                            Tooltip <span className="mandatory-indicator">*</span>
                        </Label>
                        <Textarea
                            data-testid="tooltip"
                            rows={2}
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
                        options={textOption.map((option) => {
                            return {
                                name: option.label!,
                                value: option.value.toString()!
                            };
                        })}
                    />
                )}
            />
            <hr className="divider" />
        </>
    );
    const includedInMessage = watch('includedInMessage');
    const relatedUnits = watch('relatedUnits');
    const unitTypeValue = watch('unitType');
    const IsIncludedInMessage = !includedInMessage;
    const isDisableUnitType = relatedUnits !== 'Yes';

    return (
        <PageBuilder page="question-library">
            <Breadcrumb header="Add Question" className="margin-top-1em" />
            <div className="create-question">
                <div className="create-question__container">
                    <Form onSubmit={onSubmit}>
                        <div>
                            <div className="ds-u-text-align--center margin-bottom-2em">
                                <h3 className="header-title margin-bottom-2px" data-testid="header-title">
                                    {question?.id ? `Edit question` : `Let's create a new question`}
                                </h3>
                                <label className="fields-info">
                                    All fields with <span className="mandatory-indicator">*</span> are required
                                </label>
                            </div>
                            <h4>Basic information</h4>
                            <Controller
                                control={control}
                                name="codeSet"
                                render={({ field: { onChange, value } }) => (
                                    <div className="radio-group">
                                        <Radio
                                            id="reportableCondition_Y"
                                            name="codeSet"
                                            value={QuestionRequest.codeSet.LOCAL}
                                            label="LOCAL"
                                            onChange={(e: any) => onChange(e.target.value)}
                                            checked={value === 'LOCAL'}
                                        />
                                        <Radio
                                            id="reportableCondition_N"
                                            name="reportableCondition"
                                            value={QuestionRequest.codeSet.PHIN}
                                            label="PHIN"
                                            onChange={(e: any) => onChange(e.target.value)}
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
                                render={({ field: { onChange, name, onBlur }, fieldState: { error } }) => (
                                    <>
                                        <Label htmlFor={name}>Description</Label>
                                        <Textarea onChange={onChange} onBlur={onBlur} name={name} id={name} />
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
                                    control={control}
                                    isText={selectedFieldType === UpdateQuestionRequest.type.TEXT}
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
                                <CreateDateQuestion control={control} />
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
                            <h4>Messaging</h4>
                            <p className="fields-info">Messaging - these fields will not be displayed to your users</p>
                            <p className="fields-info">Included in message?</p>
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
                            <p className="fields-info">Required in message?</p>
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
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        label="HL7 data type"
                                        defaultValue={value}
                                        disabled={IsIncludedInMessage}
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
                            <Controller
                                control={control}
                                name="HL7Segment"
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        label="HL7 Segment"
                                        defaultValue={value}
                                        className="hl7-segment"
                                        disabled
                                        required
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
                        <h4>Administrative</h4>
                        <p className="fields-info">Administrative - these fields will not be displayed to your users</p>
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
                                    error={error?.message}
                                />
                            )}
                        />
                    </Form>
                </div>
                <div className="add-question-footer">
                    <Button className="submit-btn" type="submit">
                        {question?.id ? 'Save' : 'Create and add to page'}
                    </Button>
                    <ModalToggleButton className="cancel-btn" modalRef={modalRef} onClick={() => resetInput()}>
                        Cancel
                    </ModalToggleButton>
                </div>
            </div>
        </PageBuilder>
    );
};
