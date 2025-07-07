import { ModalRef } from '@trussworks/react-uswds';
import { useAlert } from 'libs/alert';
import { PagesQuestion, PagesSection, PagesSubSection, SectionControllerService } from 'apps/page-builder/generated';
import { useRef, useState } from 'react';
import { ConfirmationModal } from '../../../../../../confirmation';
import { StatusModal } from '../../status/StatusModal';
import { usePageManagement } from '../../usePageManagement';
import { Section } from './Section';
import styles from './section.module.scss';

type Props = {
    sections: PagesSection[];
    onAddQuestion: (subsection: PagesSubSection) => void;
    onEditQuestion: (question: PagesQuestion) => void;
    onEditValueset: (valuesetName: string) => void;
    onChangeValueset: (question: PagesQuestion) => void;
    onGroupQuestion: (subsection: PagesSubSection) => void;
    onEditGroupedSubsection: (subsection: PagesSubSection) => void;
};

export const Sections = ({
    sections,
    onAddQuestion,
    onEditQuestion,
    onEditValueset,
    onChangeValueset,
    onGroupQuestion,
    onEditGroupedSubsection
}: Props) => {
    const { page, refresh } = usePageManagement();

    const sectionStatusModalRef = useRef<ModalRef>(null);

    const subSectionStatusModalRef = useRef<ModalRef>(null);

    const { showAlert } = useAlert();

    const deleteSectionModalRef = useRef<ModalRef>(null);
    const [selectedSectionToDelete, setSelectedSectionToDelete] = useState<PagesSection | undefined>(undefined);

    const handleSubsectionStatusModal = () => {
        subSectionStatusModalRef.current?.toggleModal(undefined, true);
    };

    const handleDeleteSection = (section: PagesSection) => {
        if (section.subSections.length > 0) {
            sectionStatusModalRef.current?.toggleModal(undefined, true);
        } else {
            setSelectedSectionToDelete(section);
            deleteSectionModalRef.current?.toggleModal(undefined, true);
        }
    };

    const deleteSection = () => {
        if (!selectedSectionToDelete) {
            return;
        }
        SectionControllerService.deleteSection({
            page: page.id,
            sectionId: selectedSectionToDelete.id
        }).then(() => {
            showAlert({
                message: `You have successfully deleted section "${selectedSectionToDelete.name}"`,
                type: `success`
            });
            refresh();
        });
    };

    return (
        <div className={styles.sections}>
            {sections.map((s, k) => (
                <Section
                    section={s}
                    key={k}
                    onAddQuestion={onAddQuestion}
                    onEditQuestion={onEditQuestion}
                    onDeleteSection={() => handleDeleteSection(s)}
                    onDeleteStatus={handleSubsectionStatusModal}
                    onEditValueset={onEditValueset}
                    onChangeValueset={onChangeValueset}
                    onGroupQuestion={onGroupQuestion}
                    onEditGroupedSubsection={onEditGroupedSubsection}
                />
            ))}
            <StatusModal
                modal={sectionStatusModalRef}
                messageHeader="Section cannot be deleted."
                title={'Warning'}
                message={
                    'This section contains elements (subsections and questions) inside it. Remove the contents first, and then the section can be deleted.'
                }
                onConfirm={() => {
                    sectionStatusModalRef.current?.toggleModal(undefined, false);
                }}
                confirmText="Okay"
            />
            <StatusModal
                modal={subSectionStatusModalRef}
                messageHeader="Subsection cannot be deleted."
                title={'Warning'}
                message={
                    'This subsection contains elements (questions) inside it. Remove the contents first, and then the subsection can be deleted.'
                }
                onConfirm={() => {
                    subSectionStatusModalRef.current?.toggleModal(undefined, false);
                }}
                confirmText="Okay"
            />

            <ConfirmationModal
                modal={deleteSectionModalRef}
                title="Warning"
                message="Are you sure you want to delete the section?"
                detail="Deleting this section cannot be undone. Are you sure you want to continue?"
                confirmText="Yes, delete"
                onConfirm={() => {
                    deleteSection();
                    deleteSectionModalRef.current?.toggleModal();
                }}
                onCancel={() => {
                    deleteSectionModalRef.current?.toggleModal();
                }}
            />
        </div>
    );
};
