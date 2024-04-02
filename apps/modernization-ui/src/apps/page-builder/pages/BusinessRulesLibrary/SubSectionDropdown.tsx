import { RuleRequest, PagesSubSection } from 'apps/page-builder/generated';
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
    const [errorMessage, setErrorMessage] = useState<string>('');

    const form = useFormContext<RuleRequest>();

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
        if (selectedSubsections.length > 9) {
            setErrorMessage('You can not select more than 10 target fields.');
        } else {
            setErrorMessage('');
        }
    }, [selectedSubsections]);

    useEffect(() => {
        if (form.watch('targetIdentifiers').length) {
            // use the selectedSubsectionIdentifiers to search the subsections array and find the matching subsections
            const selected: PagesSubSection[] = subSections.filter((section) =>
                form.watch('targetIdentifiers').includes(section.questionIdentifier)
            );

            setSelectedSubsections(selected.map((section) => section.questionIdentifier));
        }
    }, [form.watch('targetIdentifiers'), subSections]);

    const options = subSections.map((subSection) => ({ name: subSection.name, value: subSection.questionIdentifier }));

    return (
        <MultiSelectInput
            onChange={handleSelectSubsection}
            value={selectedSubsections}
            options={options}
            error={errorMessage}
        />
    );
};

export default SubSectionsDropdown;
