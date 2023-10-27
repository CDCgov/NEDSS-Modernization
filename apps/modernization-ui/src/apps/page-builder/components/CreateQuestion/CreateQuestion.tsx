import React, { useContext, useEffect, useRef, useState } from 'react';
import './CreateQuestion.scss';
import ReactSelect, { components } from 'react-select';
import {
    ModalToggleButton,
    Radio,
    TextInput,
    Dropdown,
    ButtonGroup,
    Button,
    ModalRef,
    Textarea
} from '@trussworks/react-uswds';
import { ValueSetControllerService, QuestionControllerService, UpdateQuestionRequest } from '../../generated';
import { UserContext } from 'user';
import { useAlert } from 'alert';
import { ToggleButton } from '../ToggleButton';
import { fieldType, text as textOption } from '../../constant/constant';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { ValuesetLibrary } from '../../pages/ValuesetLibrary/ValuesetLibrary';

export const CreateQuestion = ({ modalRef, question }: any) => {
    const init = {
        label: '',
        description: '',
        type: UpdateQuestionRequest.type.CODED,
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
        relatedUnits: 'No',
        futureDates: 'No',
        displayType: '',
        reportLabel: '',
        defaultValue: 'test@gmai.com',
        fieldLength: 50,
        mask: 'TXT',
        fieldSize: '50',
        tooltip: '',
        displayControl: 0,
        minValue: 0,
        maxValue: 50,
        allowFutureDates: false,
        dateFormat: '',
        relatedUnitsLiteral: 'ML',
        relatedUnitsValueSet: 2,
        valueset: '115920'
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
        hl7DataType: { required: true, error: false, fb: false },
        tooltip: { required: true, error: false, fb: false },
        displayType: { required: true, error: false, fb: false },
        fieldLength: { required: true, error: false, fb: false },
        minValue: { required: true, error: false, fb: false },
        maxValue: { required: true, error: false, fb: false },
        dateFormat: { required: true, error: false, fb: false }
    };

    // Fields
    const [questionData, setQuestionData] = useState(init);
    const { state } = useContext(UserContext);
    const { showAlert } = useAlert();
    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([]);
    const [groupOptions, setGroupOptions] = useState([]);
    const [isValid, setIsValid] = useState(validation);
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
        let request = {
            dataMartInfo: {
                defaultLabelInReport: 'Test',
                dataMartColumnName: questionData.dataMartColumnName,
                defaultRdbTableName: questionData.defaultRdbTableName,
                rdbColumnName: questionData.rdbColumnName,
                reportLabel: questionData.reportLabel || 'Report label'
            },
            messagingInfo: {
                codeSystem: questionData.codeSystem,
                conceptCode: 'ARBO',
                conceptName: 'CONDITION_FAMILY',
                preferredConceptName: 'Arboviral'
                // includedInMessage: questionData.includedInMessage,
                // messageVariableId: questionData.messageVariableId,
                // labelInMessage: questionData.labelInMessage,
                // requiredInMessage: questionData.requiredInMessage,
                // hl7DataType: questionData.hl7DataType
            },
            adminComments: questionData.adminComments,
            codeSet: questionData.codeSet,
            description: questionData.description,
            defaultValue: questionData.defaultValue, // text
            displayControl: questionData.displayControl,
            fieldLength: questionData.fieldLength,
            label: questionData.label,
            mask: questionData.mask,
            subgroup: questionData.subgroup,
            tooltip: questionData.tooltip,
            uniqueName: questionData.uniqueName,
            uniqueId: questionData.uniqueId
            // type: questionData.type,
            // allowFutureDates: questionData.allowFutureDates,
            // fieldSize: questionData.fieldSize
        };
        if (selectedFieldType === 'TEXT') {
            request = { ...request, defaultValue: questionData.defaultValue };
            QuestionControllerService.createTextQuestionUsingPost({
                authorization,
                request
            }).then((response: any) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        } else if (selectedFieldType === 'DATE') {
            // @ts-ignore
            request = { ...request, allowFutureDates: questionData.allowFutureDates };
            QuestionControllerService.createDateQuestionUsingPost({
                authorization,
                request
            }).then((response: any) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        } else if (selectedFieldType === 'NUMERIC') {
            const dateRequest = {
                ...request,
                minValue: 0,
                maxValue: 50,
                relatedUnitsLiteral: questionData.relatedUnitsLiteral,
                relatedUnitsValueSet: questionData.relatedUnitsValueSet
            };
            QuestionControllerService.createNumericQuestionUsingPost({
                authorization,
                request: dateRequest
            }).then((response: any) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        } else {
            // @ts-ignore
            request = { ...request, valueSet: questionData.valueset };
            QuestionControllerService.createCodedQuestionUsingPost({
                authorization,
                request
            }).then((response: any) => {
                showAlert({ type: 'success', header: 'Created', message: 'Question created successfully' });
                resetInput();
                return response;
            });
        }

        console.log('ensure', request);
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
        const { type, value, checked, name } = target;
        const newValue =
            type === 'radio' && name !== 'codeSet' ? value === 'Yes' : type === 'checkbox' ? checked : value;
        setQuestionData({
            ...questionData,
            [target.name]: newValue
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
                return '/icons/single-select.svg';
            case 'check':
                return '/icons/multi-select.svg';
            case 'dropdown':
                return '/icons/expand.svg';
            case 'TEXT':
                return '/icons/textbox.svg';
            case 'area':
                return '/icons/textarea.svg';
            case 'multi-select':
                return '/icons/multi-drop.svg';
            case 'date-time':
                return '/icons/calender.svg';
            default:
                return '/icons/single-select.svg';
        }
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
                Search Value set
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
            <div className={isValid['label'].error ? 'error-border' : ''}>
                <label htmlFor="questionLabel">
                    Question Label<span className="mandatory-indicator">*</span>
                </label>
                {isValid['label'].error && <label className="error-text">Question Label Not Valid</label>}
                <TextInput
                    className="field-space"
                    type="text"
                    id="questionLabel"
                    maxLength={50}
                    data-testid="questionLabel"
                    onBlur={(e: any) => handleValidation(e, false)}
                    name="label"
                    style={{ border: isValid['label'].error ? '1px solid #dc3545' : '1px solid black' }}
                    value={questionData.label}
                    onChange={handleQuestionInput}
                />
            </div>
            <div className={isValid['tooltip'].error ? 'error-border' : ''}>
                <label>
                    Tooltip <span className="mandatory-indicator">*</span>
                </label>
                {isValid['tooltip'].error && <label className="error-text">Tooltip Not Valid</label>}
                <Textarea
                    className="field-space"
                    id="tooltip"
                    maxLength={2000}
                    data-testid="tooltip"
                    rows={2}
                    onBlur={(e: any) => handleValidation(e, false)}
                    name="tooltip"
                    style={{ border: isValid['tooltip'].error ? '1px solid #dc3545' : '1px solid black' }}
                    value={questionData.tooltip}
                    onChange={handleQuestionInput}
                />
            </div>
            <label htmlFor="displayType">
                Display Type <span className="mandatory-indicator">*</span>
            </label>
            <br></br>
            <Dropdown
                className="field-space"
                id="displayType"
                name="displayType"
                value={questionData.displayType}
                onChange={handleQuestionInput}>
                <option>-Select-</option>
                {buildCodeOptions(textOption)}
            </Dropdown>
            <hr className="divider" />
        </>
    );

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
                <h4>Basic information</h4>
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
                        className="field-spac margin-bottom-0"
                        type="text"
                        id="uniqueId"
                        maxLength={50}
                        data-testid="uniqueId"
                        name="uniqueId"
                        onBlur={handleValidation}
                        style={{ border: isValid['uniqueId'].error ? '1px solid #dc3545' : '1px solid black' }}
                        value={questionData.uniqueId}
                        onChange={handleQuestionInput}
                    />
                    <p className="fields-help-text margin-bottom-1em margin-top-0">
                        If you decide not to provide a UNIQUE ID the system will generate one for you.
                    </p>
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
                        maxLength={50}
                        data-testid="uniqueName"
                        name="uniqueName"
                        onBlur={handleValidation}
                        style={{ border: isValid['uniqueName'].error ? '1px solid #dc3545' : '1px solid black' }}
                        value={questionData.uniqueName}
                        onChange={handleQuestionInput}
                    />
                </div>
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
                <label>Description</label>
                <br></br>
                <Textarea
                    className="field-space"
                    id="questionDesc"
                    data-testid="questionDesc"
                    name="description"
                    rows={1}
                    maxLength={50}
                    style={{ border: '1px solid black' }}
                    value={questionData.description}
                    onChange={handleQuestionInput}
                />
                <label>Field Type</label>
                <ReactSelect
                    className="field-space display-none"
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
                    <>
                        <label>
                            Mask <span className="mandatory-indicator">*</span>
                        </label>
                        <br></br>
                        <Dropdown
                            className="field-space"
                            id="inputMask"
                            name="mask"
                            value={questionData.mask}
                            onChange={handleQuestionInput}>
                            <option>-Select-</option>
                            {buildCodeOptions(codeSystemOptionList)}
                        </Dropdown>
                        <div className={isValid['fieldLength'].error ? 'error-border' : ''}>
                            <label>
                                Field length<span className="mandatory-indicator">*</span>
                            </label>
                            {isValid['fieldLength'].error && (
                                <label className="error-text">Field Length Not Valid</label>
                            )}
                            <TextInput
                                className="field-space"
                                type="number"
                                id="fieldLength"
                                maxLength={50}
                                data-testid="fieldLength"
                                onBlur={(e: any) => handleValidation(e, false)}
                                name="fieldLength"
                                style={{
                                    border: isValid['fieldLength'].error ? '1px solid #dc3545' : '1px solid black'
                                }}
                                value={questionData.fieldLength}
                                onChange={handleQuestionInput}
                            />
                        </div>
                    </>
                )}
                {selectedFieldType === UpdateQuestionRequest.type.NUMERIC && (
                    <>
                        <div className={isValid['minValue'].error ? 'error-border' : ''}>
                            <label>Minimum Value</label>
                            <TextInput
                                className="field-space"
                                type="number"
                                id="minValue"
                                maxLength={50}
                                data-testid="minValue"
                                onBlur={(e: any) => handleValidation(e, false)}
                                name="minValue"
                                style={{ border: isValid['minValue'].error ? '1px solid #dc3545' : '1px solid black' }}
                                value={questionData.minValue}
                                onChange={handleQuestionInput}
                            />
                        </div>
                        <div className={isValid['maxValue'].error ? 'error-border' : ''}>
                            <label>Maximum value</label>
                            {/* {isValid['maxValue'].error && <label className="error-text">Maximum Value Not Valid</label>}*/}
                            <TextInput
                                className="field-space"
                                type="number"
                                id="maxValue"
                                maxLength={50}
                                data-testid="maxValue"
                                onBlur={(e: any) => handleValidation(e, false)}
                                name="maxValue"
                                style={{ border: isValid['maxValue'].error ? '1px solid #dc3545' : '1px solid black' }}
                                value={questionData.maxValue}
                                onChange={handleQuestionInput}
                            />
                        </div>
                        <label>
                            Related units <span className="mandatory-indicator">*</span>
                        </label>
                        <div className={'display-flex'}>
                            <Radio
                                name="relatedUnits"
                                id="relatedUnits"
                                value="Yes"
                                className="margin-right-1em"
                                checked={questionData.relatedUnits === 'Yes'}
                                onChange={handleQuestionInput}
                                label="Yes"
                            />
                            <Radio
                                name="relatedUnits"
                                id="relatedUnits"
                                value="No"
                                checked={questionData.relatedUnits === 'No'}
                                onChange={handleQuestionInput}
                                label="No"
                            />
                        </div>
                    </>
                )}
                {selectedFieldType === UpdateQuestionRequest.type.DATE && (
                    <>
                        <div className={isValid['dateFormat'].error ? 'error-border' : ''}>
                            <label>
                                Date format<span className="mandatory-indicator">*</span>
                            </label>
                            {isValid['dateFormat'].error && <label className="error-text">Date format Not Valid</label>}
                            <Dropdown
                                className="field-space"
                                id="inputMask"
                                name="dateFormat"
                                value={questionData.dateFormat}
                                onChange={handleQuestionInput}>
                                <option value="MM/DD/yyyy">Generic date (MM/DD/YYYY)</option>
                                {buildCodeOptions(codeSystemOptionList)}
                            </Dropdown>
                        </div>
                        <br></br>
                        <label className="margin-top-1em">
                            Allow for future dates <span className="mandatory-indicator">*</span>
                        </label>
                        <div className={'display-flex'}>
                            <Radio
                                name="allowFutureDates"
                                id="futureDates"
                                value="Yes"
                                className="margin-right-1em"
                                checked={questionData.allowFutureDates}
                                onChange={handleQuestionInput}
                                label="Yes"
                            />
                            <Radio
                                name="allowFutureDates"
                                id="futureDatesNo"
                                value="No"
                                checked={!questionData.allowFutureDates}
                                onChange={handleQuestionInput}
                                label="No"
                            />
                        </div>
                    </>
                )}
                <hr className="divider" />
                {renderUserInterface}
                <h4>Data mart</h4>
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
                        maxLength={50}
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
                        maxLength={50}
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
                        maxLength={50}
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
                    maxLength={50}
                    name="dataMartColumnName"
                    style={{ border: '1px solid black' }}
                    value={questionData.dataMartColumnName}
                    onChange={handleQuestionInput}
                />
                <h4>Messaging</h4>
                <p className="fields-info">Messaging - these fields will not be displayed to your users</p>
                <p className="fields-info">Included in message?</p>
                <div className="msg-toggle-container">
                    {/* <label>No</label>*/}
                    <ToggleButton
                        className="margin-bottom-1em"
                        checked={questionData.includedInMessage}
                        name="includedInMessage"
                        onChange={handleQuestionInput}
                    />
                    {/* <label>Yes</label>*/}
                </div>
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
                        Message label <span className="mandatory-indicator">*</span>
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
                    Code system name <span className="mandatory-indicator">*</span>
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
                <div className="msg-toggle-container">
                    {/* <label>No</label>*/}
                    <ToggleButton
                        checked={questionData.requiredInMessage}
                        name="requiredInMessage"
                        onChange={handleQuestionInput}
                    />{' '}
                    {/* <label>Yes</label>*/}
                </div>
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
                <label htmlFor="hl7Segment">
                    HL7 Segment <span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <Dropdown
                    className="hl7-segment"
                    name="hl7Segment"
                    id="hl7Segment"
                    defaultValue={'OBX-3.0'}
                    disabled={true}>
                    <option className="obx-30">OBX-3.0</option>
                </Dropdown>
                <hr className="divider" />
                <h4>Administrative</h4>
                <p className="fields-info">Administrative - these fields will not be displayed to your users</p>
                <br></br>
                <label>Administrative comments</label>
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
