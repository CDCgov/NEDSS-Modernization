import { PagesTab } from 'apps/page-builder/generated';
import { Sections } from '../section/Sections';
import { PageSideMenu } from './PageSideMenu';
import styles from './page-content.module.scss';
import { AddQuestionModal } from '../../../../components/Subsection/AddQuestionModal/AddQuestionModal';
import { useRef, useState } from 'react';
import { ModalRef } from '@trussworks/react-uswds';

type Props = {
    tab: PagesTab;
};
export const PageContent = ({ tab }: Props) => {
    const [subsectionId, setSubsectionId] = useState(0);
    const addQuestionModalRef = useRef<ModalRef>(null);
    const handleAddSubsection = (section: number) => {
        console.log('add subsection not yet implemented', section);
    };

    return (
        <div className={styles.pageContent}>
            <div className={styles.invisible} />
            <Sections
                sections={tab.sections ?? []}
                onAddSubsection={handleAddSubsection}
                onAddQuestion={setSubsectionId}
                addQuestionModalRef={addQuestionModalRef}
            />
            <PageSideMenu />
            <AddQuestionModal subsectionId={subsectionId} modalRef={addQuestionModalRef} />
        </div>
    );
};
