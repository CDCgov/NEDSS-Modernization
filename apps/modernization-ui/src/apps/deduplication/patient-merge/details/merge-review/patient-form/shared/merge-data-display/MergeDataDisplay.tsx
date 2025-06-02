import { Shown } from 'conditional-render';
import { Radio } from 'design-system/radio';
import { ControllerRenderProps } from 'react-hook-form';
import { GroupLine } from '../group-line/GroupLine';
import styles from './merge-data-display.module.scss';
import classNames from 'classnames';

type Props = {
    label: string;
    value?: string;
    groupType?: 'blank' | 'linked' | 'last';
    underlined?: boolean;
    selectable?: ControllerRenderProps & { id: string; selectValue: string };
};
export const MergeDataDisplay = ({ label, value, groupType, selectable, underlined = false }: Props) => {
    return (
        <div className={styles.mergeDataDisplay}>
            <Shown
                when={selectable !== undefined}
                fallback={
                    <div className={styles.groupLine}>
                        <GroupLine groupType={groupType} />
                    </div>
                }>
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
            </Shown>

            <div className={classNames(styles.labelAndValue, underlined ? styles.underlined : '')}>
                <div className={styles.label}>{label}</div>
                <div className={styles.value}>{value ?? '---'}</div>
            </div>
        </div>
    );
};
