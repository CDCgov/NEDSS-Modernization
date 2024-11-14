import { BlockingField } from 'apps/deduplication/match-configuration/Configuration';
import classNames from 'classnames';
import { Checkbox } from 'design-system/checkbox';
import styles from './blocking-field-option.module.scss';

type Props = {
    id: BlockingField;
    label: string;
    selected: boolean;
    onChange: (field: BlockingField) => void;
};
export const BlockingFieldOption = ({ id, label, selected, onChange }: Props) => {
    return (
        <div className={classNames(styles.blockingFieldOption, selected ? styles.selected : '')}>
            <Checkbox name={id} label={label} id={id} selected={selected} onChange={() => onChange(id)} />
        </div>
    );
};
