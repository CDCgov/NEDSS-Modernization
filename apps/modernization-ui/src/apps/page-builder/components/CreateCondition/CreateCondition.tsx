import React, { useContext, useEffect, useState} from 'react';
import { ClassicButton } from '../../../../classic';
import './CreateCondition.scss';
import { ProgramAreaControllerService, ValueSetControllerService } from '../../generated';
import { UserContext } from 'user';

export const CreateCondition = () => {
    // Fields
    const [name, setName] = useState('');
    const [system, setSystem] = useState('');
    const [code, setCode] = useState('');
    const [area, setArea] = useState('');
    const [isReportable, setIsReportable] = useState(true);
    const [isMorbidity, setIsMorbidity] = useState(true);
    const [isAggregate, setIsAggregate] = useState(false);
    const [isTracingModule, setIsTracingModule] = useState(true);
    const [family, setFamily] = useState('');
    const [group, setGroup] = useState('');
    const { state } = useContext(UserContext);

    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([]);
    const [groupOptions, setGroupOptions] = useState([]);
    const [programAreaOptions, setProgramAreaOptions] = useState([]);
    const [systemOptions, setSystemOptions] = useState([]);

    const [isConditionNotValid, setIsConditionNotValid] = useState(false);
    const [isConditionCodeNotValid, setIsConditionCodeNotValid] = useState(false);
    const [isValidationFailure, setIsValidationFailure] = useState(false);

    const fetchCodingSystemOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: `Bearer ${state.getToken()}`,
            codeSetNm: 'CODE_SYSTEM'
        }).then((response: any) => {
            const data = response || [];
            const codingSystemList: never[] = [];
            data.map((each: { value: never }) => {
                codingSystemList.push(each.value);
            });
            setSystemOptions(codingSystemList);
        });
    };

    const fetchProgramAreaOptions = () => {
        ProgramAreaControllerService.getProgramAreasUsingGet({
            authorization: `Bearer ${state.getToken()}`
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
            authorization: `Bearer ${state.getToken()}`,
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
            authorization: `Bearer ${state.getToken()}`,
            // authorization: `Bearer ${token}`,
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
        fetchCodingSystemOptions();
    }, []);

    const buildOptions = (optionsToBuild: any[]) =>
        optionsToBuild.map((opt: string) => (
            <option value={opt} key={opt}>
                {opt}
            </option>
        ));
    const handleSubmit = () => {
        console.log('xox-handleSubmit', {
            name,
            system,
            code,
            area,
            isReportable,
            isMorbidity,
            isAggregate,
            isTracingModule,
            family,
            group
        });
        // xox- POST API call here
        //
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

    const validateConditionCode = (name: any) => {
        const pattern = /^[a-zA-Z0-9_]*$/;
        if (name.match(pattern)) {
            setIsConditionCodeNotValid(false);
            setIsValidationFailure(false);
        } else {
            setIsConditionCodeNotValid(true);
            setIsValidationFailure(true);
        }
    };

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
                    All fields with <span className="mandatory-indicator">*</span> are require
                </p>
                <br></br>
                <div className={isConditionNotValid ? 'error-border' : ''}>
                    <label>
                        Condition Name<span className="mandatory-indicator">*</span>
                    </label>
                    <p className="error-text">Condition Name Not Valid</p>
                    <input
                        className="field-space"
                        type="text"
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
                    defaultValue={system}
                    onSelect={(e: any) => setSystem(e.target.value)}>
                    <option>-Select-</option>
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
                    onSelect={(e: any) => setArea(e.target.value)}>
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
                    value="yes"
                    className="field-space"
                    checked={isReportable}
                    onChange={(e: any) => setIsReportable(e.target.checked)}
                />
                <span className="radio-label">Yes</span>
                <input
                    type="radio"
                    name="reportableCondition"
                    value="no"
                    className="right-radio"
                    checked={!isReportable}
                    onChange={(e: any) => setIsReportable(!e.target.checked)}
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
                    value="Yes"
                    className="field-space"
                    checked={isMorbidity}
                    onChange={(e: any) => setIsMorbidity(e.target.checked)}
                />
                <span className="radio-label">Yes</span>
                <input
                    type="radio"
                    name="mobilityReports"
                    value="No"
                    className="right-radio"
                    checked={!isMorbidity}
                    onChange={(e: any) => setIsMorbidity(!e.target.checked)}
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
                    value="Yes"
                    className="field-space"
                    checked={isAggregate}
                    onChange={(e: any) => setIsAggregate(e.target.checked)}
                />
                <span className="radio-label">Yes</span>
                <input
                    type="radio"
                    name="reportableAggregate"
                    value="No"
                    className="right-radio"
                    checked={!isAggregate}
                    onChange={(e: any) => setIsAggregate(!e.target.checked)}
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
                    value="Yes"
                    className="field-space"
                    checked={isTracingModule}
                    onChange={(e: any) => setIsTracingModule(e.target.checked)}
                />
                <span className="radio-label">Yes</span>
                <input
                    type="radio"
                    name="tracingModule"
                    value="No"
                    className="right-radio"
                    checked={!isTracingModule}
                    onChange={(e: any) => setIsTracingModule(!e.target.checked)}
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
                    onSelect={(e: any) => setFamily(e.target.value)}>
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
                    onSelect={(e: any) => setGroup(e.target.value)}>
                    <option>-Select-</option>
                    {buildOptions(groupOptions)}
                </select>
                <br></br>
            </div>
            <ClassicButton className="submit-btn" url="" onClick={handleSubmit} disabled={isValidationFailure}>
                Create & add condition
            </ClassicButton>
            <ClassicButton className="cancel-btn" url="" onClick={handleSubmit}>
                Cancel
            </ClassicButton>
        </div>
    );
};
