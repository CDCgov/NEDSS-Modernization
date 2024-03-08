import { PagesQuestion, PagesSubSection } from 'apps/page-builder/generated';
import { fetchPageDetails } from 'apps/page-builder/services/pagesAPI';
import { authorization } from 'authorization';
import { MultiSelectInput } from 'components/selection/multi';
import { useEffect, useState } from 'react';

interface Props {
    pageId: string;
    selectedQuestionIdentifiers: string[];
    onSelect: (questions: PagesQuestion[]) => void;
}

const SubSectionsDropdown = ({ pageId, selectedQuestionIdentifiers, onSelect }: Props) => {
    const [subSections, setSubSections] = useState<PagesSubSection[]>([]);
    const [selectedSubsections, setSelectedSubsections] = useState<string[]>([]);

    const handleSelectSubsection = (subSectionIds: string[]) => {
        const selected = subSections.filter((section) => subSectionIds.includes(section.id.toString()));
        const questions = selected.map((section) => section.questions).flat();

        setSelectedSubsections(selected.map((section) => section.id.toString()));
        onSelect(questions);
    };

    useEffect(() => {
        if (pageId) {
            fetchPageDetails(authorization(), Number(pageId)).then((data) => {
                const sections = data.tabs?.map((tab) => tab.sections).flat();
                const subs = sections
                    ?.map((section) => section.subSections)
                    .flat()
                    .filter((sub) => sub.questions.length);

                setSubSections(subs ?? []);
            });
        }
    }, [pageId]);

    useEffect(() => {
        if (selectedQuestionIdentifiers.length) {
            // search subsections and return all that have a question with an identifier in the selectedQuestionIdentifiers array
            const selectedSubs = subSections.filter((section) =>
                section.questions.find((question) => selectedQuestionIdentifiers.includes(question?.question ?? ''))
            );

            setSelectedSubsections(selectedSubs.map((section) => section.id.toString()));
        }
    }, [selectedQuestionIdentifiers, subSections]);

    const options = subSections.map((section) => ({ name: section.name, value: section.id.toString() }));

    return <MultiSelectInput onChange={handleSelectSubsection} value={selectedSubsections} options={options} />;
};

export default SubSectionsDropdown;
