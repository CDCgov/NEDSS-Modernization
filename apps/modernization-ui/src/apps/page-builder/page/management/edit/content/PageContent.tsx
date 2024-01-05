import { PagesQuestion, PagesTab } from 'apps/page-builder/generated';
import { Sections } from '../section/Sections';
import { PageSideMenu } from './PageSideMenu';
import styles from './page-content.module.scss';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { EditStaticElement } from '../staticelement/EditStaticElement';
import { ModalRef } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { AddQuestionModal } from '../../../../components/Subsection/AddQuestionModal/AddQuestionModal';

type Props = {
    tab: PagesTab;
    refresh?: () => void;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const originalElecDocId = 1036;
const readOnlyPartId = 1030;

const staticTypes = [hyperlinkId, commentsReadOnlyId, lineSeparatorId, originalElecDocId, readOnlyPartId];

export const PageContent = ({ tab, refresh }: Props) => {
    const editStaticElementRef = useRef<ModalRef>(null);
    const [currentEditQuestion, setCurrentEditQuestion] = useState<PagesQuestion>();
    const [subsectionId, setSubsectionId] = useState(0);
    const addQuestionModalRef = useRef<ModalRef>(null);
    const handleAddSubsection = (section: number) => {
        console.log('add subsection not yet implemented', section);
    };

    const onCloseModal = () => {
        editStaticElementRef.current?.toggleModal(undefined, false);
        setCurrentEditQuestion(undefined);
    };

    const handleEditQuestion = (question: PagesQuestion) => {
        setCurrentEditQuestion(question);
    };

    useEffect(() => {
        if (staticTypes.includes(currentEditQuestion?.displayComponent!)) {
            console.log(currentEditQuestion);
            editStaticElementRef.current?.toggleModal(undefined, true);
        }
    }, [currentEditQuestion]);

    return (
        <div className={styles.pageContent}>
            <div className={styles.invisible} />
            <Sections
                sections={tab.sections ?? []}
                onAddSubsection={handleAddSubsection}
                onEditQuestion={handleEditQuestion}
                onAddQuestion={setSubsectionId}
                addQuestionModalRef={addQuestionModalRef}
            />
            <PageSideMenu />
            <ModalComponent
                modalRef={editStaticElementRef}
                modalHeading={'Edit static elements'}
                modalBody={
                    currentEditQuestion !== undefined && (
                        <EditStaticElement
                            question={currentEditQuestion!}
                            onCloseModal={onCloseModal}
                            refresh={refresh}
                        />
                    )
                }
            />
            <AddQuestionModal subsectionId={subsectionId} modalRef={addQuestionModalRef} />
        </div>
    );
};
