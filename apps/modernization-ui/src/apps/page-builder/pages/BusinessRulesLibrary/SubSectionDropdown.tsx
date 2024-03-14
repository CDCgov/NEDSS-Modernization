import { CreateRuleRequest, PagesSubSection } from 'apps/page-builder/generated';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { MultiSelectInput } from 'components/selection/multi';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';

interface Props {
    onSelect: (subSections: PagesSubSection[]) => void;
}

const SubSectionsDropdown = ({ onSelect }: Props) => {
    const [subSections, setSubSections] = useState<PagesSubSection[]>([]);
    const [selectedSubsections, setSelectedSubsections] = useState<string[]>([]);

    const form = useFormContext<CreateRuleRequest>();

    const { page } = useGetPageDetails();

    const handleSelectSubsection = (subSectionIds: string[]) => {
        const selected: PagesSubSection[] = subSections.filter((section) =>
            subSectionIds.includes(section.questionIdentifier)
        );
        setSelectedSubsections(selected.map((section) => section.questionIdentifier));
        onSelect(selected);
    };

    useEffect(() => {
        if (page) {
            const sections = page.tabs?.map((tab) => tab.sections).flat();
            const subs = sections
                ?.map((section) => section.subSections)
                .flat()
                .filter((sub) => sub.questions.length);

            setSubSections(subs ?? []);
        }
    }, [JSON.stringify(page)]);

    useEffect(() => {
        if (form.watch('targetIdentifiers').length) {
            // use the selectedSubsectionIdentifiers to search the subsections array and find the matching subsections
            const selected: PagesSubSection[] = subSections.filter((section) =>
                form.watch('targetIdentifiers').includes(section.questionIdentifier)
            );

            setSelectedSubsections(selected.map((section) => section.questionIdentifier));
        }
        console.log(selectedSubsections);
    }, [form.watch('targetIdentifiers'), subSections]);

    const options = subSections.map((subSection) => ({ name: subSection.name, value: subSection.questionIdentifier }));

    return <MultiSelectInput onChange={handleSelectSubsection} value={selectedSubsections} options={options} />;
};

export default SubSectionsDropdown;
