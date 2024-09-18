import React, { useEffect, useState } from 'react';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import { useDataElementsContext } from '../../context/DataElementsContext';
import { ModalToggleButton, Checkbox } from '@trussworks/react-uswds';
import { DataElement } from '../../types';
import { availableMethods } from '../../context/PatientMatchContext';
import { ModalRef } from '@trussworks/react-uswds';
import styles from './add-matching-criteria.module.scss';

type Props = {
    matchingModalRef: React.RefObject<ModalRef>;
};

export const AddMatchingCriteria = ({ matchingModalRef }: Props) => {
    const { dataElements } = useDataElementsContext();
    const { blockingCriteria, matchingCriteria, setMatchingCriteria } = usePatientMatchContext();

    const [selectedFields, setSelectedFields] = useState<string[]>([]);

    useEffect(() => {
        const selected = matchingCriteria.map((criteria) => criteria.field.name);
        setSelectedFields(selected);
    }, [matchingCriteria]);

    const handleCheckboxChange = (fieldName: string, checked: boolean) => {
        setSelectedFields((prev) => {
            if (checked) {
                return [...prev, fieldName];
            } else {
                return prev.filter((name) => name !== fieldName);
            }
        });
    };

    const addMatchingCriteria = () => {
        if (!dataElements) {
            return;
        }

        const newCriteria = selectedFields
            .filter((fieldName) => !matchingCriteria.some((criteria) => criteria.field.name === fieldName))
            .map((fieldName) => {
                const field = dataElements.find((element) => element.name === fieldName);
                if (!field) {
                    return null;
                }
                return {
                    field: field,
                    method: availableMethods[0]
                };
            })
            .filter((criteria) => criteria !== null);

        const updatedCriteria = matchingCriteria.filter((criteria) => selectedFields.includes(criteria.field.name));

        setMatchingCriteria([...updatedCriteria, ...newCriteria]);

        matchingModalRef.current?.toggleModal();
    };

    return (
        <div className={styles.addMatchingCriteria}>
            <div className={styles.addMatchingCriteriaContent}>
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
                                        id={dataElement.name + index}
                                        tile
                                        disabled={
                                            !dataElement.active ||
                                            blockingCriteria.some(
                                                (criteria) => criteria.field.name === dataElement.name
                                            )
                                        }
                                        checked={selectedFields.includes(dataElement.name)}
                                        onChange={(e) => handleCheckboxChange(dataElement.name, e.target.checked)}
                                    />
                                ))}
                            </div>
                        </div>
                    ))}
            </div>
            <div className={styles.addMatchingCriteriaFooter}>
                <ModalToggleButton type="button" closer outline modalRef={matchingModalRef}>
                    Cancel
                </ModalToggleButton>
                <ModalToggleButton type="button" onClick={addMatchingCriteria} closer modalRef={matchingModalRef}>
                    Add matching criteria
                </ModalToggleButton>
            </div>
        </div>
    );
};
