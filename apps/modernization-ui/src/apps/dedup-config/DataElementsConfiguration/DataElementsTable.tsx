import React, { useEffect, useState } from 'react';
import { useFormContext, Controller, useWatch } from 'react-hook-form';
import { DataElement } from '../const/init';
import { DataTable, Column } from 'design-system/table';
import { Checkbox } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
import { useDataElementsContext } from '../context/DataElementsContext';

const columns = (
    control: any,
    handleRowCheckboxChange: (index: number, checked: boolean) => void,
    checkedState: boolean[],
    watchedDataElements: DataElement[]
): Column<DataElement>[] => [
    {
        id: 'active',
        name: 'Active',
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
        id: 'name',
        name: 'Name',
        render: (dataElement) => dataElement.name
    },
    {
        id: 'm',
        name: 'M',
        render: (dataElement, index) => (
            <Controller
                control={control}
                name={`dataElements.${index}.m`}
                render={({ field: { value, onChange, onBlur, name } }) => (
                    <Input
                        type="number"
                        defaultValue={value}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        disabled={!checkedState[index]}
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
                render={({ field: { value, onChange, onBlur, name } }) => (
                    <Input
                        type="number"
                        defaultValue={value}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        disabled={!checkedState[index]}
                    />
                )}
            />
        )
    },
    {
        id: 'oddsRatio',
        name: 'Odds ratio',
        render: (dataElement, index) => {
            const m = watchedDataElements[index]?.m;
            const u = watchedDataElements[index]?.u;
            const oddsRatio = m && u && u !== 0 ? (m / u).toFixed(2) : 'No Data';
            return oddsRatio;
        }
    },
    {
        id: 'logOdds',
        name: 'Log odds',
        render: (dataElement, index) => {
            const m = watchedDataElements[index]?.m;
            const u = watchedDataElements[index]?.u;
            const logOdds = m && u && u !== 0 && m !== 0 ? Math.log(m / u).toFixed(2) : 'No Data';
            return logOdds;
        }
    },
    {
        id: 'threshold',
        name: 'Threshold',
        render: (dataElement, index) => (
            <Controller
                control={control}
                name={`dataElements.${index}.threshold`}
                render={({ field: { value, onChange, onBlur, name } }) => (
                    <Input
                        type="number"
                        defaultValue={value}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        disabled={!checkedState[index]}
                    />
                )}
            />
        )
    }
];

const DataElementsTable = () => {
    const { control, setValue } = useFormContext<{ dataElements: DataElement[] }>();
    const { dataElements } = useDataElementsContext();
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
    };

    return (
        <DataTable<DataElement>
            id="dataElementsTable"
            columns={columns(control, handleRowCheckboxChange, checkedState, watchedDataElements)}
            data={dataElements}
        />
    );
};

export { DataElementsTable };
