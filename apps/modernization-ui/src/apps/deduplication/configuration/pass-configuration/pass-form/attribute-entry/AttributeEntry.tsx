import { Checkbox } from 'design-system/checkbox';
import styles from './attribute-entry.module.scss';
import classNames from 'classnames';

type Props = {
    name: string;
    description: string;
    selected: boolean;
    onChange: () => void;
};
export const AttributeEntry = ({ name, description, selected, onChange }: Props) => {
    return (
        <div className={classNames(styles.attributeEntry, selected ? styles.selected : '')}>
            <Checkbox id={name} label={name} selected={selected} onChange={onChange} />
            <div className={styles.description}>{description}</div>
        </div>
    );
};
