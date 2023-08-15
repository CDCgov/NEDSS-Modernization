import React, { useContext, useEffect, useState } from 'react';
import './Concept.scss';
import { ConceptsContext } from '../../context/ConceptContext';
import { useConceptPI } from './useConceptAPI';
import { ConceptTable } from './ConceptTable';
import { UserContext } from '../../../../providers/UserContext';
import { Button, DatePicker, FormGroup, Grid, Icon } from '@trussworks/react-uswds';
import { ValueSetControllerService } from '../../generated';

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
    status: ''
};

export const Concept = () => {
    const { state } = useContext(UserContext);
    const { selectedConcept } = useContext(ConceptsContext);
    const authorization = `Bearer ${state.getToken()}`;

    const { searchQuery, sortBy, sortDirection, currentPage, pageSize, setIsLoading } = useContext(ConceptsContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const [isShowFrom, setShowForm] = useState(false);
    const [concept, setConcept] = useState(initConcept);

    // @ts-ignore
    useEffect(async () => {
        setIsLoading(true);
        setSummaries([]);
        const sort = sortBy ? sortBy.toLowerCase() + ',' + sortDirection : '';
        const content: any = await useConceptPI(authorization, '', sort);
        setSummaries(content);
        setTotalElements(content?.length);
    }, [searchQuery, currentPage, pageSize, sortBy, sortDirection]);

    useEffect(() => {
        setConcept(selectedConcept);
        setShowForm(!isShowFrom);
    }, [selectedConcept]);

    const handleCancelFrom = () => {
        setShowForm(!isShowFrom);
        setConcept(initConcept);
    };
    const handleAddConceptFrom = () => {
        setShowForm(!isShowFrom);
    };
    const handleSaveConceptFrom = () => {
        const request = {
            active: concept.status,
            displayName: concept.display,
            effectiveFromTime: concept.effectiveFromTime,
            localCode: concept.localCode,
            effectiveToTime: concept.effectiveToTime,
            name: concept.codesetName
        };
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
            setShowForm(!isShowFrom);
            return response;
        });
    };
    const handleConcept = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
        setConcept({ ...concept, [target.name]: target.value });
    };
    // const effectiveDate = new Date(concept?.effectiveFromTime).toISOString();

    const renderConceptForm = (
        <div className="form-container">
            <div>
                <label>
                    UI Display name <span className="mandatory-indicator">*</span>
                </label>
                <input
                    className="field-space"
                    type="text"
                    name="display"
                    style={{ border: '1px solid black' }}
                    value={concept.display}
                    onInput={handleConcept}
                />
            </div>
            <Grid row className="inline-field">
                <Grid tablet={{ col: true }}>
                    <div className="margin-right-2">
                        <label>
                            Local code <span className="mandatory-indicator">*</span>
                        </label>
                        <input
                            className="field-space"
                            type="text"
                            name="localCode"
                            style={{ border: '1px solid black' }}
                            value={concept.localCode}
                            onInput={handleConcept}
                        />
                    </div>
                </Grid>
                <Grid tablet={{ col: true }}>
                    <div className="width-48-p">
                        <label>
                            Concept code <span className="mandatory-indicator">*</span>
                        </label>
                        <input
                            className="field-space"
                            type="text"
                            name="conceptCode"
                            style={{ border: '1px solid black' }}
                            value={concept.conceptCode}
                            onInput={handleConcept}
                        />
                    </div>
                </Grid>
            </Grid>
            <Grid row className="effective-date-field">
                <Grid col={7}>
                    <input
                        type="radio"
                        name="duration"
                        value="always"
                        id="eAlways"
                        className="field-space"
                        checked={concept.duration === 'always'}
                        onChange={handleConcept}
                    />
                    <label htmlFor="rdLOCAL" className="radio-label">
                        Always Effective
                    </label>
                    <input
                        type="radio"
                        id="eUntil"
                        name="duration"
                        value="until"
                        className="right-radio"
                        checked={concept.duration === 'until'}
                        onChange={handleConcept}
                    />
                    <label htmlFor="eUntil" className="radio-label">
                        Effective Until
                    </label>
                </Grid>
                <Grid col={5}>
                    <FormGroup error={false}>
                        <DatePicker
                            id="effectivDate"
                            name="effectivDate"
                            // defaultValue={effectiveDate}
                            maxDate={new Date().toISOString()}
                        />
                    </FormGroup>
                </Grid>
            </Grid>
            <div className=" ds-u-text-align--right footer-line-btn-block margin-bottom-1em">
                {concept?.status ? (
                    <Button
                        type="submit"
                        className="margin-right-2 line-btn delete-ln-btn"
                        unstyled
                        onClick={handleDeleteConcept}>
                        <Icon.Delete className="margin-right-2px" />
                        <span> Delete</span>
                    </Button>
                ) : (
                    <div />
                )}
                <div>
                    <Button type="submit" className="margin-right-2 line-btn" unstyled onClick={handleCancelFrom}>
                        <Icon.Cancel className="margin-right-2px" />
                        <span> Cancel</span>
                    </Button>
                    <Button type="submit" className="line-btn" unstyled onClick={handleSaveConceptFrom}>
                        <Icon.Check className="margin-right-2px" />
                        <span> Add concept</span>
                    </Button>
                </div>
            </div>
        </div>
    );

    return (
        <div className="value_set_concept_container">
            {isShowFrom ? (
                renderConceptForm
            ) : (
                <div>
                    <div className="concept-local-library">
                        <div className="concept-local-library__container">
                            <div className="concept-local-library__table">
                                <ConceptTable summaries={summaries} pages={{ currentPage, pageSize, totalElements }} />
                            </div>
                        </div>
                    </div>
                    <Button type="submit" className="line-btn" unstyled onClick={handleAddConceptFrom}>
                        <Icon.Add className="margin" />
                        <span>Add value set concept</span>
                    </Button>
                </div>
            )}
        </div>
    );
};
