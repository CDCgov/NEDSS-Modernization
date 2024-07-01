import { LabReport } from 'generated/graphql/schema';

type Props = {
    result: LabReport;
};

const LaboratoryReportSearchResultListItem = ({ result }: Props) => <>{result.localId}</>;

export { LaboratoryReportSearchResultListItem };
