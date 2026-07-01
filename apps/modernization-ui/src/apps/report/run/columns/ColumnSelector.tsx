import { Card } from 'design-system/card';
import { Checkbox } from 'design-system/checkbox';
import { ReportColumn } from 'generated';
import { Selectable } from 'options';
import { ReportExecuteForm } from '../ReportRunPage';
import { useController } from 'react-hook-form';
import { DragDropContext, Draggable, DraggableProvided, Droppable, DropResult } from '@hello-pangea/dnd';
import { Icon } from 'design-system/icon';
import { Button } from 'design-system/button';
import { useState } from 'react';
import { LiveSearch } from 'components/Search/LiveSearch';

import styles from './column-selector.module.scss';
import { toSelectable } from './utils';
import { ValidationErrorBanner } from '../../../../design-system/errors/ValidationError';

const ColumnSelector = ({ columns, defaultColumns }: { columns: ReportColumn[]; defaultColumns?: number[] }) => {
    const {
        field: { onChange, value },
        fieldState: { error },
    } = useController<ReportExecuteForm, 'columns'>({
        name: 'columns',
        defaultValue: defaultColumns?.map((c) => c.toString()) ?? [],
        rules: { required: { value: true, message: 'Select at least one column from the Available columns list.' } },
    });

    const [searchText, setSearchText] = useState<string>('');

    const options: Selectable[] = toSelectable(columns);

    const availableOptions = options.filter(
        (o) => !searchText || o.name.toLowerCase().includes(searchText.toLowerCase())
    );

    const handleOnAvailableChange = (option: Selectable) => (checked: boolean) => {
        if (checked) {
            onChange([...value!, option.value]);
        } else {
            onChange(value!.filter((v) => v !== option.value));
        }
    };

    const allSelected = availableOptions.every((o) => value?.includes(o.value));
    const selectWord = allSelected ? 'Deselect' : 'Select';

    const handleSelectAll = () => {
        if (allSelected) {
            const optionValues = availableOptions.map((o) => o.value);
            onChange(value!.filter((v) => !optionValues.includes(v)));
        } else {
            onChange([...value!, ...availableOptions.filter((o) => !value?.includes(o.value)).map((o) => o.value)]);
        }
    };

    const noneSelected = (value?.length ?? 0) === 0;

    return (
        <>
            {error?.message && (
                <ValidationErrorBanner level={3}>
                    <ul>
                        <li>{error.message}</li>
                    </ul>
                </ValidationErrorBanner>
            )}
            <div className={styles.layout}>
                <div className={styles.grid}>
                    <Card id="available-columns" title="Available columns" collapsible={false}>
                        <div className={styles.card}>
                            <div className={styles.search}>
                                <LiveSearch
                                    name="column-search"
                                    value={searchText}
                                    onChange={setSearchText}
                                    className="width-full"
                                />
                            </div>
                            <Checkbox
                                key="select-all"
                                className={styles.option}
                                label={selectWord + (searchText ? ' search results' : ' all')}
                                selected={false}
                                onChange={handleSelectAll}
                            />
                            {availableOptions.map((o) => (
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
                    <Card
                        id="selected-columns"
                        title="Selected columns"
                        collapsible={false}
                        actions={
                            <Button disabled={noneSelected} onClick={() => onChange([])}>
                                Clear selections
                            </Button>
                        }
                    >
                        <div className={styles.card}>
                            {noneSelected ? (
                                <p className={styles.center}>Select a column from "Available columns"</p>
                            ) : (
                                <SelectedColumnsList value={value} options={options} onChange={onChange} />
                            )}
                        </div>
                    </Card>
                </div>
            </div>
        </>
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
                                {(draggable: DraggableProvided) => {
                                    const optionName = options.find(({ value }) => value === id)?.name;
                                    return (
                                        <div
                                            className={styles.option}
                                            ref={draggable.innerRef}
                                            {...draggable.draggableProps}
                                        >
                                            <span
                                                className={styles.handle}
                                                aria-label={`Drag handle for ${optionName}`}
                                                {...draggable.dragHandleProps}
                                            >
                                                <Icon name="drag" />
                                            </span>
                                            <span className={styles.label}>{optionName}</span>
                                            <Button
                                                aria-label={`Remove ${optionName}`}
                                                icon="close"
                                                secondary={true}
                                                sizing="small"
                                                onClick={() => onChange(value.filter((v) => v !== id))}
                                            />
                                        </div>
                                    );
                                }}
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
