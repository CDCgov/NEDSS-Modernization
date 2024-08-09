import { LabReport } from 'generated/graphql/schema';
import { internalizeDate } from 'date';
import { Selectable } from 'options';
import { ClassicLink } from 'classic';
import {
    getPatient,
    getOrderingProviderName,
    getReportingFacility,
    getDescription,
    getAssociatedInvestigations,
    getPatientName
} from 'apps/search/laboratory-report/result';
import { Result, ResultItem, ResultItemGroup } from 'apps/search/layout/result/list';

type Props = {
    result: LabReport;
    jurisdictionResolver: (value: string) => Selectable | undefined;
};

const LaboratoryReportSearchResultListItem = ({ result, jurisdictionResolver }: Props) => {
    const patient = getPatient(result);

    return (
        <Result>
            <ResultItemGroup>
                <ResultItem label="Legal name" orientation="vertical">
                    {getPatientName(result)}
                </ResultItem>
                <ResultItem label="Date of birth">{internalizeDate(patient?.birthTime)}</ResultItem>
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
                    {getReportingFacility(result)}
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
                    {getAssociatedInvestigations(result)}
                </ResultItem>
                <ResultItem label="Local ID" orientation="vertical">
                    {result.localId}
                </ResultItem>
            </ResultItemGroup>
        </Result>
    );
};

export { LaboratoryReportSearchResultListItem };
