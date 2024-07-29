import { useEffect, useState } from 'react';
import { DragDropContext, Droppable, Draggable, DraggableProvided, DropResult } from 'react-beautiful-dnd';
import { Icon as TrussworksIcon } from '@trussworks/react-uswds';
import { useColumnPreferences, ColumnPreference } from 'design-system/table/preferences';
import { Checkbox } from 'design-system/checkbox';
import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Icon } from 'components/Icon/Icon';

import styles from './search-results-table-options.module.scss';

const swap =
    <I,>(items: I[]) =>
    (from: number, to: number) => {
        const copy = items.slice();
        copy.splice(to, 0, copy.splice(from, 1)[0]);
        return copy;
    };

type Props = {
    disabled?: boolean;
};

const SearchResultsTableOptions = ({ disabled = false }: Props) => {
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

    return (
        <ButtonActionMenu
            disabled={disabled}
            outline
            icon={<TrussworksIcon.Settings size={4} />}
            menuTitle="Columns"
            menuAction={() => save(pending)}
            menuActionTitle="Save columns">
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
        </ButtonActionMenu>
    );
};

export { SearchResultsTableOptions };
