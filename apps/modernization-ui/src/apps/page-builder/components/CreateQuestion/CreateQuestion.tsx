import React, { useContext, useEffect, useState } from 'react';
import './CreateQuestion.scss';
import ReactSelect from 'react-select';
import { ModalToggleButton } from '@trussworks/react-uswds';
import { ProgramAreaControllerService, ValueSetControllerService, QuestionControllerService } from '../../generated';
import { UserContext } from 'user';
import { useAlert } from 'alert';
import { ToggleButton } from '../ToggleButton';

export const CreateQuestion = ({ modalRef, question }: any) => {
    // Fields
    const [name, setName] = useState('');
    const [system, setSystem] = useState('');
    const [code, setCode] = useState('');
    const [area, setArea] = useState('');
    const [isLocalOrPhin, setIsLocalOrPhin] = useState('Y');
    const [family, setFamily] = useState('');
    const [group, setGroup] = useState('');
    const { state } = useContext(UserContext);
    const { showAlert } = useAlert();
    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([]);
    const [groupOptions, setGroupOptions] = useState([]);
    const [programAreaOptions, setProgramAreaOptions] = useState([]);

    const [isQuestionNotValid, setIsQuestionNotValid] = useState(false);
    const [isValidationFailure, setIsValidationFailure] = useState(false);
    const authorization = `Bearer ${state.getToken()}`;

    const fetchProgramAreaOptions = () => {
        ProgramAreaControllerService.getProgramAreasUsingGet({
            authorization
        }).then((response: any) => {
            const data = response || [];
            const programAreaList: never[] = [];
            data.map((each: { value: never }) => {
                programAreaList.push(each.value);
            });
            setProgramAreaOptions(programAreaList);
        });
    };

    const fetchFamilyOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization,
            codeSetNm: 'CONDITION_FAMILY'
        }).then((response: any) => {
            const data = response || [];
            const familyList: never[] = [];
            data.map((each: { value: never }) => {
                familyList.push(each.value);
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
            data.map((each: { value: never }) => {
                coinfectionGroupList.push(each.value);
            });
            setGroupOptions(coinfectionGroupList);
        });
    };

    useEffect(() => {
        fetchFamilyOptions();
        fetchGroupOptions();
        fetchProgramAreaOptions();
    }, []);

    const buildOptions = (optionsToBuild: any[]) =>
        optionsToBuild.map((opt: string) => (
            <option value={opt} key={opt}>
                {opt}
            </option>
        ));
    const handleSubmit = () => {
        if (question?.id) return handleUpdateQuestion();
        const request = {
            codeSystemDescTxt: system,
            conditionShortNm: name,
            id: code,
            progAreaCd: area,
            reportableMorbidityInd: isLocalOrPhin,
            familyCd: family,
            coinfectionGrpCd: group
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
            codeSystemDescTxt: system,
            conditionShortNm: name,
            id: code,
            progAreaCd: area,
            reportableMorbidityInd: isLocalOrPhin,
            familyCd: family,
            coinfectionGrpCd: group,
            adminComments: '',
            allowFutureDates: true
            // codeSystem: 'string',
            // datamartColumnName: 'string',
            // defaultLabelInReport: 'string',
            // defaultValue: 'string',
            // description: 'string',
            // displayControl: 0,
            // fieldLength: 'string',
            // hl7DataType: 'string',
            // includedInMessage: true,
            // label: 'string',
            // labelInMessage: 'string',
            // mask: 'string',
            // maxValue: 0,
            // messageVariableId: 'string',
            // minValue: 0,
            // rdbColumnName: 'string',
            // requiredInMessage: true,
            // tooltip: 'string',
            // type: 'CODED',
            // uniqueName: 'string',
            // unitType: 'CODED',
            // unitValue: 'string',
            // valueSet: 0
        };

        QuestionControllerService.updateQuestionUsingPut({
            authorization,
            id: 101,
            request
        }).then((response: any) => {
            showAlert({ type: 'success', header: 'Updated', message: 'Question updated successfully' });
            resetInput();
            return response;
        });
    };
    const resetInput = () => {
        setName('');
        setSystem('');
        setCode('');
        setArea('');
    };
    const validateQuestionLabel = (name: any) => {
        const pattern = /^[a-zA-Z0-9_]*$/;
        if (name.match(pattern)) {
            setIsQuestionNotValid(false);
            setIsValidationFailure(false);
        } else {
            setIsQuestionNotValid(true);
            setIsValidationFailure(true);
        }
    };

    const isDisableBtn = !name || !system || !code || !area;

    return (
        <div className="create-question">
            <div className="create-question__container">
                <div className="ds-u-text-align--center margin-bottom-2em">
                    <h3 className="header-title margin-bottom-2px">
                        {question?.id ? `Edit question` : `Let's create a new question`}
                    </h3>
                    <label className="fields-info">
                        All fields with <span className="mandatory-indicator">*</span> are required
                    </label>
                </div>

                <p className="fields-info margin-bottom-2em">These fields will be displayed to your users</p>
                <div className={isQuestionNotValid ? 'error-border' : ''}>
                    <label>
                        Question Label<span className="mandatory-indicator">*</span>
                    </label>
                    {isQuestionNotValid && <label className="error-text">Question Name Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="questionLabel"
                        data-testid="questionLabel"
                        name="questionLabel"
                        style={{ border: isQuestionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateQuestionLabel(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <label>Description</label>
                <br></br>
                <input
                    className="field-space"
                    type="text"
                    id="questionDesc"
                    data-testid="questionDesc"
                    name="questionDesc"
                    style={{ border: '1px solid black' }}
                    value={name}
                    onInput={(e: any) => setName(e.target.value)}
                />
                <br></br>
                <label>Field Type</label>
                <br></br>
                <select
                    className="field-space"
                    name="programArea"
                    defaultValue={area}
                    onChange={(e: any) => setArea(e.target.value)}>
                    <option>-Select-</option>
                    {buildOptions(programAreaOptions)}
                </select>
                <ReactSelect
                    className="field-space display-none"
                    options={programAreaOptions}
                    name="programArea"
                    defaultValue={area}
                    // getOptionLabel={(option: any) => <div>{option}</div>}
                    onChange={(e: any) => setArea(e.target.value)}></ReactSelect>
                <hr className="divider" />
                <p className="fields-info">
                    These fields will not be displayed to your users, it only makes it easier for others to search for
                    this question in the Question library
                </p>
                <br></br>
                <label>
                    Subgroup<span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <select
                    className="field-space"
                    name="subGroup"
                    defaultValue={family}
                    onChange={(e: any) => setFamily(e.target.value)}>
                    <option>-Select-</option>
                    {buildOptions(familyOptions)}
                </select>
                <br></br>
                <input
                    type="radio"
                    name="localOrPhin"
                    value="Y"
                    className="field-space"
                    checked={isLocalOrPhin === 'Y'}
                    onChange={(e: any) => setIsLocalOrPhin(e.target.value)}
                />
                <span className="radio-label">LOCAL</span>
                <input
                    type="radio"
                    name="localOrPhin"
                    value="N"
                    className="right-radio"
                    checked={isLocalOrPhin !== 'Y'}
                    onChange={(e: any) => setIsLocalOrPhin(e.target.value)}
                />
                <span className="radio-label">PHIN</span>
                <br></br>
                <div className={isQuestionNotValid ? 'error-border' : ''}>
                    <label>
                        Unique ID<span className="mandatory-indicator">*</span>
                    </label>
                    {isQuestionNotValid && <label className="error-text">Unique ID Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="uniqueId"
                        data-testid="uniqueId"
                        name="uniqueId"
                        style={{ border: isQuestionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateQuestionLabel(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <div className={isQuestionNotValid ? 'error-border' : ''}>
                    <label>
                        Unique name<span className="mandatory-indicator">*</span>
                    </label>
                    {isQuestionNotValid && <label className="error-text">Unique name Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="uniqueName"
                        data-testid="uniqueName"
                        name="uniqueName"
                        style={{ border: isQuestionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateQuestionLabel(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <p className="description">Is this question required?</p>
                <ToggleButton checked={true} name="requiredMessage" />
                <hr className="divider" />
                <p className="fields-info">Data mart - these fields will not be displayed to your users</p>
                <br></br>
                <div className={isQuestionNotValid ? 'error-border' : ''}>
                    <label>
                        Default Label in report<span className="mandatory-indicator">*</span>
                    </label>
                    {isQuestionNotValid && <label className="error-text">Default Label in report Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="defaultLabelInReport"
                        data-testid="defaultLabelInReport"
                        name="defaultLabelInReport"
                        style={{ border: isQuestionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateQuestionLabel(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <div className={isQuestionNotValid ? 'error-border' : ''}>
                    <label>
                        Default RDB table name<span className="mandatory-indicator">*</span>
                    </label>
                    {isQuestionNotValid && <label className="error-text">Default RDB table name Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="defaultRDBTable"
                        data-testid="defaultRDBTable"
                        name="defaultRDBTable"
                        style={{ border: isQuestionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateQuestionLabel(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <div className={isQuestionNotValid ? 'error-border' : ''}>
                    <label>
                        RDB column name<span className="mandatory-indicator">*</span>
                    </label>
                    {isQuestionNotValid && <label className="error-text">RDB column name Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="questionLabel"
                        data-testid="questionLabel"
                        name="questionLabel"
                        style={{ border: isQuestionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateQuestionLabel(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <label>Data mart column name</label>
                <br></br>
                <input
                    className="field-space"
                    type="text"
                    id="datamartColName"
                    data-testid="datamartColName"
                    name="datamartColName"
                    style={{ border: '1px solid black' }}
                    value={name}
                    onInput={(e: any) => setName(e.target.value)}
                />
                <br></br>
                <br></br>
                <p className="fields-info">Messaging - these fields will not be displayed to your users</p>
                <br></br>
                <p className="fields-info">Included in message?</p>
                <ToggleButton className="margin-bottom-1em" checked={true} name="requiredMessage" />
                <div className={isQuestionNotValid ? 'error-border' : ''}>
                    <label>
                        Message ID<span className="mandatory-indicator">*</span>
                    </label>
                    {isQuestionNotValid && <label className="error-text">Message ID Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="messageId"
                        data-testid="messageId"
                        name="messageId"
                        style={{ border: isQuestionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateQuestionLabel(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <div className={isQuestionNotValid ? 'error-border' : ''}>
                    <label>
                        Message label<span className="mandatory-indicator">*</span>
                    </label>
                    {isQuestionNotValid && <label className="error-text">Message label Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="messageLabel"
                        data-testid="messageLabel"
                        name="messageLabel"
                        style={{ border: isQuestionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateQuestionLabel(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <label>
                    Code system name<span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <select
                    className="field-space"
                    name="codeSystemName"
                    defaultValue={group}
                    onChange={(e: any) => setGroup(e.target.value)}>
                    <option>-Select-</option>
                    {buildOptions(groupOptions)}
                </select>
                <p className="fields-info">Required in message?</p>
                <ToggleButton checked={true} name="requiredMessage" />
                <br></br>
                <label>
                    HL7 data type <span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <select
                    className="field-space"
                    name="hl7datatype"
                    defaultValue={group}
                    onChange={(e: any) => setGroup(e.target.value)}>
                    <option>-Select-</option>
                    {buildOptions(groupOptions)}
                </select>
                <label>Data mart column name</label>
                <br></br>
                <input
                    className="field-space"
                    type="text"
                    id="datamartColName"
                    data-testid="datamartColName"
                    name="datamartColName"
                    style={{ border: '1px solid black' }}
                    value={name}
                    onInput={(e: any) => setName(e.target.value)}
                />
                <hr className="divider" />
                <p className="fields-info">Administrative - these fields will not be displayed to your users</p>
                <br></br>
                <label>Administrative comments</label>
                <br></br>
                <input
                    className="field-space"
                    type="text"
                    id="adminComments"
                    data-testid="adminComments"
                    name="adminComments"
                    style={{ border: '1px solid black' }}
                    value={name}
                    onInput={(e: any) => setName(e.target.value)}
                />
            </div>
            <ModalToggleButton
                className="submit-btn"
                type="button"
                modalRef={modalRef}
                onClick={handleSubmit}
                disabled={isValidationFailure || isDisableBtn}>
                {question?.id ? 'Save' : 'Create and add to page'}
            </ModalToggleButton>
            <ModalToggleButton className="cancel-btn" modalRef={modalRef} onClick={() => resetInput()} type="button">
                Cancel
            </ModalToggleButton>
        </div>
    );
};
