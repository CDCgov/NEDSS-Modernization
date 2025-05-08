import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';
import { PagesSection, PagesTab } from 'apps/page-builder/generated';
import { useEffect, useState } from 'react';
import { Draggable, DraggableProvided, Droppable } from '@hello-pangea/dnd';
import { ReorderSection } from '../ReorderSection/ReorderSection';
import { Icon } from 'components/Icon/Icon';
import styles from './reorder-tab.module.scss';

type Props = {
    index: number;
    tab: PagesTab;
    visible: boolean;
};

export const ReorderTab = ({ tab, index, visible }: Props) => {
    const [sections, setSections] = useState<PagesSection[]>([]);
    const [sectionsOpen, setSectionsOpen] = useState(true);
    const { closeId } = useDragDrop();

    useEffect(() => {
        if (!tab.sections) return;
        setSections(tab.sections);
    }, [tab]);

    useEffect(() => {
        if (closeId.id === tab.id!.toString()) {
            setSectionsOpen(false);
        }
    }, [closeId]);

    return (
        <Draggable draggableId={tab.id!.toString()} index={index}>
            {(provided: DraggableProvided, snapshot) => (
                <div
                    className={`${styles.tab} ${visible ? '' : styles.hidden} ${
                        snapshot.isDragging ? styles.dragging : ''
                    }`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div className={styles.tile}>
                        <div className={styles.toggle} onClick={() => setSectionsOpen(!sectionsOpen)}>
                            {sectionsOpen ? (
                                <Icon name={'expand-more'} size={'s'} />
                            ) : (
                                <Icon name={'navigate-next'} size={'s'} />
                            )}
                        </div>
                        <div className={styles.handle} {...provided.dragHandleProps}>
                            <Icon name={'drag'} size={'m'} />
                        </div>
                        <Icon name={'folder'} size={'m'} />
                        <p>{tab.name}</p>
                    </div>
                    <div className={`${styles.sections} ${sectionsOpen ? '' : styles.closed}`}>
                        <Droppable droppableId={tab.id!.toString()} type="section">
                            {(prov, snapshot) => (
                                <div
                                    className={styles.droppable}
                                    {...prov.droppableProps}
                                    ref={prov.innerRef}
                                    style={{ backgroundColor: snapshot.isDraggingOver ? '#d9e8f6' : 'white' }}>
                                    {sections
                                        ? sections.map((section: any, i: number) => {
                                              return (
                                                  <ReorderSection
                                                      section={section}
                                                      key={section.id.toString()}
                                                      index={i}
                                                      visible={section.visible}
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
