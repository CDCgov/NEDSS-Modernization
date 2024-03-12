import {
    PagesResponse,
    PagesTab,
    PagesSection,
    PagesSubSection,
    PagesQuestion,
    Rule
} from 'apps/page-builder/generated';
import { useGetPageDetails } from 'apps/page-builder/page/management';

const codedDisplayType = [1024, 1025, 1013, 1007, 1031, 1027, 1028];

const isCoded = (question: PagesQuestion) => codedDisplayType.includes(question.displayComponent ?? 0);

const isDate = (question: PagesQuestion) => question.dataType === 'DATE' || question.dataType === 'DATETIME';

export const handleSourceCases = (question: PagesQuestion[], ruleFunction?: string) => {
    if (ruleFunction === Rule.ruleFunction.DATE_COMPARE) {
        const filteredList = question.filter(isDate);
        return filteredList;
    } else {
        const filteredList = question.filter(isCoded);
        return filteredList;
    }
};

export const filterPage = (ruleFunction?: string) => {
    const { page } = useGetPageDetails();

    if (page) {
        const result: PagesResponse = {
            id: page.id,
            description: page.description,
            name: page.name,
            root: page.root,
            rules: page.rules,
            status: page.status,
            tabs: []
        };
        page.tabs?.forEach((tab: PagesTab) => {
            const newTab: PagesTab = {
                id: tab.id,
                name: tab.name,
                sections: [],
                visible: tab.visible,
                order: tab.order
            };

            tab.sections.forEach((section: PagesSection) => {
                const newSection: PagesSection = {
                    id: section.id,
                    name: section.name,
                    order: section.order,
                    subSections: [],
                    visible: section.visible
                };

                section.subSections.forEach((subsection: PagesSubSection) => {
                    if (subsection.questions.length > 0) {
                        if (handleSourceCases(subsection.questions, ruleFunction).length > 0) {
                            newSection?.subSections.push(subsection);
                        }
                    }
                });

                if (newSection) {
                    newSection.subSections.length > 0 && newTab?.sections.push(newSection);
                }
            });
            if (newTab) {
                newTab.sections.length > 0 && result?.tabs?.push(newTab);
            }
        });
        return result;
    }
    return undefined;
};
