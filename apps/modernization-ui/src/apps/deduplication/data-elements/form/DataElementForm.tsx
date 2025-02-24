import { Checkbox } from 'design-system/checkbox';
import DataElementRow from './DataElementRow';
import { DataElementGroup } from "../types/DataElement";
import { useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../types/DataElement';
import { Card } from 'design-system/card/Card';
import styles from './data-elements-form.module.scss';


export const DataElementsForm = () => {
    const form = useFormContext<DataElements>();
    const watch = useWatch({ control: form.control });

    const hasInactive = (): boolean => {
        return dataElementGroups.some(group =>
            group.fields.some(field => watch[field.key]?.active === false)
        );
    };

    const handleToggleAll = (toggle: boolean) => {
        dataElementGroups.forEach(group => {
            group.fields.forEach(field => {
                form.setValue(`${field.key}.active`, toggle);
            });
        });
    };

    return (
        <Card id="dataElementsCard" title="Data Elements">
            <div className={styles.dataElementsForm}>
                <table>
                    <thead>
                    <tr>
                        <th className={styles.checkbox}>
                            <Checkbox
                                name={'selectAll'}
                                label=""
                                id={'toggle-all-checkbox'}
                                selected={!hasInactive()}
                                onChange={handleToggleAll}
                            />
                        </th>
                        <th>Field</th>
                    </tr>
                    <tr className={styles.border}>
                        <th colSpan={8} />
                    </tr>
                    </thead>
                    <tbody>
                    {dataElementGroups.map(group => (
                        <>
                            <tr key={group.title} className={styles.groupHeader}>
                                <td colSpan={2}>{group.title}</td>
                            </tr>
                            {group.fields.map(field => (
                                <DataElementRow key={field.key} fieldName={field.label} field={field.key} />
                            ))}
                        </>
                    ))}
                    </tbody>
                </table>
            </div>
        </Card>
    );
};