import { Shown } from 'conditional-render';
import { Radio } from 'design-system/radio';
import { ControllerRenderProps } from 'react-hook-form';
import { GroupLine } from '../group-line/GroupLine';
import styles from './merge-data-display.module.scss';
import classNames from 'classnames';

type Props = {
    label: string;
    display?: string;
    groupType?: 'blank' | 'linked' | 'last';
    underlined?: boolean;
    selectable?: ControllerRenderProps & { id: string; formValue: string };
};
export const MergeDataDisplay = ({ label, display, groupType, selectable, underlined = false }: Props) => {
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
                            value={selectable.formValue}
                            checked={selectable.formValue === selectable.value}
                        />
                    </div>
                )}
            </Shown>

            <div className={classNames(styles.labelAndValue, underlined ? styles.underlined : '')}>
                <label className={styles.label} htmlFor={selectable?.id}>
                    {label}
                </label>
                <div className={styles.value}>{display ?? '---'}</div>
            </div>
        </div>
    );
};
