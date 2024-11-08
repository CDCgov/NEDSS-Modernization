import React, { RefObject, useState, useEffect } from 'react';
import { ModalToggleButton, ModalRef, Checkbox } from '@trussworks/react-uswds';
import { useDataElementsContext } from '../../context/DataElementsContext';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import styles from './add-blocking-criteria.module.scss';
import { DataElement } from 'apps/dedup-config/types';

type Props = {
    blockingModalRef: RefObject<ModalRef>;
};

export const AddBlockingCriteria = ({ blockingModalRef }: Props) => {
    const { dataElements } = useDataElementsContext();
    const { blockingCriteria, setBlockingCriteria, availableMethods } = usePatientMatchContext();
    const [selectedFields, setSelectedFields] = useState<string[]>([]);

    useEffect(() => {
        const selected = blockingCriteria.map((criteria) => criteria.field.name);
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

    const addBlockingCriteria = () => {
        if (!dataElements) {
            return;
        }
        const newCriteria = selectedFields
            .filter((fieldName) => !blockingCriteria.some((criteria) => criteria.field.name === fieldName))
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

        if (newCriteria.length > 0) {
            setBlockingCriteria([...blockingCriteria, ...newCriteria]);
        }

        const updatedCriteria = blockingCriteria.filter((criteria) => selectedFields.includes(criteria.field.name));

        setBlockingCriteria([...updatedCriteria, ...newCriteria]);
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
        </div>
    );
};
