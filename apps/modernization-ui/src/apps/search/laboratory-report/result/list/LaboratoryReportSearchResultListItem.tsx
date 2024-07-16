import {
    Jurisdiction,
    LabReport,
    LabReportOrganizationParticipation,
    useFindAllJurisdictionsLazyQuery
} from 'generated/graphql/schema';
import styles from './LaboratoryReportSearchResultListItem.module.scss';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { NoData } from 'components/NoData';
import { internalizeDate } from 'date';

type Props = {
    result: LabReport;
};

const LaboratoryReportSearchResultListItem = ({ result }: Props) => {
    const [jurisdictions, setJurisdictions] = useState<Jurisdiction[]>([] as Jurisdiction[]);
    const patient = result.personParticipations.find((p) => p.typeCd === 'PATSBJ');
    const firstName = patient?.firstName ?? '';
    const lastName = patient?.lastName ?? '';
    const legalName = firstName && lastName ? `${firstName} ${lastName}` : <NoData />;
    const [getJurisdictions] = useFindAllJurisdictionsLazyQuery();

    const getOrderingProviderName = (labReport: LabReport): string | undefined => {
        const provider = labReport.personParticipations.find((p) => p.typeCd === 'ORD' && p.personCd === 'PRV');
        if (provider) {
            return `${provider.firstName} ${provider.lastName}`;
        } else {
            return undefined;
        }
    };

    const getReportingFacility = (labReport: LabReport): LabReportOrganizationParticipation | undefined => {
        return labReport.organizationParticipations.find((o) => o?.typeCd === 'AUT');
    };

    const getDescription = (labReport: LabReport): string | undefined => {
        const observation = labReport.observations?.find((o) => o?.altCd && o?.displayName && o?.cdDescTxt);

        return observation && `${observation.cdDescTxt} = ${observation.displayName}`;
    };

    const getJurisdiction = () => {
        if (result.jurisdictionCd) {
            return jurisdictions?.find((j: Jurisdiction) => j.id == result.jurisdictionCd.toString());
        }
    };

    useEffect(() => {
        getJurisdictions().then((results) => {
            if (results?.data?.findAllJurisdictions?.length) {
                results.data.findAllJurisdictions?.forEach((j) => {
                    if (j) {
                        setJurisdictions([...jurisdictions, j]);
                    }
                });
            }
        });
    }, []);

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
                        {patient?.birthTime ?? <NoData />}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="sex">SEX</label>
                    <span id="sex" className={styles.value}>
                        {patient?.currSexCd ?? <NoData />}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="patientId" className={styles.listItemLabel}>
                        Patient ID
                    </label>
                    <span id="patientId" className={styles.value}>
                        {patient?.shortId ?? <NoData />}
                    </span>
                </div>
            </div>

            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="documentType">Document Type</label>
                    <br />
                    <Link
                        id="documentType"
                        className={`${styles.value}, ${styles.name}`}
                        to={`/nbs/api/profile/${patient?.personParentUid}/report/lab/${result.id}`}>
                        Lab report
                    </Link>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="dateReceived">Date received</label>
                    <br />
                    <span id="dateReceived" className={styles.value}>
                        {internalizeDate(result.addTime) ?? <NoData />}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="description">Description</label>
                    <br />
                    <span id="description" className={styles.value}>
                        {getDescription(result)}
                    </span>
                </div>
            </div>

            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="reportingFacility" className={styles.listItemLabel}>
                        Reporting Facility
                    </label>
                    <br />
                    <span>{getReportingFacility(result)?.name ?? <NoData />}</span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="orderingProvider" className={styles.listItemLabel}>
                        Ordering provider
                    </label>
                    <br />
                    <span id="orderingProvider" className={styles.value}>
                        {getOrderingProviderName(result) ?? <NoData />}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="jurisdiction" className={styles.listItemLabel}>
                        Jurisdiction
                    </label>
                    <br />
                    <span id="jurisdiction" className={styles.value}>
                        {getJurisdiction()?.toString() ?? <NoData />}
                    </span>
                </div>
            </div>

            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="associatedTo" className={styles.listItemLabel}>
                        Associated to
                    </label>
                    <br />
                    {(!result.associatedInvestigations || result.associatedInvestigations.length == 0) && (
                        <span id="associatedTo" className={styles.value}>
                            {<NoData />}
                        </span>
                    )}
                    {result.associatedInvestigations &&
                        result.associatedInvestigations?.length > 0 &&
                        result.associatedInvestigations?.map((i, index) => (
                            <div key={index} className={styles.value} id="associatedTo">
                                <span>{i?.localId}</span>
                                <br />
                                <span>{i?.cdDescTxt}</span>
                            </div>
                        ))}
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="localTo" className={styles.listItemLabel}>
                        Local ID
                    </label>
                    <br />
                    <span className={styles.value} id="localTo">
                        {result.localId || <NoData />}
                    </span>
                </div>
            </div>
        </div>
    );
};

export { LaboratoryReportSearchResultListItem };
