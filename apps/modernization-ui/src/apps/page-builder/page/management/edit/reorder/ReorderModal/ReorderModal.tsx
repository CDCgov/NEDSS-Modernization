import { ModalRef } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RefObject, useState } from 'react';
import styles from './reorder-modal.module.scss';
import { ReorderTab } from '../ReorderTab/ReorderTab';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { DragDropContext, Droppable } from '@hello-pangea/dnd';
import { Spinner } from 'components/Spinner/Spinner';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';
import { ModalToggleButton } from '@trussworks/react-uswds';
import { usePageManagement } from '../../../usePageManagement';

type ReorderProps = {
    modalRef: RefObject<ModalRef>;
    alertMessage?: string;
};

export const ReorderModal = ({ modalRef, alertMessage }: ReorderProps) => {
    const { page } = usePageManagement();
    const [loading] = useState(false);
    const { handleDragEnd, handleDragStart, handleDragUpdate } = useDragDrop();
    return (
        <ModalComponent
            modalRef={modalRef}
            closer
            size="wide"
            modalHeading="Reorder"
            modalBody={
                <DragDropContext
                    onDragEnd={handleDragEnd}
                    onDragStart={handleDragStart}
                    onDragUpdate={handleDragUpdate}>
                    {loading ? <Spinner /> : null}
                    <div className={styles.modal}>
                        {alertMessage ? <AlertBanner type={'prompt'}>{alertMessage}</AlertBanner> : null}
                        <div className={styles.content}>
                            <Droppable droppableId="all-tabs" type="tab">
                                {(provided, snapshot) => (
                                    <div
                                        {...provided.droppableProps}
                                        ref={provided.innerRef}
                                        style={{ backgroundColor: snapshot.isDraggingOver ? '#d9e8f6' : 'white' }}>
                                        {page.tabs
                                            ? page.tabs.map((tab: any, i: number) => {
                                                  return (
                                                      <ReorderTab
                                                          key={tab.id.toString()}
                                                          index={i}
                                                          tab={tab}
                                                          visible={tab.visible}
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
                </DragDropContext>
            }
            modalFooter={
                <ModalToggleButton modalRef={modalRef} closer outline data-testid="condition-cancel-btn">
                    Close
                </ModalToggleButton>
            }
        />
    );
};
