import { ModalToggleButton, Icon } from '@trussworks/react-uswds';
import styles from '../PatientMatchConfigurationPage/patient-match-form.module.scss';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import { ModalRef } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { AddMatchingCriteria } from './AddMatchingCriteria';
import { MatchingCriteriaRow } from './MatchingCriteriaRow';

export const MatchingCriteriaSection = () => {
    const matchingModalRef = useRef<ModalRef>(null);
    const { blockingCriteria, matchingCriteria, setTotalLogOdds } = usePatientMatchContext();
    const [logOddsSum, setLogOddsSum] = useState<number>(0);

    useEffect(() => {
        const sum = matchingCriteria
            .map((criteria) => criteria.field.logOdds || 0)
            .reduce((acc, logOdds) => acc + logOdds, 0);

        setLogOddsSum(sum);
        setTotalLogOdds(sum);
    }, [matchingCriteria]);

    return (
        <div className={`${styles.criteria} ${blockingCriteria.length < 1 ? styles.disabled : ''}`}>
            <div className={styles.criteriaHeadingContainer}>
                <h4>Matching criteria</h4>
                <p>Include records that meet all these conditions</p>
            </div>
            <div className={styles.criteriaContentContainer}>
                {matchingCriteria.length > 0 ? (
                    matchingCriteria.map((criteria, index) => <MatchingCriteriaRow key={index} criteria={criteria} />)
                ) : (
                    <p className={styles.criteriaRequest}>Please add a matching attribute to continue</p>
                )}
                {matchingCriteria.length > 0 && (
                    <div className={styles.totalOdds}>
                        <p>Log odds total &nbsp; {logOddsSum}</p>
                    </div>
                )}
                <ModalToggleButton
                    unstyled
                    type={'button'}
                    modalRef={matchingModalRef}
                    disabled={blockingCriteria.length < 1}>
                    <Icon.Add />
                    Add matching criteria
                </ModalToggleButton>
            </div>
            <ModalComponent
                modalRef={matchingModalRef}
                size="medium"
                modalHeading={'Add static element'}
                modalBody={<AddMatchingCriteria matchingModalRef={matchingModalRef} />}
            />
        </div>
    );
};
