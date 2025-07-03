import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useAlert } from 'libs/alert';
import { MoreOptions } from 'apps/page-builder/components/MoreOptions/MoreOptions';
import { PagesSubSection, SubSectionControllerService } from 'apps/page-builder/generated';
import { AddStaticElement } from 'apps/page-builder/page/management/edit/staticelement/AddStaticElement';
import { Icon as IconComponent } from 'components/Icon/Icon';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { ConfirmationModal } from 'confirmation';
import { useRef, useState } from 'react';
import { usePageManagement } from '../../usePageManagement';
import styles from './subsection.module.scss';
import { staticElementTypes } from '../staticelement/EditStaticElement';

type Props = {
    subsection: PagesSubSection;
    onAddQuestion: () => void;
    isExpanded: boolean;
    onExpandedChange: (isExpanded: boolean) => void;
    onDeleteSubsection: () => void;
    onEditSubsection: () => void;
    onGroupQuestion: (subsection: PagesSubSection) => void;
};

export const SubsectionHeader = ({
    subsection,
    isExpanded,
    onAddQuestion,
    onExpandedChange,
    onDeleteSubsection,
    onEditSubsection,
    onGroupQuestion
}: Props) => {
    const { page, refresh } = usePageManagement();
    const ungroupSubsectionModalRef = useRef<ModalRef>(null);
    const addStaticElementModalRef = useRef<ModalRef>(null);
    const { showAlert } = useAlert();
    const [closeOptions, setCloseOptions] = useState(false);

    const handleUngroup = () => {
        SubSectionControllerService.unGroupSubSection({
            page: page.id,
            subSectionId: subsection.id
        })
            .then(() => {
                showAlert({
                    type: 'success',
                    title: 'Ungrouped',
                    message: `You've successfully ungrouped ${subsection.name}`
                });
                refresh();
            })
            .catch((error) => {
                if (error instanceof Error) {
                    console.error(error);
                    showAlert({
                        type: 'error',
                        title: 'error',
                        message: error.message
                    });
                } else {
                    console.error(error);
                    showAlert({
                        type: 'error',
                        title: 'error',
                        message: 'An unknown error occurred'
                    });
                }
            });
    };

    const closeThenAct = (action: (subsection: PagesSubSection) => void) => {
        setCloseOptions(true);
        action(subsection);
    };

    return (
        <div className={`${styles.header} ${subsection.isGrouped !== false ? styles.grouped : ''} subsectionHeader`}>
            <div className={styles.info}>
                {subsection.isGrouped !== false ? (
                    <div data-testid="questionGroupIndicator" className={styles.indicator}>
                        R
                    </div>
                ) : null}
                <div>
                    <div className={styles.name}>
                        <h4>{subsection.name}</h4>
                    </div>
                    <div className={styles.count}>
                        {`${subsection.questions?.length} question${subsection.questions?.length > 1 ? 's' : ''}`}
                    </div>
                </div>
            </div>
            <div className={styles.buttons}>
                <Button type="button" className="add-btn addQuestionBtn" outline onClick={onAddQuestion}>
                    Add question
                </Button>
                <MoreOptions
                    header={<Icon.MoreVert role="menu" size={4} onClick={() => setCloseOptions(false)} />}
                    className={`subsectionOptionsWithGrouped-${subsection.isGrouped ? 'grouped' : subsection.questions.length > 2 ? 'ungrouped' : ''}`}
                    close={closeOptions}>
                    <Button
                        type="button"
                        data-testid="editSubsectionOption"
                        onClick={() => closeThenAct(onEditSubsection)}>
                        <Icon.Edit size={3} /> Edit subsection
                    </Button>
                    {subsection.isGrouped &&
                        page.status !== 'Published' &&
                        subsection.questions.every((question) => question.isPublished === false) && (
                            <ModalToggleButton
                                type="button"
                                modalRef={ungroupSubsectionModalRef}
                                data-testid="ungroupQuestionsOption"
                                onClick={() => setCloseOptions(true)}>
                                <IconComponent name={'group'} size={'s'} /> Ungroup questions
                            </ModalToggleButton>
                        )}
                    {!subsection.isGrouped &&
                        page.status !== 'Published' &&
                        subsection.questions.every(
                            (question) =>
                                question.isPublished === false &&
                                !staticElementTypes.includes(question.displayComponent ?? 0)
                        ) && (
                            <>
                                {subsection.isGroupable && subsection.questions.length > 0 && (
                                    <Button
                                        type="button"
                                        data-testid="groupQuestionsOption"
                                        onClick={() => closeThenAct(onGroupQuestion)}>
                                        <IconComponent name={'group'} size={'s'} /> Group questions
                                    </Button>
                                )}
                            </>
                        )}
                    <ModalToggleButton type="button" modalRef={addStaticElementModalRef}>
                        <Icon.Add size={3} /> Add static element
                    </ModalToggleButton>
                    <Button
                        type="button"
                        onClick={() => {
                            closeThenAct(onDeleteSubsection);
                        }}
                        className="deleteSubsectionBtn">
                        <Icon.Delete size={3} /> Delete subsection
                    </Button>
                </MoreOptions>
                {isExpanded ? (
                    <Icon.ExpandLess className="iconExpandLess" size={4} onClick={() => onExpandedChange(false)} />
                ) : (
                    <Icon.ExpandMore size={4} onClick={() => onExpandedChange(true)} />
                )}
            </div>
            <ConfirmationModal
                modal={ungroupSubsectionModalRef}
                title="Warning"
                message="You have indicated that you would like to ungroup the repeating block questions in the Tribal Affiliation Repeating Block questions."
                detail="Select Ungroup or Cancel to return to Edit Page."
                confirmText="Ungroup"
                onConfirm={() => {
                    handleUngroup();
                    ungroupSubsectionModalRef.current?.toggleModal();
                }}
                onCancel={() => {
                    ungroupSubsectionModalRef.current?.toggleModal();
                }}
            />
            <ModalComponent
                modalRef={addStaticElementModalRef}
                modalHeading={'Add static element'}
                modalBody={<AddStaticElement modalRef={addStaticElementModalRef} subsectionId={subsection.id} />}
            />
        </div>
    );
};
