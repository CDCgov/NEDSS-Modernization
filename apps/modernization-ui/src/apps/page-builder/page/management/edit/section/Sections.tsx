import { PagesQuestion, PagesSection, SectionControllerService } from 'apps/page-builder/generated';
import { useRef } from 'react';

import styles from './section.module.scss';
import { ModalRef } from '@trussworks/react-uswds';
import { authorization } from 'authorization';
import { usePageManagement } from '../../usePageManagement';
import { useAlert } from 'alert';
import { StatusModal } from '../../status/StatusModal';
import { Section } from './Section';

type Props = {
    sections: PagesSection[];
    onAddQuestion: (subsection: number) => void;
    onAddSubsection: (section: number) => void;
    onEditQuestion: (question: PagesQuestion) => void;
    onEditSection: (section: PagesSection) => void;
};

export const Sections = ({ sections, onAddSubsection, onAddQuestion, onEditQuestion, onEditSection }: Props) => {
    const { page, fetch } = usePageManagement();

    const statusModalRef = useRef<ModalRef>(null);

    const { showAlert } = useAlert();

    const handleDeleteSection = (section: PagesSection) => {
        if (section.subSections.length > 0) {
            statusModalRef.current?.toggleModal(undefined, true);
        } else {
            SectionControllerService.deleteSectionUsingDelete({
                authorization: authorization(),
                page: page.id,
                sectionId: section.id
            }).then(() => {
                showAlert({ message: `You've successfully deleted section!`, type: `success` });
                fetch(page.id);
            });
        }
    };

    return (
        <div className={styles.sections}>
            {sections.map((s, k) => (
                <Section
                    section={s}
                    key={k}
                    onAddSubsection={onAddSubsection}
                    onAddQuestion={onAddQuestion}
                    onEditQuestion={onEditQuestion}
                    handleEditSection={() => onEditSection(s)}
                    handleDeleteSection={() => handleDeleteSection(s)}
                />
            ))}
            <StatusModal
                modal={statusModalRef}
                messageHeader="Section cannot be deleted."
                title={'Warning'}
                message={
                    'This section contains elements (subsections and questions) inside it. Remove the contents first, and then the section can be deleted.'
                }
                onConfirm={() => {
                    statusModalRef.current?.toggleModal(undefined, false);
                }}
                confirmText="Okay"
            />
        </div>
    );
};
