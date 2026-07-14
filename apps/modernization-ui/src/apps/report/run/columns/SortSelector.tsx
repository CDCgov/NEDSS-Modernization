import { ReportColumn, SortSpec } from 'generated';
import { ReportExecuteForm } from '../ReportRunPage';
import { Controller, useController, useWatch } from 'react-hook-form';
import { useEffect, useId } from 'react';
import { SingleSelect } from 'design-system/select';
import { toSelectable } from './utils';
import { EnumSelectable } from 'apps/report/utils';
import { EntryWrapper } from 'components/Entry';
import { SIZING } from 'apps/report/constants';

import styles from './sort-selector.module.scss';

const DIRECTION_OPTIONS: EnumSelectable<SortSpec.direction>[] = [
    { value: SortSpec.direction.ASC, name: 'Ascending' },
    { value: SortSpec.direction.DESC, name: 'Descending' },
];

const SortSelector = ({
    columns,
    defaultSort,
    defaultColumns,
}: {
    columns: ReportColumn[];
    defaultSort?: SortSpec;
    defaultColumns?: number[];
}) => {
    const groupId = useId();
    const {
        field: { onChange, value },
    } = useController<ReportExecuteForm, 'sort.column'>({
        name: 'sort.column',
        defaultValue: defaultSort?.columnUid.toString(),
    });
    const selectedColumns =
        useWatch<ReportExecuteForm, 'columns'>({
            name: 'columns',
            defaultValue: defaultColumns?.map((id) => id.toString()) ?? [],
        }) ?? [];
    const columnOptions = toSelectable(columns.filter(({ id }) => selectedColumns.includes(id.toString())));

    // make sure the sort column data resets if column un-selected
    useEffect(() => {
        if (!!value && !selectedColumns.includes(value)) {
            onChange(null);
        }
    }, [selectedColumns, value]);

    return (
        <EntryWrapper
            sizing={SIZING}
            label="Report data sorting"
            htmlFor={groupId}
            orientation="horizontal"
            helperText="Sort data by a selected column"
        >
            <div role="group" id={groupId} className={styles.layout}>
                <SingleSelect
                    sizing={SIZING}
                    label="Sort by"
                    id={`${groupId}-col`}
                    orientation="vertical"
                    options={columnOptions}
                    value={columnOptions.find((option) => option.value === value)}
                    onChange={(option) => onChange(option?.value ?? null)}
                />
                <Controller
                    name="sort.direction"
                    defaultValue={defaultSort?.direction ?? DIRECTION_OPTIONS[0].value}
                    render={({ field: { value, onChange } }) => (
                        <SingleSelect
                            sizing={SIZING}
                            label="Sort order"
                            id={`${groupId}-dir`}
                            orientation="vertical"
                            placeholder="" // force a choice
                            options={DIRECTION_OPTIONS}
                            value={DIRECTION_OPTIONS.find((option) => option.value === value)}
                            onChange={(option) => onChange(option?.value ?? null)}
                        />
                    )}
                />
            </div>
        </EntryWrapper>
    );
};

export { SortSelector };
