import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';
import { Permitted } from '../../../../libs/permission';

const reportLinks = [
    {
        text: 'Manage data sources',
        href: '/nbs/ListDataSource.do'
    },
    {
        text: 'Manage reports',
        href: '/nbs/ListReport.do'
    },
    {
        text: 'Manage report sections',
        href: '/nbs/ListReportSections.do'
    }
];

type Props = {
    filter: string;
};

export const ReportSection = ({ filter }: Props) => {
    return (
        <Permitted permission={'REPORTADMIN-SYSTEM'}>
            <SystemManagementInfoCard id="report" title="Report" filter={filter} links={reportLinks} />
        </Permitted>
    );
};
