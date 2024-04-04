import { RuleRequest, PagesSubSection, PagesQuestion } from 'apps/page-builder/generated';
import { useGetTargetSubsections } from 'apps/page-builder/hooks/useGetTargetSubsections';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { MultiSelectInput } from 'components/selection/multi';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';

interface Props {
    sourceQuestion?: PagesQuestion;
    onSelect: (subSections: PagesSubSection[]) => void;
}

const SubSectionsDropdown = ({ onSelect, sourceQuestion }: Props) => {
    const [subSections, setSubSections] = useState<PagesSubSection[]>([]);
    const [selectedSubsections, setSelectedSubsections] = useState<string[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>('');

    const form = useFormContext<RuleRequest>();

    const { response, fetch } = useGetTargetSubsections();

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
            if (form.watch('targetIdentifiers')) {
                fetch(page.id ?? 0, {
                    orderNbr: sourceQuestion?.order,
                    targetSubsections: form.watch('targetIdentifiers')
                });
            } else {
                fetch(page.id ?? 0, { orderNbr: sourceQuestion?.order });
            }
        }
    }, [JSON.stringify(page)]);

    useEffect(() => {
        if (response) {
            setSubSections(response);
        }
    }, [JSON.stringify(response)]);

    useEffect(() => {
        if (selectedSubsections.length > 10) {
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
