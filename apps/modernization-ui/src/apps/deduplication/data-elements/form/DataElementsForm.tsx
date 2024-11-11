import { Card } from 'apps/patient/add/extended/card/Card';
import { Checkbox } from 'design-system/checkbox';
import styles from './data-elements-form.module.scss';
import { DataElementRow } from './DataElementRow';

export const DataElementsForm = () => {
    const handleToggleAll = (toggle: boolean) => {
        console.log('toggle all', toggle);
    };

    return (
        <Card id="dataElementsCard" title="Data elements">
            <div className={styles.dataElementsForm}>
                <table>
                    <thead>
                        <tr>
                            <th className={styles.checkbox}>
                                <Checkbox
                                    name={'selectAll'}
                                    label=""
                                    id={'toggle-all-checkbox'}
                                    onChange={handleToggleAll}
                                />
                            </th>
                            <th>Field</th>
                            <th>M</th>
                            <th>U</th>
                            <th className={styles.calculatedField}>Odds ratio</th>
                            <th className={styles.calculatedField}>Log odds</th>
                            <th>Threshold</th>
                            <th />
                        </tr>
                        <tr className={styles.border}>
                            <th colSpan={8} />
                        </tr>
                    </thead>
                    <tbody>
                        <DataElementRow fieldName="Last name" field="lastName" />
                        <DataElementRow fieldName="First name" field="firstName" />
                    </tbody>
                </table>
            </div>
        </Card>
    );
};
