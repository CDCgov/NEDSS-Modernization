import { useEffect, useState } from 'react';
import { DragDropContext, Droppable, Draggable, DraggableProvided, DropResult } from 'react-beautiful-dnd';
import { Icon as TrussworksIcon } from '@trussworks/react-uswds';
import { useColumnPreferences, ColumnPreference } from 'design-system/table/preferences';
import { Checkbox } from 'design-system/checkbox';
import { Button } from 'components/button';
import { Icon } from 'components/Icon/Icon';
import styles from './column-preference-panel.module.scss';

const swap =
    <I,>(items: I[]) =>
    (from: number, to: number) => {
        const copy = items.slice();
        copy.splice(to, 0, copy.splice(from, 1)[0]);
        return copy;
    };

type Props = {
    close: () => void;
};

const ColumnPreferencesPanel = ({ close }: Props) => {
    const { preferences, save, reset } = useColumnPreferences();

    const [pending, setPending] = useState<ColumnPreference[]>([]);

    useEffect(() => {
        setPending(structuredClone(preferences));
    }, [JSON.stringify(preferences)]);

    const handleVisibilityChange = (preference: ColumnPreference) => (visible: boolean) => {
        setPending((current) => {
            const copy = current.slice();
            const index = copy.indexOf(preference);

            if (index >= 0) {
                copy[index].hidden = !visible;
            }
            return copy;
        });
    };

    const handleDragEnd = (result: DropResult) => {
        if (result.destination) {
            const { source, destination } = result;
            setPending((current) =>
                current[destination.index].moveable ? swap(current)(source.index, destination.index) : current
            );
        }
    };

    const handleSave = () => {
        save(pending);
        close();
    };

    const handleReset = () => {
        reset();
        close();
    };

    return (
        <div className={styles.panel}>
            <header>
                <h2>Columns</h2>
                <TrussworksIcon.Close size={3} onClick={close} />
            </header>
            <DragDropContext onDragEnd={handleDragEnd}>
                <Droppable droppableId="preferences">
                    {(droppable) => (
                        <div {...droppable.droppableProps} ref={droppable.innerRef} className={styles.preferences}>
                            {pending.map((preference, index) => (
                                <Draggable
                                    key={preference.id}
                                    draggableId={preference.id + 1}
                                    index={index}
                                    isDragDisabled={!preference.moveable}>
                                    {(draggable: DraggableProvided) => (
                                        <div
                                            ref={draggable.innerRef}
                                            {...draggable.draggableProps}
                                            className={styles.preference}>
                                            <Checkbox
                                                id={`${preference.id}_visible`}
                                                name={preference.id}
                                                label={preference.name}
                                                disabled={!preference.toggleable}
                                                selected={!preference.hidden}
                                                onChange={handleVisibilityChange(preference)}
                                            />
                                            {preference.moveable && (
                                                <span className={styles.handle} {...draggable.dragHandleProps}>
                                                    <Icon name="drag" />
                                                </span>
                                            )}
                                        </div>
                                    )}
                                </Draggable>
                            ))}
                            {droppable.placeholder}
                        </div>
                    )}
                </Droppable>
            </DragDropContext>
            <footer>
                <Button className={styles.reset} onClick={handleReset}>
                    Reset
                </Button>
                <Button type="button" id="save-column-preferences" outline onClick={handleSave}>
                    Save columns
                </Button>
            </footer>
        </div>
    );
};

export { ColumnPreferencesPanel };
