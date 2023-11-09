import { SideNavigation } from 'components/Navigation/SideNavigation';
import { NavigationEntry } from 'components/Navigation/NavigationEntry';

export const PageBuilderNavigation = () => {
    return (
        <SideNavigation
            title="Page Management"
            active={1}
            entries={[
                <NavigationEntry key={1} label="Page library" href="/page-builder/manage/pages" useNav />,
                <NavigationEntry
                    key={2}
                    label="Condition library"
                    href="/nbs/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&initLoad=true"
                />,
                <NavigationEntry
                    key={3}
                    label="Question library"
                    href="/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&initLoad=true"
                />,
                <NavigationEntry
                    key={4}
                    label="Template library"
                    href="/nbs/ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&initLoad=true"
                />,
                <NavigationEntry
                    key={5}
                    label="Value set library"
                    href="/nbs/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true"
                />
            ]}
        />
    );
};
