import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

type Props = {
    filter: string;
    links: { text: string; href: string }[];
};

export const ReportSection = ({ filter, links }: Props) => {
    return <SystemManagementInfoCard id="report" title="Report" filter={filter} links={links} />;
};
