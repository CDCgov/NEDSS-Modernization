import { Shown } from 'conditional-render';
import { Radio } from 'design-system/radio';
import { ControllerRenderProps } from 'react-hook-form';
import { GroupLine } from '../group-line/GroupLine';
import styles from './merge-data-display.module.scss';

type Props = {
    label: string;
    value?: string;
    groupType?: 'linked' | 'last';
    selectable?: ControllerRenderProps & { id: string; selectValue: string };
};
export const MergeDataDisplay = ({ label, value, groupType: grouped, selectable }: Props) => {
    return (
        <div className={styles.mergeDataDisplay}>
            <Shown when={grouped !== undefined}>
                <div className={styles.groupLine}>
                    <GroupLine last={grouped === 'last'} />
                </div>
            </Shown>
            {selectable && (
                <div className={styles.radioButton}>
                    <Radio
                        id={selectable.id}
                        name={selectable.name}
                        sizing="small"
                        label=""
                        onChange={selectable.onChange}
                        value={selectable.selectValue}
                        checked={selectable.selectValue === selectable.value}
                    />
                </div>
            )}
            <div className={styles.labelAndValue}>
                <div className={styles.label}>{label}</div>
                <div className={styles.value}>{value ?? '---'}</div>
            </div>
        </div>
    );
};
