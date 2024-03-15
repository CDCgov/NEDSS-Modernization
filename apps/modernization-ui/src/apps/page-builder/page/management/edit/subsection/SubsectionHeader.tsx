import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { MoreOptions } from 'apps/page-builder/components/MoreOptions/MoreOptions';
import { PagesSubSection, SubSectionControllerService } from 'apps/page-builder/generated';
import { AddStaticElement } from 'apps/page-builder/page/management/edit/staticelement/AddStaticElement';
import { authorization } from 'authorization';
import { Icon as IconComponent } from 'components/Icon/Icon';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { ConfirmationModal } from 'confirmation';
import { useRef, useState } from 'react';
import { usePageManagement } from '../../usePageManagement';
import styles from './subsection.module.scss';

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

    const handleOpenGroup = () => {
        setCloseOptions(true);
        onGroupQuestion(subsection);
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
                <MoreOptions
                    header={<Icon.MoreVert size={4} onClick={() => setCloseOptions(false)} />}
                    close={closeOptions}>
                    <Button type="button" onClick={onEditSubsection}>
                        <Icon.Edit size={3} /> Edit subsection
                    </Button>
                    {subsection.isGrouped ? (
                        <ModalToggleButton
                            type="button"
                            modalRef={ungroupSubsectionModalRef}
                            onClick={() => setCloseOptions(true)}>
                            <IconComponent name={'group'} size={'s'} /> Ungroup questions
                        </ModalToggleButton>
                    ) : (
                        <>
                            {subsection.isGroupable && subsection.questions.length > 0 && (
                                <Button type="button" onClick={handleOpenGroup}>
                                    <IconComponent name={'group'} size={'s'} /> Group questions
                                </Button>
                            )}
                        </>
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
