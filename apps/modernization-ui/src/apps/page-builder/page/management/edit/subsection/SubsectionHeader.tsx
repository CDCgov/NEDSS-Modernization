import styles from './subsection.module.scss';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { MoreOptions } from 'apps/page-builder/components/MoreOptions/MoreOptions';
import { Icon as IconComponent } from 'components/Icon/Icon';
import { useRef } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { AddStaticElement } from 'apps/page-builder/page/management/edit/staticelement/AddStaticElement';
import { PagesSubSection, SubSectionControllerService } from 'apps/page-builder/generated';
import { GroupQuestion } from '../question/GroupQuestion/GroupQuestion';
import { ConfirmationModal } from 'confirmation';
import { authorization } from 'authorization';
import { usePageManagement } from '../../usePageManagement';
import { useAlert } from 'alert';

type Props = {
    subsection: PagesSubSection;
    onAddQuestion: () => void;
    isExpanded: boolean;
    onExpandedChange: (isExpanded: boolean) => void;
    onDeleteSubsection: () => void;
    onEditSubsection: () => void;
};

export const SubsectionHeader = ({
    subsection,
    isExpanded,
    onAddQuestion,
    onExpandedChange,
    onDeleteSubsection,
    onEditSubsection
}: Props) => {
    const { page, refresh } = usePageManagement();
    const groupSubsectionModalRef = useRef<ModalRef>(null);
    const ungroupSubsectionModalRef = useRef<ModalRef>(null);
    const addStaticElementModalRef = useRef<ModalRef>(null);
    const { showAlert } = useAlert();

    const handleUngroup = () => {
        try {
            SubSectionControllerService.unGroupSubSectionUsingGet({
                authorization: authorization(),
                page: page.id,
                subSectionId: subsection.id
            }).then(() => {
                showAlert({
                    type: 'success',
                    header: 'Ungrouped',
                    message: `You've successfully ungrouped ${subsection.name}`
                });
                refresh();
            });
        } catch (error) {
            if (error instanceof Error) {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: error.message
                });
            } else {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: 'An unknown error occurred'
                });
            }
        }
    };

    return (
        <div className={`${styles.header} ${subsection.isGrouped !== false ? styles.grouped : ''}`}>
            <div className={styles.info}>
                {subsection.isGrouped !== false ? <div className={styles.indicator}>R</div> : null}
                <div>
                    <div className={styles.name}>{subsection.name}</div>
                    <div className={styles.count}>
                        {`${subsection.questions?.length} question${subsection.questions?.length > 1 ? 's' : ''}`}
                    </div>
                </div>
            </div>
            <div className={styles.buttons}>
                <Button type="button" className="add-btn" outline onClick={onAddQuestion}>
                    Add question
                </Button>
                <MoreOptions header={<Icon.MoreVert size={4} />}>
                    <Button type="button" onClick={onEditSubsection}>
                        <Icon.Edit size={3} /> Edit subsection
                    </Button>
                    {subsection.isGrouped ? (
                        <ModalToggleButton type="button" modalRef={ungroupSubsectionModalRef}>
                            <IconComponent name={'group'} size={'s'} /> Ungroup questions
                        </ModalToggleButton>
                    ) : (
                        <ModalToggleButton type="button" modalRef={groupSubsectionModalRef}>
                            <IconComponent name={'group'} size={'s'} /> Group questions
                        </ModalToggleButton>
                    )}
                    <ModalToggleButton type="button" modalRef={addStaticElementModalRef}>
                        <Icon.Add size={3} /> Add static element
                    </ModalToggleButton>
                    <Button type="button" onClick={onDeleteSubsection}>
                        <Icon.Delete size={3} /> Delete subsection
                    </Button>
                </MoreOptions>
                {isExpanded ? (
                    <Icon.ExpandLess size={4} onClick={() => onExpandedChange(false)} />
                ) : (
                    <Icon.ExpandMore size={4} onClick={() => onExpandedChange(true)} />
                )}
            </div>
            <ModalComponent
                modalRef={groupSubsectionModalRef}
                modalHeading={'Edit subsection'}
                modalBody={
                    <GroupQuestion
                        subsection={subsection}
                        questions={subsection.questions}
                        modalRef={groupSubsectionModalRef}
                    />
                }
                size="wide"
            />
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
