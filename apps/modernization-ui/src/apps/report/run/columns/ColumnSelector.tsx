import { Card } from 'design-system/card';
import { Checkbox, CheckboxGroup, SelectableCheckbox } from 'design-system/checkbox';
import { ReportColumn } from 'generated';
import { Selectable } from 'options';

import styles from './column-selector.module.scss';
import { ReportExecuteForm } from '../ReportRunPage';
import { validateRequiredRule } from 'validation/entry';
import { useController } from 'react-hook-form';
import { DragDropContext, Draggable, DraggableProvided, Droppable, DropResult } from '@hello-pangea/dnd';
import { Icon } from 'design-system/icon';

const ColumnSelector = ({ columns }: { columns: ReportColumn[] }) => {
    const {
        field: { onChange, value },
        fieldState: { error },
    } = useController<ReportExecuteForm, 'columns'>({
        name: 'columns',
        defaultValue: [], // TODO
        rules: validateRequiredRule('Columns'),
    });

    const options: Selectable[] = columns
        .filter(({ isDisplayable }) => isDisplayable)
        .map((c) => ({ value: c.id.toString(), name: c.title }));

    const handleOnAvailableChange = (option: Selectable) => (checked: boolean) => {
        if (checked) {
            onChange([...value!, option.value]);
        } else {
            onChange(value!.filter((v) => v !== option.value));
        }
    };

    return (
        <div className={styles.layout}>
            <Card id="available-columns" title="Available columns" collapsible={false}>
                <div className={styles.card}>
                    {options.map((o) => (
                        <Checkbox
                            key={o.value}
                            className={styles.option}
                            label={o.name}
                            selected={value?.includes(o.value)}
                            onChange={handleOnAvailableChange(o)}
                        />
                    ))}
                </div>
            </Card>
            <Card id="selected-columns" title="Selected columns" collapsible={false}>
                <div className={styles.card}>
                    {(value?.length ?? 0) === 0 ? (
                        <div className="display-flex flex-align-center flex-justify-center minh-full width-full">
                            <p>Select a column from "Available columns"</p>
                        </div>
                    ) : (
                        <SelectedColumnsList value={value} options={options} onChange={onChange} />
                    )}
                </div>
            </Card>
        </div>
    );
};

const SelectedColumnsList = ({
    options,
    value,
    onChange,
}: {
    options: Selectable[];
    value?: string[];
    onChange: (v: string[]) => void;
}) => {
    const handleDragEnd = (result: DropResult) => {
        if (result.destination) {
            const { source, destination } = result;

            const copy = value?.slice() ?? [];
            copy.splice(destination.index, 0, copy.splice(source.index, 1)[0]);
            onChange(copy);
        }
    };

    return (
        <DragDropContext onDragEnd={handleDragEnd}>
            <Droppable droppableId="columns">
                {(droppable) => (
                    <div {...droppable.droppableProps} ref={droppable.innerRef} className={styles.preferences}>
                        {value?.map((id, index) => (
                            <Draggable key={id} draggableId={id} index={index} disableInteractiveElementBlocking>
                                {(draggable: DraggableProvided) => (
                                    <div
                                        className={styles.option}
                                        ref={draggable.innerRef}
                                        {...draggable.draggableProps}>
                                        <span className={styles.handle} {...draggable.dragHandleProps}>
                                            <Icon name="drag" />
                                        </span>
                                        <span className={styles.label}>
                                            {options.find(({ value }) => value === id)?.name}
                                        </span>
                                    </div>
                                )}
                            </Draggable>
                        ))}
                        {droppable.placeholder}
                    </div>
                )}
            </Droppable>
        </DragDropContext>
    );
};

export { ColumnSelector };
