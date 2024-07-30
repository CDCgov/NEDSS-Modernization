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
    const { preferences, save } = useColumnPreferences();

    const [pending, setPending] = useState<ColumnPreference[]>([]);

    useEffect(() => {
        setPending(preferences);
    }, [preferences]);

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

    return (
        <div className={styles.panel}>
            <header>
                <label>Columns</label>
                <TrussworksIcon.Close size={3} onClick={close} />
            </header>
            <DragDropContext onDragEnd={handleDragEnd}>
                <Droppable droppableId="preferences">
                    {(dropable) => (
                        <div {...dropable.droppableProps} ref={dropable.innerRef} className={styles.preferences}>
                            {pending.map((preference, index) => (
                                <Draggable
                                    key={preference.id}
                                    draggableId={preference.id}
                                    index={index}
                                    isDragDisabled={!preference.moveable}>
                                    {(drabbable: DraggableProvided) => (
                                        <div
                                            ref={drabbable.innerRef}
                                            {...drabbable.draggableProps}
                                            className={styles.preference}>
                                            <span
                                                className={styles.handle}
                                                ref={drabbable.innerRef}
                                                {...drabbable.draggableProps}
                                                {...drabbable.dragHandleProps}>
                                                <Icon name="drag" />
                                            </span>
                                            <Checkbox
                                                id={`${preference.id}_visible`}
                                                name={preference.id}
                                                label={preference.name}
                                                className={styles.check}
                                                disabled={!preference.toggleable}
                                                selected={!preference.hidden}
                                                onChange={handleVisibilityChange(preference)}
                                            />
                                        </div>
                                    )}
                                </Draggable>
                            ))}
                            {dropable.placeholder}
                        </div>
                    )}
                </Droppable>
            </DragDropContext>
            <footer>
                <Button type="button" id="save-column-preferences" outline onClick={handleSave}>
                    Save columns
                </Button>
            </footer>
        </div>
    );
};

export { ColumnPreferencesPanel };
