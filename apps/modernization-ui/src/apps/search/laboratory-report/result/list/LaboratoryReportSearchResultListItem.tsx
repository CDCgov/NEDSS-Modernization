import { Link } from 'react-router-dom';
import { LabReport, LabReportOrganizationParticipation } from 'generated/graphql/schema';
import { NoData } from 'components/NoData';
import { internalizeDate } from 'date';
import { Selectable } from 'options';
import { ClassicLink } from 'classic';
import { Result, ResultItem, ResultItemGroup } from 'apps/search/layout/result/list';

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

type Props = {
    result: LabReport;
    jurisdictionResolver: (value: string) => Selectable | undefined;
};

const LaboratoryReportSearchResultListItem = ({ result, jurisdictionResolver }: Props) => {
    const patient = result.personParticipations.find((p) => p.typeCd === 'PATSBJ');
    const firstName = patient?.firstName ?? '';
    const lastName = patient?.lastName ?? '';
    const legalName = firstName && lastName ? `${firstName} ${lastName}` : <NoData />;

    return (
        <Result>
            <ResultItemGroup>
                <ResultItem label="Legal name" orientation="vertical">
                    <Link id="legalName" to={`/patient-profile/${patient?.shortId}/summary`}>
                        {legalName}
                    </Link>
                </ResultItem>
                <ResultItem label="Date of birth">{patient?.birthTime}</ResultItem>
                <ResultItem label="Sex">{patient?.currSexCd}</ResultItem>
                <ResultItem label="Patient ID">{patient?.shortId}</ResultItem>
            </ResultItemGroup>

            <ResultItemGroup>
                <ResultItem label="Document Type" orientation="vertical">
                    <ClassicLink
                        id="documentType"
                        url={`/nbs/api/profile/${patient?.personParentUid}/report/lab/${result.id}`}>
                        Lab report
                    </ClassicLink>
                </ResultItem>
                <ResultItem label="Date received" orientation="vertical">
                    {internalizeDate(result.addTime)}
                </ResultItem>
                <ResultItem label="Description" orientation="vertical">
                    {getDescription(result)}
                </ResultItem>
            </ResultItemGroup>

            <ResultItemGroup>
                <ResultItem label="Reporting Facility" orientation="vertical">
                    {getReportingFacility(result)?.name}
                </ResultItem>
                <ResultItem label="Ordering Provider" orientation="vertical">
                    {getOrderingProviderName(result)}
                </ResultItem>
                <ResultItem label="Jurisdiction" orientation="vertical">
                    {jurisdictionResolver(String(result.jurisdictionCd))?.name}
                </ResultItem>
            </ResultItemGroup>

            <ResultItemGroup>
                <ResultItem label="Associated to" orientation="vertical">
                    {result.associatedInvestigations &&
                        result.associatedInvestigations?.length > 0 &&
                        result.associatedInvestigations
                            ?.map((investigation) => `${investigation?.localId}\n${investigation?.cdDescTxt}\n`)
                            .join('\n')}
                </ResultItem>
                <ResultItem label="Local ID" orientation="vertical">
                    {result.localId}
                </ResultItem>
            </ResultItemGroup>
        </Result>
    );
};

export { LaboratoryReportSearchResultListItem };
