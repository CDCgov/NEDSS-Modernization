import { PatientSearchResult } from 'generated/graphql/schema';
import styles from './PatientSearchResultListItem.module.scss';
import { Link } from 'react-router-dom';

type Props = {
    result: PatientSearchResult;
};

const PatientSearchResultListItem = ({ result }: Props) => {
    const { legalName, birthday, gender, shortId, phones, names, emails, addresses, identification } = result;
    const noData = 'No Data';
    const driversLicense = identification.find((id) => id.type === "Driver's license number");
    const ssn = identification.find((id) => id.type === 'Social Security');
    const first = legalName?.first ?? '';
    const last = legalName?.last ?? '';
    const firstName = first.charAt(0).toUpperCase() + first.slice(1).toLowerCase();
    const lastName = last.charAt(0).toUpperCase() + last.slice(1).toLowerCase();

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
                        to={`patient-profile/${result.patient}/summary`}>
                        {lastName && firstName ? `${lastName}, ${firstName}` : noData}
                    </Link>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="dob" className={styles.listItemLabel}>
                        DATE OF BIRTH
                    </label>
                    <span id="dob" className={styles.value}>
                        {birthday ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="sex" className={styles.listItemLabel}>
                        SEX
                    </label>
                    <span id="sex" className={styles.value}>
                        {gender ?? noData}
                    </span>
                </div>
                <div className={styles.listItemData}>
                    <label htmlFor="patientId" className={styles.listItemLabel}>
                        PATIENT ID
                    </label>
                    <span id="patientID" className={styles.value}>
                        {shortId}
                    </span>
                </div>
            </div>
            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="phone" className={styles.listItemLabel}>
                        PHONE
                    </label>
                    <br />
                    <span id="phone" className={styles.value}>
                        {phones[0] ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="otherNames" className={styles.listItemLabel}>
                        OTHER NAMES
                    </label>
                    <br />
                    <div id="otherNames">
                        {names.map((name, index) => (
                            <div key={index}>
                                <span className={styles.value}>
                                    {name.last}, {name.first}
                                </span>
                                <br />
                            </div>
                        )) ?? noData}
                    </div>
                </div>
            </div>
            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="email" className={styles.listItemLabel}>
                        Email
                    </label>
                    <br />
                    <span id="email" className={styles.value}>
                        {emails[0] ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="address" className={styles.listItemLabel}>
                        address
                    </label>
                    <br />
                    {addresses.length ? (
                        <>
                            <span id="email" className={styles.value}>
                                {addresses[0].address}
                            </span>
                            <br />
                            <span>{`${addresses[0].city}, ${addresses[0].state}, ${addresses[0].zipcode}`}</span>
                        </>
                    ) : (
                        noData
                    )}
                </div>
            </div>
            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="driversLicense" className={styles.listItemLabel}>
                        driver's license
                    </label>
                    <br />

                    <span id="driversLicense" className={styles.value}>
                        {driversLicense?.value ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="socialSecurityNumber" className={styles.listItemLabel}>
                        Social Security
                    </label>
                    <br />

                    <span id="socialSecurityNumber" className={styles.value}>
                        {ssn?.value ?? noData}
                    </span>
                </div>
            </div>
        </div>
    );
};

export { PatientSearchResultListItem };
