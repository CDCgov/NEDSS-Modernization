import React, { RefObject, useState, useEffect } from 'react';
import { ModalToggleButton, ModalRef, Checkbox } from '@trussworks/react-uswds';
import { useDataElementsContext } from '../../context/DataElementsContext';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import styles from './add-blocking-criteria.module.scss';
import { DataElement } from 'apps/dedup-config/types';

type Props = {
    modalRef: RefObject<ModalRef>;
};

export const AddBlockingCriteria = ({ modalRef }: Props) => {
    const { dataElements } = useDataElementsContext();
    const { blockingCriteria, setBlockingCriteria, availableMethods } = usePatientMatchContext();

    const [selectedFields, setSelectedFields] = useState<string[]>([]);

    useEffect(() => {
        const selected = blockingCriteria.map((criteria) => criteria.field.name);
        setSelectedFields(selected);
    }, [blockingCriteria]);

    const handleCheckboxChange = (fieldName: string, checked: boolean) => {
        setSelectedFields((prev) => (checked ? [...prev, fieldName] : prev.filter((name) => name !== fieldName)));
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

        modalRef.current?.toggleModal();
    };

    return (
        <div className={styles.addBlockingCriteria}>
            <div className={styles.addBlockingCriteriaContent}>
                {dataElements &&
                    dataElements.length > 0 &&
                    dataElements.map((dataElement: DataElement, index: number) => (
                        <Checkbox
                            key={index}
                            name={dataElement.name}
                            label={dataElement.label}
                            id={dataElement.name}
                            tile
                            disabled={!dataElement.active}
                            checked={selectedFields.includes(dataElement.name)} // Check if this field is already selected
                            onChange={(e) => handleCheckboxChange(dataElement.name, e.target.checked)}
                        />
                    ))}
            </div>
            <div className={styles.addBlockingCriteriaFooter}>
                <ModalToggleButton type="button" closer outline modalRef={modalRef}>
                    Cancel
                </ModalToggleButton>
                <ModalToggleButton type="button" onClick={addBlockingCriteria} closer modalRef={modalRef}>
                    Add blocking criteria
                </ModalToggleButton>
            </div>
        </div>
    );
};
