import { PagesTab, PagesSection, PagesSubSection, PagesQuestion, PagesResponse } from 'apps/page-builder/generated';

export const findQuestionFromPage = (
    questionIdentifier: string,
    page: PagesResponse | undefined
): PagesQuestion | undefined => {
    page?.tabs?.map((tab: PagesTab) => {
        tab.sections?.map((section: PagesSection) => {
            section.subSections?.map((subsection: PagesSubSection) => {
                subsection.questions?.map((question: PagesQuestion) => {
                    if (question.question === questionIdentifier) {
                        console.log('did we');
                        console.log({ question });
                        return question;
                    }
                });
            });
        });
    });
    return undefined;
};
