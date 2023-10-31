import { ModalRef, ModalFooter, ModalToggleButton, ButtonGroup } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RefObject, useState } from 'react';
import './ReorderModal.scss';
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
                <div className="reorder-modal__header">
                    <h3>Reorder</h3>
                </div>
            }
            modalBody={
                <DragDropContext
                    onDragEnd={handleDragEnd}
                    onDragStart={handleDragStart}
                    onDragUpdate={handleDragUpdate}>
                    {loading ? <Spinner /> : null}
                    <div className="reorder-modal">
                        {alertMessage ? <AlertBanner type={'prompt'}>{alertMessage}</AlertBanner> : null}
                        <div className="reorder-modal__content">
                            <div className="reorder-modal__top">
                                <Icon name="folder" />
                                <p>{pageName}</p>
                            </div>
                            <Droppable droppableId="all-sections" type="section">
                                {(provided) => (
                                    <div
                                        {...provided.droppableProps}
                                        ref={provided.innerRef}
                                        className="reorder-modal__tiles">
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
