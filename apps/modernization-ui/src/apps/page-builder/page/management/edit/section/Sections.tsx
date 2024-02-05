import { PagesQuestion, PagesSection, SectionControllerService } from 'apps/page-builder/generated';
import { useRef } from 'react';
import styles from './section.module.scss';
import { ModalRef } from '@trussworks/react-uswds';
import { authorization } from 'authorization';
import { usePageManagement } from '../../usePageManagement';
import { StatusModal } from '../../status/StatusModal';
import { Section } from './Section';
import { useAlert } from 'alert';

type Props = {
    sections: PagesSection[];
    onAddQuestion: (subsection: number) => void;
    onEditQuestion: (question: PagesQuestion) => void;
};

export const Sections = ({ sections, onAddQuestion, onEditQuestion }: Props) => {
    const { page, refresh } = usePageManagement();

    const sectionStatusModalRef = useRef<ModalRef>(null);

    const subSectionStatusModalRef = useRef<ModalRef>(null);

    const { showAlert } = useAlert();

    const handleSubsectionStatusModal = () => {
        subSectionStatusModalRef.current?.toggleModal(undefined, true);
    };

    const handleDeleteSection = (section: PagesSection) => {
        if (section.subSections.length > 0) {
            sectionStatusModalRef.current?.toggleModal(undefined, true);
        } else {
            SectionControllerService.deleteSectionUsingDelete({
                authorization: authorization(),
                page: page.id,
                sectionId: section.id
            }).then(() => {
                showAlert({ message: `You have successfully deleted section "${section.name}"`, type: `success` });
                refresh();
            });
        }
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
        </div>
    );
};
