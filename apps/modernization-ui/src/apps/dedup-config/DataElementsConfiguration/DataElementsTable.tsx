import React, { useEffect, useState } from 'react';
import styles from './data-elements-table.module.scss';
import { useDataElementsContext } from '../context/DataElementsContext';
import { DataElement } from '../const/init';
import { TableBody, TableComponent } from 'components/Table';
import { Checkbox } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
import { Controller, useFormContext, useWatch } from 'react-hook-form';

export const DataElementsTable = () => {
    const { control, setValue, watch } = useFormContext<{ dataElements: DataElement[] }>();
    const { dataElements } = useDataElementsContext();

    const [bodies, setBodies] = useState<TableBody[]>([]);

    const activeStates = useWatch({
        control,
        name: 'dataElements',
        defaultValue: dataElements
    });

    useEffect(() => {
        setValue('dataElements', dataElements);
        setBodies(dataElements.map((dataElement, index) => asTableBody(dataElement, index)));
    }, [dataElements, setValue]);

    useEffect(() => {
        setBodies(dataElements.map((dataElement, index) => asTableBody(dataElement, index)));
    }, [activeStates, dataElements]);

    const calculateOddsRatio = (m: number, u: number) => (u !== 0 ? (m / u).toFixed(2) : 'No Data');

    const calculateLogOdds = (m: number, u: number) => {
        if (u === 0 || m === 0) return 'No Data';
        return Math.log(m / u).toFixed(2);
    };

    const asTableBody = (dataElement: DataElement, index: number): TableBody => {
        const mValue = watch(`dataElements.${index}.m`) || dataElement.m;
        const uValue = watch(`dataElements.${index}.u`) || dataElement.u;
        const isActive = activeStates && activeStates[index]?.active;

        return {
            id: dataElement.name,
            tableDetails: [
                {
                    id: 1,
                    title: (
                        <Controller
                            control={control}
                            name={`dataElements.${index}.active`}
                            render={({ field: { value, onChange, onBlur, name } }) => (
                                <Checkbox
                                    name={name}
                                    label={''}
                                    id={dataElement.name}
                                    checked={value || false}
                                    onChange={(e) => onChange(e.target.checked)}
                                    onBlur={onBlur}
                                />
                            )}
                        />
                    )
                },
                {
                    id: 2,
                    title: dataElement.name
                },
                {
                    id: 3,
                    title: (
                        <Controller
                            control={control}
                            name={`dataElements.${index}.m`}
                            rules={{ required: { value: isActive, message: 'M is required' } }}
                            render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                                <Input
                                    type="number"
                                    defaultValue={value.toString()}
                                    value={value}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    name={name}
                                    disabled={!isActive}
                                    error={error?.message}
                                />
                            )}
                        />
                    )
                },
                {
                    id: 4,
                    title: (
                        <Controller
                            control={control}
                            name={`dataElements.${index}.u`}
                            render={({ field: { value, onChange, onBlur, name } }) => (
                                <Input
                                    type="number"
                                    defaultValue={value.toString()}
                                    value={value}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    name={name}
                                    disabled={!isActive}
                                />
                            )}
                        />
                    )
                },
                {
                    id: 5,
                    title: calculateOddsRatio(mValue, uValue)
                },
                {
                    id: 6,
                    title: calculateLogOdds(mValue, uValue)
                },
                {
                    id: 7,
                    title: (
                        <Controller
                            control={control}
                            name={`dataElements.${index}.threshold`}
                            render={({ field: { value, onChange, onBlur, name } }) => (
                                <Input
                                    type="number"
                                    defaultValue={value.toString()}
                                    value={value}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    name={name}
                                    disabled={!isActive}
                                />
                            )}
                        />
                    )
                }
            ]
        };
    };

    const headings = [
        {
            name: <Checkbox id="all" label="" name="all" onChange={() => console.log('Checkbox state changed')} />,
            sortable: false
        },
        { name: 'Name', sortable: false },
        { name: 'M', sortable: false },
        { name: 'U', sortable: false },
        { name: 'Odds ratio', sortable: false },
        { name: 'Log odds', sortable: false },
        { name: 'Threshold', sortable: false }
    ];

    return (
        <div className={styles.table}>
            <TableComponent tableHead={headings} tableBody={bodies} isPagination={false} />
        </div>
    );
};
