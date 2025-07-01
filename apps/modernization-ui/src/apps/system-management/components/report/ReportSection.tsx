import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';
import { Permitted } from '../../../../libs/permission';
import { RedirectHome } from '../../../../routes';

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
        <Permitted permission={'REPORTADMIN-SYSTEM'} fallback={<RedirectHome />}>
            <SystemManagementInfoCard id="report" title="Report" filter={filter} links={reportLinks} />;
        </Permitted>
    );
};
