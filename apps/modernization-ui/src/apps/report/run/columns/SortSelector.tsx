import { ReportColumn, SortSpec } from 'generated';
import { ReportExecuteForm } from '../ReportRunPage';
import { Controller, useWatch } from 'react-hook-form';
import { useId } from 'react';
import { SingleSelect } from 'design-system/select';
import { toSelectable } from './utils';
import { EnumSelectable } from 'apps/report/utils';
import { EntryWrapper } from 'components/Entry';

import styles from './sort-selector.module.scss'

const DIRECTION_OPTIONS: EnumSelectable<SortSpec.direction>[] = [
    { value: SortSpec.direction.ASC, name: 'Ascending' },
    { value: SortSpec.direction.DESC, name: 'Descending' },
];

const SortSelector = ({ columns, defaultSort }: { columns: ReportColumn[]; defaultSort?: SortSpec }) => {
    const groupId = useId();
    const selectedColumns = useWatch<ReportExecuteForm, 'columns'>({ name: 'columns', defaultValue: [] }) ?? [];

    const columnOptions = toSelectable(columns.filter(({ id }) => selectedColumns.includes(id.toString())));

    return (
        <EntryWrapper
            sizing="medium"
            label="Report data sorting"
            htmlFor={groupId}
            orientation="horizontal"
            helperText="Sort data by a selected column">
            <div role="group" id={groupId} className={styles.layout}>
                <Controller
                    name="sort.column"
                    defaultValue={columnOptions.find(({ value }) => parseInt(value) === defaultSort?.columnUid)}
                    render={({ field: { value, onChange } }) => (
                        <SingleSelect
                            label="Sort by"
                            id={`${groupId}-col`}
                            orientation="vertical"
                            options={columnOptions}
                            value={columnOptions.find((option) => option.value === value)}
                            onChange={(option) => onChange(option?.value)}
                        />
                    )}
                />
                <Controller
                    name="sort.direction"
                    defaultValue={DIRECTION_OPTIONS.find(({ value }) => value === defaultSort?.direction)}
                    render={({ field: { value, onChange } }) => (
                        <SingleSelect
                            label="Sort order"
                            id={`${groupId}-dir`}
                            orientation="vertical"
                            placeholder="" // force a choice
                            options={DIRECTION_OPTIONS}
                            value={DIRECTION_OPTIONS.find((option) => option.value === value)}
                            onChange={(option) => onChange(option?.value)}
                        />
                    )}
                />
            </div>
        </EntryWrapper>
    );
};

export { SortSelector };
