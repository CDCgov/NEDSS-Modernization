import React, { RefObject, useState, useEffect } from 'react';
import { ModalToggleButton, ModalRef, Checkbox } from '@trussworks/react-uswds';
import { useDataElementsContext, DataElement } from '../../context/DataElementsContext';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import styles from './add-blocking-criteria.module.scss';

type Props = {
    modalRef: RefObject<ModalRef>;
};

export const AddBlockingCriteria = ({ modalRef }: Props) => {
    const { dataElements } = useDataElementsContext();
    const { blockingCriteria, setBlockingCriteria, availableMethods } = usePatientMatchContext();

    // Initialize state to track selected fields
    const [selectedFields, setSelectedFields] = useState<string[]>([]);

    // Pre-select checkboxes for already selected blockingCriteria
    useEffect(() => {
        const selected = blockingCriteria.map((criteria) => criteria.field.name);
        setSelectedFields(selected); // Pre-check the ones that are already selected
    }, [blockingCriteria]);

    // Handle checkbox change
    const handleCheckboxChange = (fieldName: string, checked: boolean) => {
        setSelectedFields((prev) => (checked ? [...prev, fieldName] : prev.filter((name) => name !== fieldName)));
    };

    // Add selected dataElements as blocking criteria
    const addBlockingCriteria = () => {
        const newCriteria = selectedFields
            .filter((fieldName) => !blockingCriteria.some((criteria) => criteria.field.name === fieldName))
            .map((fieldName) => ({
                field: dataElements.find((element) => element.name === fieldName)!,
                method: availableMethods[0] // Default to 'Equals' method object
            }));

        // Update the context with new criteria
        setBlockingCriteria([...blockingCriteria, ...newCriteria]);

        // Close the modal
        modalRef.current?.toggleModal();
    };

    return (
        <div className={styles.addBlockingCriteria}>
            <div className={styles.addBlockingCriteriaContent}>
                {dataElements.map((dataElement: DataElement, index: number) => (
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
