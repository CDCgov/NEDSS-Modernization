import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';
import { Permitted } from '../../../../libs/permission';
import { RedirectHome } from '../../../../routes';

export const pageLinks = [
    {
        text: 'Manage conditions',
        href: '/nbs/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&initLoad=true'
    },
    {
        text: 'Manage pages',
        href: '/nbs/ManagePage.do?method=list&amp;initLoad=true'
    },
    {
        text: 'Manage questions',
        href: '/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&initLoad=true'
    },
    {
        text: 'Manage templates',
        href: '/nbs/ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&initLoad=true'
    },
    {
        text: 'Manage value sets',
        href: '/nbs/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true'
    }
];

type Props = {
    filter: string;
};

export const PageSection = ({ filter }: Props) => {
    return (
        <Permitted permission={'LDFADMINISTRATION-SYSTEM'} fallback={<RedirectHome />}>
            <SystemManagementInfoCard id="page" title="Page" filter={filter} links={pageLinks} />
        </Permitted>
    );
};
