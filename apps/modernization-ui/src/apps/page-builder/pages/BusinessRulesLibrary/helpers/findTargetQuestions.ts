import { PagesQuestion, PagesTab, PagesSection, PagesSubSection, PagesResponse } from 'apps/page-builder/generated';

export const findTargetQuestion = (targets: string[], page: PagesResponse | undefined): PagesQuestion[] => {
    const targetQuestions: PagesQuestion[] = [];
    page?.tabs?.map((tab: PagesTab) => {
        tab.sections?.map((section: PagesSection) => {
            section.subSections?.map((subsection: PagesSubSection) => {
                subsection.questions?.map((question: PagesQuestion) => {
                    targets?.map((target) => {
                        if (target === question.question) {
                            targetQuestions.push(question);
                        }
                    });
                });
            });
        });
    });

    return targetQuestions;
};

export const findTargetSubsection = (targets: string[], page: PagesResponse | undefined): PagesSubSection[] => {
    const targetQuestions: PagesSubSection[] = [];
    page?.tabs?.map((tab: PagesTab) => {
        tab.sections?.map((section: PagesSection) => {
            section.subSections?.map((subsection: PagesSubSection) => {
                targets?.map((target) => {
                    if (target === subsection.questionIdentifier) {
                        targetQuestions.push(subsection);
                    }
                });
            });
        });
    });

    return targetQuestions;
};
