import { PagesQuestion, PagesTab } from 'apps/page-builder/generated';
import { Sections } from '../section/Sections';
import { PageSideMenu } from './PageSideMenu';
import styles from './page-content.module.scss';
import { EditStaticElement } from '../staticelement/EditStaticElement';
import { Button, ModalRef } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { AddQuestionModal } from '../../../../components/Subsection/AddQuestionModal/AddQuestionModal';
import { CreateQuestion } from '../../../../components/CreateQuestion/CreateQuestion';
import { Heading } from '../../../../../../components/heading';
import { ManageSection } from '../section/manage/ManageSection';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { Icon } from '@trussworks/react-uswds';
import { AddSection } from '../section/manage/AddSection';

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

const questionTypes = [1001, 1006, 1007, 1008, 1009, 1013, 1017, 1019, 1024, 1025, 1026, 1027, 1028, 1029, 1031, 1032];

export const PageContent = ({ tab, refresh }: Props) => {
    const editStaticElementRef = useRef<ModalRef>(null);
    const [currentEditQuestion, setCurrentEditQuestion] = useState<PagesQuestion>();
    const [subsectionId, setSubsectionId] = useState(0);
    const addQuestionModalRef = useRef<ModalRef>(null);
    const editQuestionModalRef = useRef<ModalRef>(null);
    const manageSectionModalRef = useRef<ModalRef>(null);
    const addSectionModalRef = useRef<ModalRef>(null);
    const [currentTab, setCurrentTab] = useState<PagesTab>();

    const handleAddSubsection = (section: number) => {
        console.log('add subsection not yet implemented', section);
    };

    const onCloseModal = () => {
        if (staticTypes.includes(currentEditQuestion?.displayComponent!)) {
            editStaticElementRef.current?.toggleModal(undefined, false);
        } else {
            editQuestionModalRef.current?.toggleModal(undefined, false);
        }
        setCurrentEditQuestion(undefined);
    };

    const handleEditQuestion = (question: PagesQuestion) => {
        setCurrentEditQuestion(question);
    };

    useEffect(() => {
        if (staticTypes.includes(currentEditQuestion?.displayComponent!)) {
            editStaticElementRef.current?.toggleModal(undefined, true);
        } else if (questionTypes.includes(currentEditQuestion?.displayComponent!)) {
            editQuestionModalRef.current?.toggleModal(undefined, true);
        }
    }, [currentEditQuestion]);

    const handleManageSection = () => {
        setCurrentTab(tab);
    };

    useEffect(() => {
        if (currentTab !== undefined) {
            manageSectionModalRef.current?.toggleModal(undefined, true);
        }
    }, [currentTab]);

    const onCloseManageSectionModal = () => {
        manageSectionModalRef.current?.toggleModal(undefined, false);
        setCurrentTab(undefined);
    };

    const handleAddSection = () => {
        addSectionModalRef.current?.toggleModal(undefined, true);
    };

    const closeAddSection = () => {
        addSectionModalRef.current?.toggleModal(undefined, false);
    };

    return (
        <div className={styles.pageContent}>
            <div className={styles.invisible} />
            <Sections
                sections={tab.sections ?? []}
                onAddSubsection={handleAddSubsection}
                onEditQuestion={handleEditQuestion}
                onAddQuestion={setSubsectionId}
                handleManageSection={handleManageSection}
                addQuestionModalRef={addQuestionModalRef}
            />
            <PageSideMenu onAddSection={handleAddSection} />
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
            <ModalComponent
                isLarge
                modalRef={editQuestionModalRef}
                closer
                modalHeading={
                    <div className="edit-question-header">
                        <Heading level={2}>Edit question</Heading>
                    </div>
                }
                modalBody={<CreateQuestion onCloseModal={onCloseModal} question={currentEditQuestion} />}
            />
            <ModalComponent
                modalRef={manageSectionModalRef}
                modalHeading={
                    <>
                        <Heading level={2}>Manage sections</Heading>
                        <Button type="button" onClick={handleAddSection} outline className={styles.addSectionBtn}>
                            <Icon.Add size={3} />
                            Add sections
                        </Button>
                    </>
                }
                modalBody={
                    currentTab !== undefined && (
                        <ManageSection tab={currentTab!} refresh={refresh} onCloseModal={onCloseManageSectionModal} />
                    )
                }
            />
            <ModalComponent
                modalRef={addSectionModalRef}
                modalHeading={'Add a section'}
                modalBody={<AddSection onCloseModal={closeAddSection} refresh={refresh} tabId={currentTab?.id} />}
            />
        </div>
    );
};
