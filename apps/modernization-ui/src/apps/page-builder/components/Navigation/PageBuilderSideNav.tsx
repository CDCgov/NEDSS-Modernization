import { NavEntry, SideNavigation } from 'design-system/side-nav/SideNavigation';

export const PageBuilderSideNav = () => (
    <SideNavigation title="Data entry" className="side-nav">
        <NavEntry name="Page library" active />
        <NavEntry
            name="Condition library"
            href="/nbs/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&initLoad=true"
            external
        />
        <NavEntry
            name="Question library"
            href="/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&initLoad=true"
            external
        />
        <NavEntry
            name="Template library"
            href="/nbs/ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&initLoad=true"
            external
        />
        <NavEntry name="Value set library" href="/nbs/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true" external />
    </SideNavigation>
);
