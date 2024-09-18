import { ModalToggleButton, Icon } from '@trussworks/react-uswds';
import styles from '../PatientMatchConfigurationPage/patient-match-form.module.scss';
import { ModalRef } from '@trussworks/react-uswds';
import { useRef } from 'react';
import { AddBlockingCriteria } from './AddBlockingCriteria';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import { BlockingCriteriaRow } from './BlockingCriteriaRow';

export const BlockingCriteriaSection = () => {
    const modalRef = useRef<ModalRef>(null);
    const { blockingCriteria } = usePatientMatchContext();

    return (
        <div className={styles.criteria}>
            <div className={styles.criteriaHeadingContainer}>
                <h4>Blocking criteria</h4>
                <p>Include records that meet all these conditions</p>
            </div>
            <div className={styles.criteriaContentContainer}>
                {blockingCriteria.length > 0 ? (
                    blockingCriteria.map((criteria, index) => <BlockingCriteriaRow key={index} criteria={criteria} />)
                ) : (
                    <p className={styles.criteriaRequest}>Please add blocking criteria to get started</p>
                )}
                <ModalToggleButton type="button" unstyled modalRef={modalRef}>
                    <Icon.Add />
                    Add blocking criteria
                </ModalToggleButton>
            </div>
            <ModalComponent
                modalRef={modalRef}
                size="medium"
                modalHeading={'Add an attribute for blocking'}
                modalBody={<AddBlockingCriteria blockingModalRef={modalRef} />}
            />
        </div>
    );
};
