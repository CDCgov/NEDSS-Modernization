import { Icon } from 'components/Icon/Icon';
import './ReorderSection.scss';
import { PagesSection } from 'apps/page-builder/generated/models/PagesSection';
import { useEffect, useState } from 'react';
import { ReorderSubsection } from '../ReorderSubsection/ReorderSubsection';
import { Draggable, DraggableProvided, Droppable } from 'react-beautiful-dnd';
import { PagesSubSection } from 'apps/page-builder/generated';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';

type Props = {
    index: number;
    section: PagesSection;
    visible: boolean;
};

export const ReorderSection = ({ section, index, visible }: Props) => {
    const [subsections, setSubsections] = useState<PagesSubSection[]>([]);
    const [subsectionsOpen, setSubsectionsOpen] = useState(true);
    const { closeId } = useDragDrop();

    useEffect(() => {
        if (!section.subSections) return;
        setSubsections(section.subSections);
    }, [section]);

    useEffect(() => {
        if (closeId.id === section.id!.toString()) {
            setSubsectionsOpen(false);
        } else setSubsectionsOpen(subsectionsOpen);
    }, [closeId]);

    return (
        <Draggable draggableId={section.id!.toString()} index={index}>
            {(provided: DraggableProvided) => (
                <div
                    className={`reorder-section ${visible ? '' : 'hidden'}`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div className="reorder-section__tile">
                        <div className="reorder-section__toggle" onClick={() => setSubsectionsOpen(!subsectionsOpen)}>
                            {subsectionsOpen ? (
                                <Icon name={'expand-more'} size={'xs'} />
                            ) : (
                                <Icon name={'navigate-next'} size={'xs'} />
                            )}
                        </div>
                        <div className="reorder-section__handle" {...provided.dragHandleProps}>
                            <Icon name={'drag'} size={'m'} />
                        </div>
                        <Icon name={'group'} size={'m'} />
                        <p>{section.name}</p>
                    </div>
                    <div className={`reorder-section__subsections ${subsectionsOpen ? '' : 'closed'}`}>
                        <Droppable droppableId={section.id!.toString()} type="subsection">
                            {(prov) => (
                                <div {...prov.droppableProps} ref={prov.innerRef} className="reorder-section__tiles">
                                    {subsections
                                        ? subsections.map((subsection: any, i: number) => {
                                              return (
                                                  <ReorderSubsection
                                                      subsection={subsection}
                                                      key={subsection.id.toString()}
                                                      index={i}
                                                      visible={subsection.visible}
                                                  />
                                              );
                                          })
                                        : null}
                                    {prov.placeholder}
                                </div>
                            )}
                        </Droppable>
                    </div>
                </div>
            )}
        </Draggable>
    );
};
