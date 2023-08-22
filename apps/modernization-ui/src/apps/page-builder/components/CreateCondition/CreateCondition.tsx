import React, { RefObject, useContext, useEffect, useState } from 'react';
import './CreateCondition.scss';
import { Button, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ProgramAreaControllerService, ConditionControllerService, ValueSetControllerService } from '../../generated';
import { UserContext } from 'user';
import { useAlert } from 'alert';
import { Concept } from '../../generated/models/Concept';

type Props = {
    modal?: RefObject<ModalRef>;
};

export const CreateCondition = ({ modal }: Props) => {
    // Fields
    const [name, setName] = useState('');
    const [system, setSystem] = useState('');
    const [code, setCode] = useState('');
    const [area, setArea] = useState('');
    const [isReportable, setIsReportable] = useState('Y');
    const [isMorbidity, setIsMorbidity] = useState('Y');
    const [isAggregate, setIsAggregate] = useState('N');
    const [isTracingModule, setIsTracingModule] = useState('Y');
    const [family, setFamily] = useState('');
    const [group, setGroup] = useState('');
    const { state } = useContext(UserContext);
    const { showAlert } = useAlert();

    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([] as Concept[]);
    const [groupOptions, setGroupOptions] = useState([] as Concept[]);
    const [programAreaOptions, setProgramAreaOptions] = useState([] as Concept[]);
    const [systemOptions, setSystemOptions] = useState([] as Concept[]);

    const [isConditionNotValid, setIsConditionNotValid] = useState(false);
    const [isConditionCodeNotValid, setIsConditionCodeNotValid] = useState(false);
    const [isValidationFailure, setIsValidationFailure] = useState(false);

    const fetchCodingSystemOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: `Bearer ${state.getToken()}`,
            codeSetNm: 'CODE_SYSTEM'
        }).then((response: Concept[]) => {
            setSystemOptions(response);
        });
    };

    const fetchProgramAreaOptions = () => {
        ProgramAreaControllerService.getProgramAreasUsingGet({
            authorization: `Bearer ${state.getToken()}`
        }).then((response: Concept[]) => {
            setProgramAreaOptions(response);
        });
    };

    const fetchFamilyOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: `Bearer ${state.getToken()}`,
            codeSetNm: 'CONDITION_FAMILY'
        }).then((response: Concept[]) => {
            setFamilyOptions(response);
        });
    };

    const fetchGroupOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: `Bearer ${state.getToken()}`,
            codeSetNm: 'COINFECTION_GROUP'
        }).then((response: Concept[]) => {
            setGroupOptions(response);
        });
    };

    useEffect(() => {
        fetchFamilyOptions();
        fetchGroupOptions();
        fetchProgramAreaOptions();
        fetchCodingSystemOptions();
    }, []);

    const buildOptions = (optionsToBuild: Concept[]) =>
        optionsToBuild.map((o, i) => (
            <option key={i} value={o.conceptCode}>
                {o.display}
            </option>
        ));
    const handleSubmit = () => {
        const authorization = `Bearer ${state.getToken()}`;
        const request = {
            codeSystemDescTxt: system,
            conditionShortNm: name,
            id: code,
            progAreaCd: area,
            nndInd: isReportable,
            reportableMorbidityInd: isMorbidity,
            reportableSummaryInd: isAggregate,
            contactTracingEnableInd: isTracingModule,
            familyCd: family,
            coinfectionGrpCd: group
        };

        ConditionControllerService.createConditionUsingPost({
            authorization,
            request
        })
            .then((response: any) => {
                showAlert({ type: 'success', header: 'Created', message: 'Condition created successfully' });
                resetInput();
                return response;
            })
            .catch((error: any) => {
                console.log(error.body);
                showAlert({ type: 'error', header: 'Error', message: error.body.message });
            });
    };
    const resetInput = () => {
        setName('');
        setSystem('');
        setCode('');
        setArea('');
    };
    const validateConditionName = (name: any) => {
        const pattern = /^[a-zA-Z0-9_]*$/;
        if (name.match(pattern)) {
            setIsConditionNotValid(false);
            setIsValidationFailure(false);
        } else {
            setIsConditionNotValid(true);
            setIsValidationFailure(true);
        }
    };

    const validateConditionCode = (code: any) => {
        const pattern = /^[a-zA-Z0-9_]*$/;
        if (code.match(pattern)) {
            setIsConditionCodeNotValid(false);
            setIsValidationFailure(false);
        } else {
            setIsConditionCodeNotValid(true);
            setIsValidationFailure(true);
        }
    };

    const isDisableBtn = !name || !system || !code || !area;

    return (
        <div className="create-condition">
            <h3 className="main-header-title" data-testid="header-title">
                Create a new Condition
            </h3>
            <div className="create-condition__container">
                <h3 className="header-title">Let's create a new condition to add to your page</h3>
                <p className="description">
                    First, we fill out some information about your new condition before creating it and associating it
                    to your new page
                </p>
                <p className="fields-info">
                    All fields with <span className="mandatory-indicator">*</span> are required
                </p>
                <br></br>
                <div className={isConditionNotValid ? 'error-border' : ''}>
                    <label>
                        Condition Name<span className="mandatory-indicator">*</span>
                    </label>
                    {isConditionNotValid && <label className="error-text">Condition Name Not Valid</label>}
                    <input
                        className="field-space"
                        type="text"
                        id="conditionName"
                        data-testid="conditionName"
                        name="conditionName"
                        style={{ border: isConditionNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateConditionName(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <label>
                    Coding System<span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <select
                    className="field-space"
                    name="codingSystem"
                    value={system}
                    onChange={(e: any) => setSystem(e.target.value)}>
                    <option>- Select -</option>
                    {buildOptions(systemOptions)}
                </select>
                <br></br>
                <div className={isConditionCodeNotValid ? 'error-border' : ''}>
                    <label>
                        Condition Code<span className="mandatory-indicator">*</span>
                    </label>
                    <br></br>
                    <p className="error-text">Condition Code Not Valid</p>
                    <input
                        className="field-space"
                        type="text"
                        name="conditionCode"
                        style={{ border: isConditionCodeNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateConditionCode(e.target.value)}
                        value={code}
                        onInput={(e: any) => setCode(e.target.value)}
                    />
                </div>
                <label>
                    Program Area<span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <select
                    className="field-space"
                    name="programArea"
                    defaultValue={area}
                    onChange={(e: any) => setArea(e.target.value)}>
                    <option>-Select-</option>
                    {buildOptions(programAreaOptions)}
                </select>
                <br></br>
                <label>
                    Is this a CDC reportable condition (NND)?
                    <span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <input
                    type="radio"
                    name="reportableCondition"
                    value="Y"
                    className="field-space"
                    checked={isReportable === 'Y'}
                    onChange={(e: any) => setIsReportable(e.target.value)}
                />
                <span className="radio-label">Yes</span>
                <input
                    type="radio"
                    name="reportableCondition"
                    value="N"
                    className="right-radio"
                    checked={isReportable === 'Y'}
                    onChange={(e: any) => setIsReportable(e.target.value)}
                />
                <span className="radio-label">No</span>
                <br></br>
                <label>
                    Is this reportable through Morbidity Reports?
                    <span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <input
                    type="radio"
                    name="mobilityReports"
                    value="Y"
                    className="field-space"
                    checked={isMorbidity === 'Y'}
                    onChange={(e: any) => setIsMorbidity(e.target.value)}
                />
                <span className="radio-label">Yes</span>
                <input
                    type="radio"
                    name="mobilityReports"
                    value="N"
                    className="right-radio"
                    checked={isMorbidity !== 'Y'}
                    onChange={(e: any) => setIsMorbidity(e.target.value)}
                />
                <span className="radio-label">No</span>
                <br></br>
                <label>
                    Is this reportable in Aggregate (summary)?
                    <span className="mandatory-indicator">*</span>
                </label>
                <br />
                <input
                    type="radio"
                    name="reportableAggregate"
                    value="Y"
                    className="field-space"
                    checked={isAggregate === 'Y'}
                    onChange={(e: any) => setIsAggregate(e.target.value)}
                />
                <span className="radio-label">Yes</span>
                <input
                    type="radio"
                    name="reportableAggregate"
                    value="N"
                    className="right-radio"
                    checked={isAggregate !== 'Y'}
                    onChange={(e: any) => setIsAggregate(e.target.value)}
                />
                <span className="radio-label">No</span>
                <br />
                <label>
                    Will this condition need the Contact Tracing Module?
                    <span className="mandatory-indicator">*</span>
                </label>
                <br></br>
                <input
                    type="radio"
                    name="tracingModule"
                    value="Y"
                    className="field-space"
                    checked={isTracingModule === 'Y'}
                    onChange={(e: any) => setIsTracingModule(e.target.value)}
                />
                <span className="radio-label">Yes</span>
                <input
                    type="radio"
                    name="tracingModule"
                    value="N"
                    className="right-radio"
                    checked={isTracingModule !== 'Y'}
                    onChange={(e: any) => setIsTracingModule(e.target.value)}
                />
                <span className="radio-label">No</span>
                <br />
                <p>Would you like to add any additional information?</p>
                <p className="fields-info">These fields are optional, you can make changes to this later.</p>
                <br />
                <label>Condition family</label>
                <br></br>
                <select
                    className="field-space"
                    name="conditionFamily"
                    defaultValue={family}
                    onChange={(e: any) => setFamily(e.target.value)}>
                    <option>-Select-</option>
                    {buildOptions(familyOptions)}
                </select>
                <br></br>
                <label>Co-infection group</label>
                <br></br>
                <select
                    className="field-space"
                    name="coinfectionGroup"
                    defaultValue={group}
                    onChange={(e: any) => setGroup(e.target.value)}>
                    <option>-Select-</option>
                    {buildOptions(groupOptions)}
                </select>
                <br></br>
            </div>
            {modal ? (
                <ModalToggleButton modalRef={modal} closer className="submit-btn" onClick={handleSubmit}>
                    Create & add condition
                </ModalToggleButton>
            ) : (
                <Button
                    className="submit-btn"
                    type="submit"
                    onClick={handleSubmit}
                    disabled={isValidationFailure || isDisableBtn}>
                    Create & add condition
                </Button>
            )}
            {modal ? (
                <ModalToggleButton modalRef={modal} closer className="cancel-btn" onClick={() => resetInput()}>
                    Cancel
                </ModalToggleButton>
            ) : (
                <Button className="cancel-btn" type="submit" onClick={() => resetInput()}>
                    Cancel
                </Button>
            )}
        </div>
    );
};
