import { Icon } from 'components/Icon/Icon';
import styles from './reorder-section.module.scss';
import { PagesSection } from 'apps/page-builder/generated/models/PagesSection';
import { useEffect, useState } from 'react';
import { ReorderSubsection } from '../ReorderSubsection/ReorderSubsection';
import { Draggable, DraggableProvided, Droppable } from '@hello-pangea/dnd';
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
        }
    }, [closeId]);

    return (
        <Draggable draggableId={section.id!.toString()} index={index}>
            {(provided: DraggableProvided, snapshot) => (
                <div
                    className={`${styles.section} ${visible ? '' : styles.hidden} ${
                        snapshot.isDragging ? styles.dragging : ''
                    }`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div className={styles.tile}>
                        <div className={styles.toggle} onClick={() => setSubsectionsOpen(!subsectionsOpen)}>
                            {subsectionsOpen ? (
                                <Icon name={'expand-more'} size={'s'} />
                            ) : (
                                <Icon name={'navigate-next'} size={'s'} />
                            )}
                        </div>
                        <div className={styles.handle} {...provided.dragHandleProps}>
                            <Icon name={'drag'} size={'m'} />
                        </div>
                        <Icon name={'group'} size={'m'} />
                        <p>{section.name}</p>
                    </div>
                    <div className={`${styles.subsections} ${subsectionsOpen ? '' : styles.closed}`}>
                        <Droppable droppableId={section.id!.toString()} type="subsection">
                            {(prov, snapshot) => (
                                <div
                                    className={styles.droppable}
                                    {...prov.droppableProps}
                                    ref={prov.innerRef}
                                    style={{ backgroundColor: snapshot.isDraggingOver ? '#d9e8f6' : 'white' }}>
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
