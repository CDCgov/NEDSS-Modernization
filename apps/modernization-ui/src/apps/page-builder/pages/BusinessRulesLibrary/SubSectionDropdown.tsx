import { PagesSubSection } from 'apps/page-builder/generated';
import { fetchPageDetails } from 'apps/page-builder/services/pagesAPI';
import { authorization } from 'authorization';
import { MultiSelectInput } from 'components/selection/multi';
import { useEffect, useState } from 'react';

interface Props {
    pageId: string;
    selectedSubsectionIdentifiers: string[];
    onSelect: (subSections: PagesSubSection[]) => void;
}

const SubSectionsDropdown = ({ pageId, selectedSubsectionIdentifiers, onSelect }: Props) => {
    const [subSections, setSubSections] = useState<PagesSubSection[]>([]);
    const [selectedSubsections, setSelectedSubsections] = useState<string[]>([]);

    console.log('selectedQuestionidentifiers', selectedSubsectionIdentifiers);
    const handleSelectSubsection = (subSectionIds: string[]) => {
        console.log('subsectionIds on change', subSectionIds);
        const selected: PagesSubSection[] = subSections.filter((section) =>
            subSectionIds.includes(section.id.toString())
        );

        console.log('test', selected);
        setSelectedSubsections(selected.map((section) => section.id.toString()));
        onSelect(selected);
    };

    useEffect(() => {
        if (pageId) {
            fetchPageDetails(authorization(), Number(pageId)).then((data) => {
                console.log('data', data);
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
        if (selectedSubsectionIdentifiers.length) {
            // use the selectedSubsectionIdentifiers to search the subsections array and find the matching subsections
            const selected: PagesSubSection[] = subSections.filter((section) =>
                selectedSubsectionIdentifiers.includes(section.id.toString())
            );

            setSelectedSubsections(selected.map((section) => section.id.toString()));
        }
    }, [selectedSubsectionIdentifiers, subSections]);

    const options = subSections.map((section) => ({ name: section.name, value: section.id.toString() }));

    return <MultiSelectInput onChange={handleSelectSubsection} value={selectedSubsections} options={options} />;
};

export default SubSectionsDropdown;
