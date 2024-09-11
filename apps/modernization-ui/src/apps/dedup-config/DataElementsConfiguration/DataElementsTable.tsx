import React, { useEffect, useState } from 'react';
import { useFormContext, Controller, useWatch } from 'react-hook-form';
import { DataElement } from '../const/init';
import { DataTable, Column } from 'design-system/table';
import { Checkbox } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
<<<<<<< HEAD
import styles from './data-elements-table.module.scss';

export const dataElements: DataElement[] = [
    { name: 'lastName', active: false, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'secondLastName', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'firstName', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'middleName', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'secondMiddleName', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'suffix', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'currentSex', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    {
        name: 'dateOfBirth',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5
    },
    {
        name: 'ssn',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5
    },
    {
        name: 'idType',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'idAssigningAuthority',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'idValue',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'streetAddress1',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'city',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'state',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'zip',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'telephone',
        active: true,
        m: 0.5,
        u: 0.1,
        threshold: 0.5
    }
];

=======
import { useDataElementsContext } from '../context/DataElementsContext';
import styles from './data-elements-table.module.scss';

>>>>>>> case-dedup-ui
const columns = (
    control: any,
    handleRowCheckboxChange: (index: number, checked: boolean) => void,
    checkedState: boolean[],
    watchedDataElements: DataElement[],
<<<<<<< HEAD
    handleHeaderCheckboxChange: (checked: boolean) => void // Add this parameter
=======
    handleHeaderCheckboxChange: (checked: boolean) => void
>>>>>>> case-dedup-ui
): Column<DataElement>[] => [
    {
        id: 'active',
        name: (
            <Checkbox
                name="select-all"
                id="select-all-checkbox"
                label=""
<<<<<<< HEAD
                checked={checkedState.every(Boolean)} // Check if all rows are selected
                onChange={(e) => handleHeaderCheckboxChange(e.target.checked)} // Handle "Select All"
=======
                checked={checkedState.every(Boolean)}
                onChange={(e) => handleHeaderCheckboxChange(e.target.checked)}
>>>>>>> case-dedup-ui
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
                rules={{ required: { value: checkedState[index], message: 'M is required' } }}
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
<<<<<<< HEAD
=======
    const { dataElements } = useDataElementsContext();
>>>>>>> case-dedup-ui
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

<<<<<<< HEAD
        // Update the form state
=======
>>>>>>> case-dedup-ui
        setValue(`dataElements.${index}.active`, checked);
    };

    const handleHeaderCheckboxChange = (checked: boolean) => {
        const updatedCheckedState = dataElements.map(() => checked);
        setCheckedState(updatedCheckedState);

<<<<<<< HEAD
        // Update the form state for all rows
=======
>>>>>>> case-dedup-ui
        dataElements.forEach((_, index) => {
            setValue(`dataElements.${index}.active`, checked);
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
