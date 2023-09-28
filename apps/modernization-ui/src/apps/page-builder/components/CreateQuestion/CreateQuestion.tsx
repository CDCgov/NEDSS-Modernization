import React, { useContext, useEffect, useState } from 'react';
import './CreateQuestion.scss';
import ReactSelect, { components } from 'react-select';
import { ModalToggleButton, Radio, TextInput, Dropdown } from '@trussworks/react-uswds';
import { ValueSetControllerService, QuestionControllerService, UpdateQuestionRequest } from '../../generated';
import { UserContext } from 'user';
import { useAlert } from 'alert';
import { ToggleButton } from '../ToggleButton';

import { fieldType } from '../../constant/constant';

export const CreateQuestion = ({ modalRef, question }: any) => {
    const init = {
        label: '',
        description: '',
        type: UpdateQuestionRequest.type.TEXT,
        subgroup: '',
        uniqueId: '',
        uniqueName: '',
        defaultLabelInReport: '',
        defaultRdbTableName: '',
        rdbColumnName: '',
        includedInMessage: false,
        messageVariableId: '',
        labelInMessage: '',
        codeSystem: '',
        hl7DataType: ',',
        requiredInMessage: false,
        dataMartColumnName: '',
        adminComments: '',
        codeSet: 'PHIN',
        reportLabel: '',
        defaultValue: 'test@gmai.com',
        fieldLength: '50',
        mask: 'TXT',
        fieldSize: '50',
        tooltip: '',
        displayControl: 0,
        allowFutureDates: true
    };
    const validation = {
        label: { required: true, error: false, fb: false },
        description: { required: false, error: false, fb: false },
        subgroup: { required: true, error: false, fb: false },
        uniqueId: { required: true, error: false, fb: false },
        uniqueName: { required: true, error: false, fb: false },
        defaultLabelInReport: { required: true, error: false, fb: false },
        defaultRdbTableName: { required: true, error: false, fb: false },
        rdbColumnName: { required: true, error: false, fb: false },
        dataMartColumnName: { required: false, error: false, fb: false },
        messageVariableId: { required: true, error: false, fb: false },
        labelInMessage: { required: true, error: false, fb: false },
        codeSystem: { required: true, error: false, fb: false },
        hl7DataType: { required: true, error: false, fb: false }
    };

    // Fields
    const [questionData, setQuestionData] = useState(init);
    const { state } = useContext(UserContext);
    const { showAlert } = useAlert();
    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([]);
    const [groupOptions, setGroupOptions] = useState([]);
    const [isValid, setIsValid] = useState(validation);
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

    const buildOptions = (optionsToBuild: any[]) =>
        optionsToBuild.map((opt: string) => (
            <option value={opt} key={opt}>
                {opt}
            </option>
        ));
    const buildCodeOptions = (optionsToBuild: any[]) =>
        optionsToBuild.map((opt: any) => (
            <option value={opt.value} key={opt.value}>
                {opt.label}
            </option>
        ));
    const handleSubmit = () => {
        if (question?.id) return handleUpdateQuestion();
        const request = {
            dataMartInfo: {
                defaultLabelInReport: 'Test',
                defaultRdbTableName: questionData.defaultRdbTableName,
                rdbColumnName: questionData.rdbColumnName,
                dataMartColumnName: questionData.dataMartColumnName,
                reportLabel: questionData.reportLabel || 'Report label'
            },
            messagingInfo: {
                includedInMessage: questionData.includedInMessage,
                messageVariableId: questionData.messageVariableId,
                labelInMessage: questionData.labelInMessage,
                codeSystem: questionData.codeSystem,
                requiredInMessage: questionData.requiredInMessage,
                hl7DataType: questionData.hl7DataType
            },
            label: questionData.label,
            uniqueName: questionData.uniqueName,
            uniqueId: questionData.uniqueId,
            subgroup: questionData.subgroup,
            adminComments: questionData.adminComments,
            description: questionData.label,
            type: questionData.type,
            codeSet: questionData.codeSet,
            tooltip: questionData.tooltip || '',
            displayControl: questionData.displayControl || 0,
            defaultValue: questionData.defaultValue,
            allowFutureDates: questionData.allowFutureDates,
            fieldLength: questionData.fieldLength,
            fieldSize: questionData.fieldSize,
            mask: questionData.mask
        };
        QuestionControllerService.createQuestionUsingPost({
            authorization,
            request
        }).then((response: any) => {
            showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
            resetInput();
            return response;
        });
    };
    const handleUpdateQuestion = () => {
        const request = {
            ...questionData,
            tooltip: questionData.tooltip || ''
        };
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
    const handleQuestionInput = ({ target }: any) => {
        setQuestionData({
            ...questionData,
            [target.name]: target?.type === 'checkbox' ? target?.checked : target.value
        });
    };
    const handleValidation = ({ target }: React.ChangeEvent<HTMLInputElement>, unique = true) => {
        const pattern = unique ? /^[a-zA-Z0-9_]*$/ : /^[a-zA-Z0-9\s?!,-_]*$/;
        setIsValid({
            ...isValid,
            [target?.name]: { error: !pattern.test(target?.value) || target?.value === '', fb: true }
        });
    };

    const resetInput = () => {
        setQuestionData(init);
    };
    const isDisableBtnFun = () => {
        let disabled = false;
        for (const keys in isValid) {
            if (keys && isValid[keys as keyof typeof validation].error) disabled = true;
        }
        return disabled;
    };

    const {
        label,
        uniqueName,
        labelInMessage,
        subgroup,
        uniqueId,
        defaultLabelInReport,
        defaultRdbTableName,
        rdbColumnName,
        messageVariableId,
        codeSystem
    } = questionData || {};
    const isDisableBtn =
        label ||
        uniqueName ||
        labelInMessage ||
        subgroup ||
        uniqueId ||
        defaultLabelInReport ||
        defaultRdbTableName ||
        rdbColumnName ||
        messageVariableId ||
        codeSystem;

    const formatOptionLabel = ({ value, label }: any) => (
        <div key={value} style={{ display: 'flex', alignItems: 'center', lineHeight: '12px' }}>
            <div style={{ marginRight: '16px' }}>
                <img src={renderIconFieldType(value)} />
            </div>
            <div>{label}</div>
        </div>
    );
    const setFieldType = (type: string) => fieldType.find((data) => data.value === type);

    const customStyles = {
        control: (base: any, state: any) => ({
            ...base,
            background: 'none',
            borderRadius: 0,
            borderColor: '#565c65',
            height: '40px',
            boxShadow: state.isFocused ? null : null,
            '&:hover': {
                borderColor: '#565c65'
            },
            outline: state.isFocused ? '0.25rem solid #2491ff' : 'none'
        }),
        container: (base: any) => {
            return {
                ...base
            };
        },
        indicatorSeparator: (base: any) => {
            return {
                ...base,
                backgroundColor: 'unset'
            };
        },
        path: (base: any) => ({
            ...base,
            display: 'none !important'
        })
    };

    const USWDSDropdownIndicator = (props: any) => (
        <components.DropdownIndicator {...props}>
            <div className="multi-select select-indicator margin-top-neg-1" />
        </components.DropdownIndicator>
    );
    const renderIconFieldType = (type: string): string => {
        switch (type) {
            case 'radio':
                return '/single-select.svg';
            case 'check':
                return '/multi-select.svg';
            case 'dropdown':
                return '/expand.svg';
            case 'TEXT':
                return '/textbox.svg';
            case 'area':
                return '/textarea.svg';
            case 'multi-select':
                return '/multi-drop.svg';
            case 'date-time':
                return '/calender.svg';
            default:
                return '/single-select.svg';
        }
    };

    return (
        <div className="create-question">
            <div className="create-question__container">
                <div className="ds-u-text-align--center margin-bottom-2em">
                    <h3 className="header-title margin-bottom-2px" data-testid="header-title">
                        {question?.id ? `Edit question` : `Let's create a new question`}
                    </h3>
                    <label className="fields-info">
                        All fields with <span className="mandatory-indicator">*</span> are required
                    </label>
                </div>
                <p className="fields-info margin-bottom-2em">These fields will be displayed to your users</p>
                <div className={isValid['label'].error ? 'error-border' : ''}>
                    <label>
                        Question Label<span className="mandatory-indicator">*</span>
                    </label>
                    {isValid['label'].error && <label className="error-text">Question Label Not Valid</label>}
                    <TextInput
                        className="field-space"
                        type="text"
                        id="questionLabel"
                        data-testid="questionLabel"
                        onBlur={(e: any) => handleValidation(e, false)}
                        name="label"
                        style={{ border: isValid['label'].error ? '1px solid #dc3545' : '1px solid black' }}
                        value={questionData.label}
                        onChange={handleQuestionInput}
                    />
                </div>
                <label>Description</label>
                <br></br>
                <TextInput
                    className="field-space"
                    type="text"
                    id="questionDesc"
                    data-testid="questionDesc"
                    name="description"
                    style={{ border: '1px solid black' }}
                    value={questionData.description}
                    onChange={handleQuestionInput}
                />
                <br></br>
                <label>Field Type</label>
                <br></br>
                <ReactSelect
                    className="field-space"
                    options={fieldType}
                    name="programArea"
                    defaultValue={setFieldType(questionData.type)}
                    placeholder="- Select -"
                    formatOptionLabel={formatOptionLabel}
                    styles={{
                        ...customStyles
                    }}
                    isSearchable={false}
                    components={{ DropdownIndicator: USWDSDropdownIndicator }}></ReactSelect>
                <hr className="divider" />
                <p className="fields-info">
                    These fields will not be displayed to your users, it only makes it easier for others to search for
                    this question in the Question library
                </p>
                <br></br>
                <label>
                    Subgroup <span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <Dropdown
                    className="field-space"
                    name="subgroup"
                    id="subgroup"
                    defaultValue={questionData.subgroup}
                    onChange={handleQuestionInput}>
                    <option>-Select-</option>
                    {buildOptions(familyOptions)}
                </Dropdown>
                <br></br>
                <div className={'display-flex'}>
                    <Radio
                        name="codeSet"
                        id="localOrPhin"
                        value="LOCAL"
                        className="margin-right-1em"
                        checked={questionData.codeSet === 'LOCAL'}
                        onChange={handleQuestionInput}
                        label="LOCAL"
                    />
                    <Radio
                        name="codeSet"
                        id="localOrPhin"
                        value="PHIN"
                        checked={questionData.codeSet === 'PHIN'}
                        onChange={handleQuestionInput}
                        label="PHIN"
                    />
                </div>
                <br></br>
                <div className={isValid['uniqueId'].error ? 'error-border' : ''}>
                    <label>
                        Unique ID<span className="mandatory-indicator">*</span>
                    </label>
                    {isValid['uniqueId'].error && <label className="error-text">Unique ID Not Valid</label>}
                    <TextInput
                        className="field-space"
                        type="text"
                        id="uniqueId"
                        data-testid="uniqueId"
                        name="uniqueId"
                        onBlur={handleValidation}
                        style={{ border: isValid['uniqueId'].error ? '1px solid #dc3545' : '1px solid black' }}
                        value={questionData.uniqueId}
                        onChange={handleQuestionInput}
                    />
                </div>
                <div className={isValid['uniqueName'].error ? 'error-border' : ''}>
                    <label>
                        Unique name<span className="mandatory-indicator">*</span>
                    </label>
                    {isValid['uniqueName'].error && <label className="error-text">Unique name Not Valid</label>}
                    <TextInput
                        className="field-space"
                        type="text"
                        id="uniqueName"
                        data-testid="uniqueName"
                        name="uniqueName"
                        onBlur={handleValidation}
                        style={{ border: isValid['uniqueName'].error ? '1px solid #dc3545' : '1px solid black' }}
                        value={questionData.uniqueName}
                        onChange={handleQuestionInput}
                    />
                </div>
                <p className="description">Is this question required?</p>
                <ToggleButton defaultChecked={true} onChange={handleQuestionInput} name="requiredMessage" />
                <hr className="divider" />
                <p className="fields-info">Data mart - these fields will not be displayed to your users</p>
                <br></br>
                <div className={isValid['defaultLabelInReport'].error ? 'error-border' : ''}>
                    <label>
                        Default Label in report<span className="mandatory-indicator">*</span>
                    </label>
                    {isValid['defaultLabelInReport'].error && (
                        <label className="error-text">Default Label in report Not Valid</label>
                    )}
                    <TextInput
                        className="field-space"
                        type="text"
                        id="defaultLabelInReport"
                        data-testid="defaultLabelInReport"
                        name="defaultLabelInReport"
                        style={{
                            border: isValid['defaultLabelInReport'].error ? '1px solid #dc3545' : '1px solid black'
                        }}
                        value={questionData.defaultLabelInReport}
                        onBlur={handleValidation}
                        onChange={handleQuestionInput}
                    />
                </div>
                <div className={isValid['defaultRdbTableName'].error ? 'error-border' : ''}>
                    <label>
                        Default RDB table name<span className="mandatory-indicator">*</span>
                    </label>
                    {isValid['defaultRdbTableName'].error && (
                        <label className="error-text">Default RDB table name Not Valid</label>
                    )}
                    <TextInput
                        className="field-space"
                        type="text"
                        id="defaultRDBTable"
                        data-testid="defaultRDBTable"
                        name="defaultRdbTableName"
                        style={{
                            border: isValid['defaultRdbTableName'].error ? '1px solid #dc3545' : '1px solid black'
                        }}
                        onBlur={handleValidation}
                        value={questionData.defaultRdbTableName}
                        onChange={handleQuestionInput}
                    />
                </div>
                <div className={isValid['rdbColumnName'].error ? 'error-border' : ''}>
                    <label>
                        RDB column name<span className="mandatory-indicator">*</span>
                    </label>
                    {isValid['rdbColumnName'].error && <label className="error-text">RDB column name Not Valid</label>}
                    <TextInput
                        className="field-space"
                        type="text"
                        id="rdbColumnName"
                        data-testid="rdbColumnName"
                        name="rdbColumnName"
                        style={{ border: isValid['rdbColumnName'].error ? '1px solid #dc3545' : '1px solid black' }}
                        value={questionData.rdbColumnName}
                        onBlur={handleValidation}
                        onChange={handleQuestionInput}
                    />
                </div>
                <label>Data mart column name</label>
                <br></br>
                <TextInput
                    className="field-space"
                    type="text"
                    id="datamartColName"
                    data-testid="datamartColName"
                    name="dataMartColumnName"
                    style={{ border: '1px solid black' }}
                    value={questionData.dataMartColumnName}
                    onChange={handleQuestionInput}
                />
                <p className="fields-info">Messaging - these fields will not be displayed to your users</p>
                <p className="fields-info">Included in message?</p>
                <ToggleButton
                    className="margin-bottom-1em"
                    checked={questionData.includedInMessage}
                    name="includedInMessage"
                    onChange={handleQuestionInput}
                />
                <div className={isValid['messageVariableId'].error ? 'error-border' : ''}>
                    <label>
                        Message ID<span className="mandatory-indicator">*</span>
                    </label>
                    {isValid['messageVariableId'].error && <label className="error-text">Message ID Not Valid</label>}
                    <TextInput
                        className="field-space"
                        type="text"
                        id="messageId"
                        data-testid="messageId"
                        name="messageVariableId"
                        style={{ border: isValid['messageVariableId'].error ? '1px solid #dc3545' : '1px solid black' }}
                        value={questionData.messageVariableId}
                        disabled={!questionData.includedInMessage}
                        onChange={handleQuestionInput}
                        onBlur={handleValidation}
                    />
                </div>
                <div className={isValid['labelInMessage'].error ? 'error-border' : ''}>
                    <label>
                        Message label<span className="mandatory-indicator">*</span>
                    </label>
                    {isValid['labelInMessage'].error && <label className="error-text">Message label Not Valid</label>}
                    <TextInput
                        className="field-space"
                        type="text"
                        id="messageLabel"
                        data-testid="messageLabel"
                        name="labelInMessage"
                        style={{ border: isValid['labelInMessage'].error ? '1px solid #dc3545' : '1px solid black' }}
                        value={questionData.labelInMessage}
                        disabled={!questionData.includedInMessage}
                        onChange={handleQuestionInput}
                        onBlur={(e: any) => handleValidation(e, false)}
                    />
                </div>
                <label>
                    Code system name<span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <Dropdown
                    className="field-space"
                    id="codeSystem"
                    name="codeSystem"
                    disabled={!questionData.includedInMessage}
                    value={questionData.codeSystem}
                    onChange={handleQuestionInput}>
                    <option>-Select-</option>
                    {buildCodeOptions(codeSystemOptionList)}
                </Dropdown>
                <p className="fields-info">Required in message?</p>
                <ToggleButton
                    checked={questionData.requiredInMessage}
                    name="requiredInMessage"
                    onChange={handleQuestionInput}
                />
                <br></br>
                <label>
                    HL7 data type <span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <Dropdown
                    className="field-space"
                    name="hl7DataType"
                    id="hl7DataType"
                    defaultValue={questionData.hl7DataType}
                    disabled={!questionData.includedInMessage}
                    onChange={handleQuestionInput}>
                    <option>-Select-</option>
                    {buildOptions(groupOptions)}
                </Dropdown>
                <hr className="divider" />
                <p className="fields-info">Administrative - these fields will not be displayed to your users</p>
                <br></br>
                <label>Administrative comments</label>
                <br></br>
                <TextInput
                    className="field-space"
                    type="text"
                    id="adminComments"
                    data-testid="adminComments"
                    name="adminComments"
                    style={{ border: '1px solid black' }}
                    value={questionData.adminComments}
                    onChange={handleQuestionInput}
                />
            </div>
            <ModalToggleButton
                className="submit-btn"
                type="button"
                modalRef={modalRef}
                onClick={handleSubmit}
                disabled={!isDisableBtn || isDisableBtnFun()}>
                {question?.id ? 'Save' : 'Create and add to page'}
            </ModalToggleButton>
            <ModalToggleButton className="cancel-btn" modalRef={modalRef} onClick={() => resetInput()} type="button">
                Cancel
            </ModalToggleButton>
        </div>
    );
};
