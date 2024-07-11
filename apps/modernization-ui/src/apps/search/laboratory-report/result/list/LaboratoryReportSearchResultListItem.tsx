import {
    Jurisdiction,
    LabReport,
    LabReportOrganizationParticipation,
    useFindAllJurisdictionsLazyQuery
} from 'generated/graphql/schema';
import styles from './LaboratoryReportSearchResultListItem.module.scss';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';

type Props = {
    result: LabReport;
};

const LaboratoryReportSearchResultListItem = ({ result }: Props) => {
    const [jurisdictions, setJurisdictions] = useState<any[] | null>(null);

    const noData = 'No data';
    const patient = result.personParticipations.find((p) => p.typeCd === 'PATSBJ');
    const firstName = `${patient?.firstName}` ?? null;
    const lastName = `${patient?.lastName}` ?? null;
    const legalName = firstName && lastName ? `${firstName} ${lastName}` : 'No data';
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
        // TODO - there could be multiple tests associated with one lab report. How to display them in UI
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
            setJurisdictions(results?.data?.findAllJurisdictions ?? []);
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
                    <Link id="legalName" className={`${styles.value}, ${styles.name}`} to={`/patient-profile/`}>
                        {legalName}
                    </Link>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="dob" className={styles.listItemLabel}>
                        Date of birth
                    </label>
                    <span id="dob" className={styles.listItemValue}>
                        {patient?.birthTime ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="sex" className={styles.listItemLabel}>
                        SEX
                    </label>
                    <span id="sex" className={styles.listItemValue}>
                        {patient?.currSexCd ?? 'No data'}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="patientId" className={styles.listItemLabel}>
                        Patient ID
                    </label>
                    <span id="patientId" className={styles.listItemValue}>
                        {patient?.shortId ?? noData}
                    </span>
                </div>
            </div>

            <div className={styles.listItemBox}>
                <div className={styles.listItemData}>
                    <label htmlFor="documentType" className={styles.listItemLabel}>
                        Document Type
                    </label>
                    <br />
                    <Link id="documentType" className={`${styles.value}, ${styles.name}`} to={`/patient-profile/`}>
                        Lab report
                    </Link>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="dateReceived" className={styles.listItemLabel}>
                        Date received
                    </label>
                    <span id="dateReceived" className={styles.listItemValue}>
                        {result.addTime ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="description" className={styles.listItemLabel}>
                        Description
                    </label>
                    <span id="description" className={styles.listItemValue}>
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
                    <span>{getReportingFacility(result)?.name ?? noData}</span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="orderingProvider" className={styles.listItemLabel}>
                        Ordering provider
                    </label>
                    <span id="orderingProvider" className={styles.listItemValue}>
                        {getOrderingProviderName(result) ?? noData}
                    </span>
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="jurisdiction" className={styles.listItemLabel}>
                        Jurisdiction
                    </label>
                    <span id="jurisdiction" className={styles.listItemValue}>
                        {getJurisdiction() ?? noData}
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
                        <span id="associatedTo" className={styles.listItemValue}>
                            {noData}
                        </span>
                    )}
                    {result.associatedInvestigations &&
                        result.associatedInvestigations?.length > 0 &&
                        result.associatedInvestigations?.map((i, index) => (
                            <div key={index} className={styles.listItemValue} id="associatedTo">
                                <span>{i?.localId}</span>
                                <br />
                                <span>{i?.cdDescTxt}</span>
                            </div>
                        ))}
                </div>

                <div className={styles.listItemData}>
                    <label htmlFor="localTo" className={styles.listItemLabel}>
                        Local to
                    </label>
                    <br />
                    <span className={styles.listItemValue} id="localTo">
                        {result.localId || noData}
                    </span>
                </div>
            </div>
        </div>
    );
};

export { LaboratoryReportSearchResultListItem };
