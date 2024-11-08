import React, { RefObject, useState, useEffect } from 'react';
import { ModalToggleButton, ModalRef, Checkbox } from '@trussworks/react-uswds';
import { useDataElementsContext } from '../../context/DataElementsContext';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import styles from './add-blocking-criteria.module.scss';
import { DataElement } from 'apps/dedup-config/types';
import PatientMatchForm from '../PatientMatchConfigurationPage/PatientMatchForm';

type Props = {
    blockingModalRef: RefObject<ModalRef>;
};

export const AddBlockingCriteria = ({ blockingModalRef }: Props) => {
    const { dataElements } = useDataElementsContext();
    const { blockingCriteria, availableMethods } = usePatientMatchContext();
    const [selectedFields, setSelectedFields] = useState<string[]>([]);

    useEffect(() => {
        console.log('Blocking Criteria after update:', blockingCriteria);
        const selected = blockingCriteria.map((criteria) => criteria.field.name);
        console.log('Blocking Criteria in context:', blockingCriteria);
        console.log('Selected Fields updated:', selectedFields);
        setSelectedFields(selected);
    }, [blockingCriteria]);

    const handleCheckboxChange = (fieldName: string, checked: boolean) => {
        setSelectedFields((prev) => {
            if (checked) {
                return [...prev, fieldName];
            } else {
                return prev.filter((name) => name !== fieldName);
            }
        });
    };

    const addBlockingCriteria = async () => {
        if (!dataElements) {
            return;
        }

        const selectedFields = blockingCriteria.map((criteria) => criteria.field.name);

        // Prepare new blocking criteria based on selected fields
        const newCriteria = selectedFields
            .filter((fieldName) => !blockingCriteria.some((criteria) => criteria.field.name === fieldName))
            .map((fieldName) => {
                const field = dataElements.find((element) => element.name === fieldName);
                console.log('Field found for', fieldName, ':', field); // Debug log
                return field ? { field: field, method: availableMethods[0] } : null;
            })
            .filter((criteria) => criteria !== null);
        const updatedCriteria = blockingCriteria.filter((criteria) => selectedFields.includes(criteria.field.name));

        // Combine updated and new criteria
        const allCriteria = [...updatedCriteria, ...newCriteria];

        // Here, you prepare the request body to send to the API
        const requestBody = {
            blockingCriteria: allCriteria,
            selectedFields: selectedFields
        };

        console.log('Request body to send:', requestBody);

        try {
            const response = await fetch('/blocking-criteria', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (response.ok) {
                console.log('Pass configuration saved successfully');
            } else {
                console.error('Failed to save pass configuration');
            }
        } catch (error) {
            console.error('Error saving pass configuration:', error);
        }

        // Close the modal
        blockingModalRef.current?.toggleModal();
    };

    return (
        <div className={styles.addBlockingCriteria}>
            <div className={styles.addBlockingCriteriaContent}>
                {dataElements &&
                    dataElements.length > 0 &&
                    Object.entries(
                        dataElements.reduce(
                            (acc, dataElement) => {
                                if (!acc[dataElement.category]) {
                                    acc[dataElement.category] = [];
                                }
                                if (dataElement.active) {
                                    acc[dataElement.category].push(dataElement);
                                }
                                return acc;
                            },
                            {} as { [key: string]: DataElement[] }
                        )
                    ).map(([category, elements], index) => (
                        <div key={index} className={styles.category}>
                            <h3>{category}</h3>
                            <div className={styles.categoryGroup}>
                                {elements.map((dataElement, index) => (
                                    <Checkbox
                                        key={index}
                                        name={dataElement.name}
                                        label={dataElement.label}
                                        id={dataElement.name}
                                        tile
                                        disabled={!dataElement.active}
                                        checked={selectedFields.includes(dataElement.name)}
                                        onChange={(e) => handleCheckboxChange(dataElement.name, e.target.checked)}
                                    />
                                ))}
                            </div>
                        </div>
                    ))}
            </div>
            <div className={styles.addBlockingCriteriaFooter}>
                <ModalToggleButton type="button" closer outline modalRef={blockingModalRef}>
                    Cancel
                </ModalToggleButton>
                <ModalToggleButton type="button" onClick={addBlockingCriteria} closer modalRef={blockingModalRef}>
                    Add blocking criteria
                </ModalToggleButton>
            </div>
            <PatientMatchForm
                selectedFields={selectedFields}
                passConfiguration={{
                    name: 'Default Name',
                    description: 'Default Description',
                    active: false,
                    blockingCriteria: [],
                    matchingCriteria: []
                }}
                onSaveConfiguration={() => {}}
                onDeleteConfiguration={() => true}
                onCancel={() => {}}
                isAdding={false}
            />
        </div>
    );
};
