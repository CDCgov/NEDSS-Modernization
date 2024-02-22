import { ModalRef, ModalFooter, ModalToggleButton, ButtonGroup } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RefObject, useState } from 'react';
import styles from './reorder-modal.module.scss';
import { ReorderSection } from '../ReorderSection/ReorderSection';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { Icon } from 'components/Icon/Icon';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';
import { Spinner } from 'components/Spinner/Spinner';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';

type ReorderProps = {
    modalRef: RefObject<ModalRef>;
    pageName?: string;
    alertMessage?: string;
};

export const ReorderModal = ({ modalRef, pageName, alertMessage }: ReorderProps) => {
    const [loading] = useState(false);
    const { sections, handleDragEnd, handleDragStart, handleDragUpdate } = useDragDrop();
    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading={
                <div className={styles.header}>
                    <h2>Reorder</h2>
                </div>
            }
            modalBody={
                <DragDropContext
                    onDragEnd={handleDragEnd}
                    onDragStart={handleDragStart}
                    onDragUpdate={handleDragUpdate}>
                    {loading ? <Spinner /> : null}
                    <div className={styles.modal}>
                        {alertMessage ? <AlertBanner type={'prompt'}>{alertMessage}</AlertBanner> : null}
                        <div className={styles.content}>
                            <div className={styles.top}>
                                <Icon name="folder" />
                                <p>{pageName}</p>
                            </div>
                            <Droppable droppableId="all-sections" type="section">
                                {(provided) => (
                                    <div {...provided.droppableProps} ref={provided.innerRef}>
                                        {sections
                                            ? sections.map((section: any, i: number) => {
                                                  return (
                                                      <ReorderSection
                                                          key={section.id.toString()}
                                                          index={i}
                                                          section={section}
                                                          visible={section.visible}
                                                      />
                                                  );
                                              })
                                            : null}
                                        {provided.placeholder}
                                    </div>
                                )}
                            </Droppable>
                        </div>
                    </div>
                    <ModalFooter className="padding-2 margin-left-auto footer">
                        <ButtonGroup className="flex-justify-end">
                            <ModalToggleButton modalRef={modalRef} closer outline data-testid="condition-cancel-btn">
                                Close
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </DragDropContext>
            }></ModalComponent>
    );
};
