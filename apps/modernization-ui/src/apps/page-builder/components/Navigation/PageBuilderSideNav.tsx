import { SideNavigation, NavigationEntry, LinkEntry } from 'components/SideNavigation/SideNavigation';

import './PageBuilderSideNav.scss';

export const PageBuilderSideNav = () => (
    <SideNavigation className="page-builder-side-nav" title="Page Management">
        <NavigationEntry path="/page-builder/pages">Page library</NavigationEntry>
        <LinkEntry href="/nbs/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&initLoad=true">
            Condition library
        </LinkEntry>
        <LinkEntry href="/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&initLoad=true">
            Question library
        </LinkEntry>
        <LinkEntry href="/nbs/ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&initLoad=true">
            Template library
        </LinkEntry>
        <LinkEntry href="/nbs/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true">Value set library</LinkEntry>
    </SideNavigation>
);
