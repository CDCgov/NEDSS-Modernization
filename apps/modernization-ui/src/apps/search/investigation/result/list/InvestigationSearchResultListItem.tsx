import styles from './InvestigationSearchResultListItem.module.scss';
import { Link } from 'react-router-dom';
import { Investigation, InvestigationPersonParticipation } from 'generated/graphql/schema';

type Props = {
    result: Investigation;
};

const InvestigationSearchResultListItem = ({ result }: Props) => {
    const noData = 'No data';

    const getPatient = (investigation: Investigation): InvestigationPersonParticipation | undefined | null => {
        return investigation.personParticipations?.find((p) => p?.typeCd === 'SubjOfPHC');
    };

    const patient = getPatient(result);
    const firstName = patient?.firstName ?? '';
    const lastName = patient?.lastName ?? '';
    const legalName = firstName && lastName ? `${firstName} ${lastName}` : 'No data';

    const getInvestigatorName = (investigation: Investigation): string | undefined => {
        const provider = investigation.personParticipations?.find((p) => p?.typeCd === 'InvestgrOfPHC');
        if (provider) {
            return `${provider.firstName} ${provider.lastName}`;
        } else {
            return undefined;
        }
    };

    const getInvestigationStatusString = (investigation: Investigation): string => {
        switch (investigation.investigationStatusCd) {
            case 'O':
                return 'OPEN';
            case 'C':
                return 'CLOSED';
            default:
                return investigation.investigationStatusCd ?? 'No Data';
        }
    };

    return (
        <div className={styles.listItem}>
            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="legalName" className={styles.listItemLabel}>
                        LEGAL NAME
                    </label>
                    <br />
                    <Link
                        id="legalName"
                        className={`${styles.value}, ${styles.name}`}
                        to={`/patient-profile/${patient?.shortId}/summary`}>
                        {legalName}
                    </Link>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="dob">Date of birth</label>
                    <span id="dob" className={styles.value}>
                        {patient?.birthTime ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="sex">SEX</label>
                    <span id="sex" className={styles.value}>
                        {patient?.currSexCd ?? 'No data'}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="patientId" className={styles.listItemLabel}>
                        Patient ID
                    </label>
                    <span id="patientId" className={styles.value}>
                        {patient?.shortId ?? noData}
                    </span>
                </div>
            </div>

            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="condition">Condition</label>
                    <br />
                    <Link
                        id="condition"
                        className={`${styles.value}, ${styles.name}`}
                        to={`/nbs/api/profile/${patient?.personParentUid}/investigation/${result.id}`}>
                        {result.cdDescTxt}
                    </Link>
                    <br />
                    <span>{result.localId}</span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="startDate">Start Date</label>
                    <span id="startDate" className={styles.value}>
                        {result.addTime ?? noData}
                    </span>
                </div>
            </div>

            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="jurisdiction" className={styles.listItemLabel}>
                        Jurisdiction
                    </label>
                    <span id="jurisdiction" className={styles.value}>
                        {result.jurisdictionCodeDescTxt ?? noData}
                    </span>
                </div>
                <div className={styles.listItemData}>
                    <label htmlFor="investigator" className={styles.listItemLabel}>
                        Investigator
                    </label>
                    <br />
                    <span id="investigator" className={styles.value}>
                        {getInvestigatorName(result) ?? noData}
                    </span>
                </div>
            </div>

            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="status" className={styles.listItemLabel}>
                        STATUS
                    </label>
                    <br />
                    <span
                        className={`${styles.value} ${result.investigationStatusCd === 'O' ? 'open' : ''}`}
                        id="status">
                        {getInvestigationStatusString(result) ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="notification" className={styles.listItemLabel}>
                        NOTIFICATION
                    </label>
                    <br />
                    <span className={styles.value} id="notification">
                        {result.notificationRecordStatusCd ?? noData}
                    </span>
                </div>
            </div>
        </div>
    );
};

export { InvestigationSearchResultListItem };
