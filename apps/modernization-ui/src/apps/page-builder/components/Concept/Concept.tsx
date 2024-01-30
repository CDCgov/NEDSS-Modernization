import React, { useContext, useEffect, useState, ReactNode } from 'react';
import './Concept.scss';
import { ConceptsContext } from '../../context/ConceptContext';
import { useConceptAPI } from './useConceptAPI';
import { ConceptTable } from './ConceptTable';
import { Button, Icon } from '@trussworks/react-uswds';
import { ValueSet, ValueSetControllerService } from '../../generated';
import { authorization } from 'authorization';
import { EditConcept } from './EditConcept/EditConcept';
import { CreateConcept } from './CreateConcept/CreateConcept';
import { AlertBanner } from '../AlertBanner/AlertBanner';

type Props = {
    valueset: ValueSet;
    onEdit?: () => void;
};
interface CodeSystemOption {
    label: string;
    value: string;
}

export const Concept = ({ valueset, onEdit }: Props) => {
    const { selectedConcept } = useContext(ConceptsContext);
    const { searchQuery, sortDirection, currentPage, pageSize } = useContext(ConceptsContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const [showForm, setShowForm] = useState(false);
    const [codeSystemOptionList, setCodeSystemOptionList] = useState<CodeSystemOption[]>([]);
    const [editMode, setEditMode] = useState(false);
    const [alertMessage, setAlertMessage] = useState<AlertMessage | undefined>(undefined);
    type AlertMessage = { type: 'error' | 'success'; message: string | ReactNode; expiration: number | undefined };

    const fetchContent = async () => {
        try {
            setSummaries([]);
            const content: any = await useConceptAPI(authorization(), valueset!.valueSetCode!);
            setSummaries(content);
            setTotalElements(content?.length);
            fetchCodeSystemOptions();
        } catch (error) {
            console.error('Error fetching content', error);
        }
    };

    useEffect(() => {
        fetchContent();
    }, [searchQuery, currentPage, pageSize, sortDirection, valueset!.valueSetCode]);

    useEffect(() => {
        selectedConcept.status && setShowForm(!showForm);
    }, [selectedConcept]);

    useEffect(() => {
        setTimeout(() => setAlertMessage(undefined), 5000);
    }, [alertMessage]);

    const updateCallback = () => {
        fetchContent();
        setShowForm(false);
    };

    const fetchCodeSystemOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: authorization(),
            codeSetNm: 'CODE_SYSTEM'
        }).then((response: any) => {
            const data = response || [];
            const codeSystemOptionList: any = [];
            data.map((each: { display: string; conceptCode: string }) => {
                codeSystemOptionList.push({ label: each.display, value: each.conceptCode });
            });
            setCodeSystemOptionList(codeSystemOptionList);
        });
    };

    const list = [
        { name: 'Value Set Type', value: valueset?.valueSetTypeCd },
        { name: 'Value Set code', value: valueset?.valueSetCode },
        { name: 'Value Set name', value: valueset?.valueSetNm },
        { name: 'Value Set description', value: valueset?.codeSetDescTxt }
    ];
    return (
        <div className={`concept ${editMode ? 'edit' : ''}`}>
            <div className="concept-header">
                <h3>Value set details</h3>
                <Icon.Edit onClick={onEdit} style={{ cursor: 'pointer' }} size={3} />
            </div>
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
                Add value set concept
            </h3>
            {alertMessage && (
                <AlertBanner type={alertMessage.type} expiration={alertMessage.expiration}>
                    {alertMessage.message}
                </AlertBanner>
            )}
            {showForm ? (
                editMode ? (
                    <EditConcept
                        valueset={valueset}
                        selectedConcept={selectedConcept}
                        codeSystemOptionList={codeSystemOptionList}
                        setShowForm={() => setShowForm(false)}
                        updateCallback={updateCallback}
                        setAlertMessage={setAlertMessage}
                        closeForm={() => setShowForm(false)}
                    />
                ) : (
                    <CreateConcept
                        valueset={valueset}
                        codeSystemOptionList={codeSystemOptionList}
                        setShowForm={() => setShowForm(false)}
                        updateCallback={updateCallback}
                        setAlertMessage={setAlertMessage}
                        closeForm={() => setShowForm(false)}
                    />
                )
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
                                        setEditMode={() => setEditMode(true)}
                                    />
                                </div>
                            </div>
                        </div>
                    )}
                    <div className="concept__footer">
                        {!summaries || summaries.length < 1 ? (
                            <p>
                                No value set concept is displayed. Please click the button below to add new value set
                                concept.
                            </p>
                        ) : (
                            <p>Can't find what you're looking for? Click the link below to add a new concept.</p>
                        )}
                        <Button
                            type="submit"
                            onClick={() => {
                                setEditMode(false);
                                setShowForm(!showForm);
                            }}>
                            <span>Add New concept</span>
                        </Button>
                    </div>
                </div>
            )}
        </div>
    );
};
