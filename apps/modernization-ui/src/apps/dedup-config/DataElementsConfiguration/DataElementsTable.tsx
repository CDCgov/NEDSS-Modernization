import React, { useEffect, useState } from 'react';
import { useFormContext, Controller, useWatch } from 'react-hook-form';
import { dataElements } from '../const/init';
import { DataTable, Column } from 'design-system/table';
import { Checkbox } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
import styles from './data-elements-table.module.scss';
import { DataElement } from '../types';

const columns = (
    control: any,
    handleRowCheckboxChange: (index: number, checked: boolean) => void,
    checkedState: boolean[],
    watchedDataElements: DataElement[],
    handleHeaderCheckboxChange: (checked: boolean) => void
): Column<DataElement>[] => [
    {
        id: 'active',
        name: (
            <Checkbox
                name="select-all"
                id="select-all-checkbox"
                label=""
                checked={checkedState.every(Boolean)}
                onChange={(e) => handleHeaderCheckboxChange(e.target.checked)}
            />
        ),
        render: (dataElement, index) => (
            <Controller
                control={control}
                name={`dataElements.${index}.active`}
                render={({ field: { value, onChange, name } }) => (
                    <Checkbox
                        name={name}
                        label=""
                        id={`checkbox-${index}`}
                        checked={value || false}
                        onChange={(e) => {
                            handleRowCheckboxChange(index, e.target.checked);
                            onChange(e.target.checked);
                        }}
                    />
                )}
            />
        )
    },
    {
        id: 'label',
        name: 'Name',
        render: (dataElement) => dataElement.label
    },
    {
        id: 'm',
        name: 'M',
        render: (dataElement, index) => (
            <Controller
                control={control}
                name={`dataElements.${index}.m`}
                rules={{ required: { value: checkedState[index], message: 'M is required' } }}
                render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                    <Input
                        type="number"
                        defaultValue={value}
                        value={value}
                        onChange={(e: any) => {
                            onChange(e);
                            handleRowCheckboxChange(index, checkedState[index]);
                        }}
                        onBlur={onBlur}
                        name={name}
                        disabled={!checkedState[index]}
                        error={error?.message}
                        tooltipDirection="top"
                    />
                )}
            />
        )
    },
    {
        id: 'u',
        name: 'U',
        render: (dataElement, index) => (
            <Controller
                control={control}
                name={`dataElements.${index}.u`}
                rules={{ required: { value: checkedState[index], message: 'U is required' } }}
                render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                    <Input
                        type="number"
                        defaultValue={value}
                        value={value}
                        onChange={(e: any) => {
                            onChange(e);
                            handleRowCheckboxChange(index, checkedState[index]);
                        }}
                        onBlur={onBlur}
                        name={name}
                        disabled={!checkedState[index]}
                        error={error?.message}
                        tooltipDirection="top"
                    />
                )}
            />
        )
    },
    {
        id: 'oddsRatio',
        name: 'Odds ratio',
        render: (dataElement, index) => (
            <Controller
                control={control}
                name={`dataElements.${index}.oddsRatio`}
                render={({ field: { value } }) => <span>{value || 'No Data'}</span>}
            />
        )
    },
    {
        id: 'logOdds',
        name: 'Log odds',
        render: (dataElement, index) => (
            <Controller
                control={control}
                name={`dataElements.${index}.logOdds`}
                render={({ field: { value } }) => <span>{value || 'No Data'}</span>}
            />
        )
    },
    {
        id: 'threshold',
        name: 'Threshold',
        render: (dataElement, index) => (
            <Controller
                control={control}
                name={`dataElements.${index}.threshold`}
                rules={{ required: { value: checkedState[index], message: 'Threshold is required' } }}
                render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                    <Input
                        type="number"
                        defaultValue={value}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        disabled={!checkedState[index]}
                        error={error?.message}
                        tooltipDirection="top"
                    />
                )}
            />
        )
    }
];

const DataElementsTable = () => {
    const { control, setValue } = useFormContext<{ dataElements: DataElement[] }>();
    const [checkedState, setCheckedState] = useState<boolean[]>([]);

    const watchedDataElements = useWatch({
        control,
        name: 'dataElements',
        defaultValue: dataElements
    });

    useEffect(() => {
        setValue('dataElements', dataElements);
        setCheckedState(dataElements.map((de) => de.active));
    }, [dataElements, setValue]);

    const handleRowCheckboxChange = (index: number, checked: boolean) => {
        const updatedCheckedState = [...checkedState];
        updatedCheckedState[index] = checked;
        setCheckedState(updatedCheckedState);

        const m = watchedDataElements[index]?.m;
        const u = watchedDataElements[index]?.u;

        const oddsRatio = m && u && u !== 0 ? m / u : 0;
        const logOdds = m && u && u !== 0 && m !== 0 ? Math.log(m / u) : 0;

        setValue(`dataElements.${index}.active`, checked);
        setValue(`dataElements.${index}.oddsRatio`, oddsRatio);
        setValue(`dataElements.${index}.logOdds`, logOdds);
    };

    const handleHeaderCheckboxChange = (checked: boolean) => {
        const updatedCheckedState = dataElements.map(() => checked);
        setCheckedState(updatedCheckedState);

        dataElements.forEach((_, index) => {
            setValue(`dataElements.${index}.active`, checked);

            const m = watchedDataElements[index]?.m;
            const u = watchedDataElements[index]?.u;
            const oddsRatio = m && u && u !== 0 ? m / u : 0;
            const logOdds = m && u && u !== 0 && m !== 0 ? Math.log(m / u) : 0;

            setValue(`dataElements.${index}.oddsRatio`, oddsRatio);
            setValue(`dataElements.${index}.logOdds`, logOdds);
        });
    };

    return (
        <div className={styles.table}>
            <DataTable<DataElement>
                id="dataElementsTable"
                columns={columns(
                    control,
                    handleRowCheckboxChange,
                    checkedState,
                    watchedDataElements,
                    handleHeaderCheckboxChange
                )}
                data={dataElements}
            />
        </div>
    );
};

export { DataElementsTable };
