import React, { useContext, useEffect, useState } from 'react';
import './Concept.scss';
import { ConceptsContext } from '../../context/ConceptContext';
import { useConceptPI } from './useConceptAPI';
import { ConceptTable } from './ConceptTable';
import { UserContext } from '../../../../providers/UserContext';
import { Button, DatePicker, FormGroup, Grid, Icon, TextInput, Radio } from '@trussworks/react-uswds';
import { UpdateConceptRequest, ValueSetControllerService } from '../../generated';

const initConcept = {
    localCode: '',
    codesetName: '',
    display: '',
    description: '',
    conceptCode: '',
    messagingConceptName: '',
    codeSystem: '',
    effectiveFromTime: new Date().toLocaleString(),
    effectiveToTime: new Date().toLocaleString(),
    duration: 'always',
    status: true,
    cstatus: 'inactive'
};

export const Concept = () => {
    const { state } = useContext(UserContext);
    const { selectedConcept } = useContext(ConceptsContext);
    const authorization = `Bearer ${state.getToken()}`;

    const { searchQuery, sortBy, sortDirection, currentPage, pageSize } = useContext(ConceptsContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const [isShowFrom, setShowForm] = useState(false);
    const [isDelete, setIsDelete] = useState(false);
    const [concept, setConcept] = useState(initConcept);

    // @ts-ignore
    useEffect(async () => {
        setSummaries([]);
        const sort = sortBy ? sortBy.toLowerCase() + ',' + sortDirection : '';
        const content: any = await useConceptPI(authorization, '', sort);
        setSummaries(content);
        setTotalElements(content?.length);
    }, [searchQuery, currentPage, pageSize, sortBy, sortDirection]);

    useEffect(() => {
        setConcept(selectedConcept);
        selectedConcept.status && setShowForm(!isShowFrom);
    }, [selectedConcept]);

    const handleCancelFrom = () => {
        setShowForm(!isShowFrom);
        setConcept(initConcept);
    };
    const handleAddConceptFrom = () => {
        setShowForm(!isShowFrom);
    };
    const handleSaveConceptFrom = () => {
        const request: UpdateConceptRequest = {
            active: concept.status,
            displayName: concept.display,
            effectiveFromTime: concept.effectiveFromTime,
            effectiveToTime: concept.effectiveToTime,
            longName: concept.codesetName
        };
        if (concept?.status) return handleUpdateConcept(request);
        ValueSetControllerService.createValueSetUsingPost({
            authorization,
            request
        }).then((response: any) => {
            setShowForm(!isShowFrom);
            return response;
        });
    };
    const handleUpdateConcept = (request: any) => {
        ValueSetControllerService.updateConceptUsingPut({
            authorization,
            codeSetNm: concept.codesetName,
            conceptCode: concept.conceptCode,
            request
        }).then((response: any) => {
            setShowForm(!isShowFrom);
            return response;
        });
    };
    const handleDeleteConcept = () => {
        ValueSetControllerService.deleteValueSetUsingPut({
            authorization,
            codeSetNm: concept.codesetName
        }).then((response: any) => {
            // setShowForm(!isShowFrom);
            setIsDelete(false);
            return response;
        });
    };
    const handleConcept = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
        setConcept({ ...concept, [target.name]: target.value });
    };

    const newDate = new Date(concept?.effectiveFromTime) || new Date();
    const month = newDate.getMonth() + 1;
    const day = newDate.getDate();
    const year = newDate.getFullYear();
    const effectiveDate = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}/`;

    const renderConceptForm = (
        <div className="form-container">
            {isDelete && (
                <div className="usa-alert__body-delete">
                    <p>
                        <Icon.Warning className="margin-left-2" size={3} />
                        <span>Are you sure you want to delete the concept?</span>
                    </p>
                    <div>
                        <Button type="submit" className="line-btn" unstyled onClick={handleDeleteConcept}>
                            <span> Yes, Delete</span>
                        </Button>
                        <div className={'vertical-divider'} />
                        <Button type="submit" className="line-btn" unstyled onClick={() => setIsDelete(false)}>
                            <span> Cancel</span>
                        </Button>
                    </div>
                </div>
            )}
            <p className="instruction">Please fill out the forms to add new value set concept.</p>
            <div className="margin-left-15">
                <label className="fields-info">
                    All fields with <span className="mandatory-indicator">*</span> are required
                </label>
                <div className="margin-top-2">
                    <label>
                        UI Display name <span className="mandatory-indicator">*</span>
                    </label>
                    <TextInput
                        className="field-space"
                        type="text"
                        name="display"
                        id="iUdisplay"
                        style={{ border: '1px solid black' }}
                        value={concept.display}
                        onChange={handleConcept}
                    />
                </div>
                <Grid row className="inline-field">
                    <Grid tablet={{ col: true }}>
                        <div className="margin-right-2">
                            <label>
                                Local code <span className="mandatory-indicator">*</span>
                            </label>
                            <TextInput
                                className="field-space"
                                type="text"
                                name="localCode"
                                id="localCode"
                                style={{ border: '1px solid black' }}
                                value={concept.localCode}
                                onChange={handleConcept}
                            />
                        </div>
                    </Grid>
                    <Grid tablet={{ col: true }}>
                        <div className="width-48-p">
                            <label>
                                Concept code <span className="mandatory-indicator">*</span>
                            </label>
                            <TextInput
                                onChange={handleConcept}
                                className="field-space"
                                type="text"
                                id="conceptCode"
                                value={concept.conceptCode}
                                name="conceptCode"
                                style={{ border: '1px solid black' }}
                            />
                        </div>
                    </Grid>
                </Grid>
                <Grid row className="effective-date-field">
                    <Grid col={6} className="effective-radio">
                        <Radio
                            type="radio"
                            name="duration"
                            value="always"
                            id="eAlways"
                            checked={concept.duration === 'always'}
                            onChange={handleConcept}
                            label="Always Effective"
                        />
                        <Radio
                            id="eUntil"
                            name="duration"
                            value="until"
                            checked={concept.duration === 'until'}
                            onChange={handleConcept}
                            label="Effective Until"
                        />
                    </Grid>
                    <Grid col={5}>
                        <FormGroup error={false}>
                            <DatePicker
                                id="effectivDate"
                                name="effectivDate"
                                defaultValue={effectiveDate}
                                maxDate={new Date().toISOString()}
                            />
                        </FormGroup>
                    </Grid>
                </Grid>
                <Grid row className="effective-date-field">
                    <Grid col={6} className="effective-radio">
                        <Radio
                            name="cstatus"
                            value="active"
                            id="cstatus"
                            checked={concept.cstatus === 'active'}
                            onChange={handleConcept}
                            label="Active"
                        />
                        <Radio
                            id="incstatus"
                            name="cstatus"
                            value="inactive"
                            checked={concept.cstatus === 'inactive'}
                            onChange={handleConcept}
                            label="In active"
                        />
                    </Grid>
                </Grid>
                <div className=" ds-u-text-align--right footer-line-btn-block margin-bottom-1em">
                    {!isDelete && concept?.status ? (
                        <Button
                            type="submit"
                            className="margin-right-2 line-btn delete-ln-btn display-none"
                            unstyled
                            onClick={() => setIsDelete(true)}>
                            <Icon.Delete className="margin-right-2px" />
                            <span> Delete</span>
                        </Button>
                    ) : (
                        <div />
                    )}
                    <div />
                    <div>
                        <Button type="submit" className="cancel-btn margin-right-2" onClick={handleCancelFrom}>
                            {/* <Icon.Cancel className="margin-right-2px" />*/}
                            <span> Cancel</span>
                        </Button>
                        <Button type="submit" className="submit-btn" onClick={handleSaveConceptFrom}>
                            {/* <Icon.Check className="margin-right-2px" />*/}
                            <span> Add concept</span>
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
    const list = [
        { name: 'Value Set Type', value: 'Value set' },
        { name: 'Value Set code', value: 'Value set' },
        { name: 'Value Set name', value: 'Value set' },
        { name: 'Value Set description', value: 'Value set' }
    ];
    return (
        <div className="value_set_concept_container">
            <div>
                <ul className="list-details">
                    {list.map((ls, index) => (
                        <li key={index}>
                            <div className="title">{ls.name}</div>
                            <div className="details">{ls.value}</div>
                        </li>
                    ))}
                </ul>
            </div>
            <h3 className="main-header-title" data-testid="header-title">
                Value set concepts
            </h3>
            {isShowFrom ? (
                renderConceptForm
            ) : (
                <div>
                    {!summaries?.length && !searchQuery ? (
                        <p className="description">
                            No value set concept is displayed. Please click the button below to add new value set
                            concept.
                        </p>
                    ) : (
                        <div className="concept-local-library">
                            <div className="concept-local-library__container">
                                <div className="concept-local-library__table">
                                    <ConceptTable
                                        summaries={summaries}
                                        pages={{ currentPage, pageSize, totalElements }}
                                    />
                                </div>
                            </div>
                        </div>
                    )}
                    <div className="add-new-concept-container">
                        <Button type="submit" className="add-new-concept line-btn" onClick={handleAddConceptFrom}>
                            {/* <Icon.Add className="margin" />*/}
                            <span>Add New concept</span>
                        </Button>
                    </div>
                </div>
            )}
        </div>
    );
};
