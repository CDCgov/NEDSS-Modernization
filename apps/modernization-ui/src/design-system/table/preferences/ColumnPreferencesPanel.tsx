import { useEffect, useState } from 'react';
import { DragDropContext, Droppable, Draggable, DraggableProvided, DropResult } from '@hello-pangea/dnd';
import { Checkbox } from 'design-system/checkbox';
import { Icon } from 'design-system/icon';
import { Button } from 'design-system/button';
import { Sizing } from 'design-system/field';
import { ClosablePanel } from 'design-system/panel/closable';
import { ColumnPreference, isNamed, isLabeled, NamedColumnPreference } from './preference';
import { useColumnPreferences } from './useColumnPreferences';

import styles from './column-preference-panel.module.scss';

const swap =
    <I,>(items: I[]) =>
    (from: number, to: number) => {
        const copy = items.slice();
        copy.splice(to, 0, copy.splice(from, 1)[0]);
        return copy;
    };

type Props = {
    sizing?: Sizing;
    close: () => void;
};

const ColumnPreferencesPanel = ({ close, sizing = 'small' }: Props) => {
    const { preferences, save, reset } = useColumnPreferences();

    const [pending, setPending] = useState<ColumnPreference[]>([]);
    useEffect(() => {
        setPending(structuredClone(preferences));
    }, [JSON.stringify(preferences)]);

    const handleVisibilityChange = (preference: NamedColumnPreference) => (visible: boolean) => {
        setPending((current) => {
            const copy = current.slice();
            const index = copy.indexOf(preference);

            if (index >= 0) {
                const found = copy[index];

                if (isNamed(found)) {
                    found.hidden = !visible;
                }
            }
            return copy;
        });
    };

    const handleDragEnd = (result: DropResult) => {
        if (result.destination) {
            const { source, destination } = result;
            setPending((current) => {
                const found = current[destination.index];

                if (isNamed(found) && found.moveable) {
                    return swap(current)(source.index, destination.index);
                }

                return current;
            });
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
        <ClosablePanel
            title="Columns"
            onClose={close}
            footer={() => (
                <div className={styles.footer}>
                    <Button tertiary sizing={sizing} onClick={handleReset}>
                        Reset
                    </Button>
                    <Button type="button" secondary sizing={sizing} onClick={handleSave}>
                        Save columns
                    </Button>
                </div>
            )}>
            <DragDropContext onDragEnd={handleDragEnd}>
                <Droppable droppableId="preferences">
                    {(droppable) => (
                        <div {...droppable.droppableProps} ref={droppable.innerRef} className={styles.preferences}>
                            {pending.map((preference, index) => (
                                <Draggable
                                    key={preference.id}
                                    draggableId={preference.id}
                                    index={index}
                                    disableInteractiveElementBlocking
                                    isDragDisabled={
                                        isLabeled(preference) || (isNamed(preference) && !preference.moveable)
                                    }>
                                    {(draggable: DraggableProvided) => (
                                        <div ref={draggable.innerRef} {...draggable.draggableProps}>
                                            {isNamed(preference) && (
                                                <PreferenceOption
                                                    sizing={sizing}
                                                    draggable={draggable}
                                                    onVisibilityChange={handleVisibilityChange(preference)}>
                                                    {preference}
                                                </PreferenceOption>
                                            )}
                                            {isLabeled(preference) && (
                                                <PreferencePlaceholder draggable={draggable}>
                                                    {preference}
                                                </PreferencePlaceholder>
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
        </ClosablePanel>
    );
};

type PreferencePlaceholderProps = {
    draggable: DraggableProvided;
    children: ColumnPreference;
};

const PreferencePlaceholder = ({ draggable, children }: PreferencePlaceholderProps) => (
    <span id={children.id} {...draggable.dragHandleProps} tabIndex={-1} />
);

type PreferenceOptionProps = {
    draggable: DraggableProvided;
    onVisibilityChange: (value: boolean) => void;
    sizing?: Sizing;
    children: NamedColumnPreference;
} & Omit<JSX.IntrinsicElements['span'], 'draggable' | 'children'>;

const PreferenceOption = ({ draggable, onVisibilityChange, sizing, children, ...remaining }: PreferenceOptionProps) => (
    <span className={styles.preference} {...remaining}>
        <Checkbox
            id={`${children.id}_visible`}
            sizing={sizing}
            name={children.id}
            label={children.name}
            disabled={!children.toggleable}
            selected={!children.hidden}
            onChange={onVisibilityChange}
        />
        {children.moveable && (
            <span className={styles.handle} {...draggable.dragHandleProps}>
                <Icon name="drag" />
            </span>
        )}
    </span>
);

export { ColumnPreferencesPanel };
