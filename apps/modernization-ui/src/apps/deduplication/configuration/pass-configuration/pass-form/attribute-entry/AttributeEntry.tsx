import { Checkbox } from 'design-system/checkbox';
import styles from './attribute-entry.module.scss';
import classNames from 'classnames';
import { Shown } from 'conditional-render';

type Props = {
    label: string;
    description?: string;
    selected: boolean;
    onChange: () => void;
};
export const AttributeEntry = ({ label: name, description, selected, onChange }: Props) => {
    return (
        <div className={classNames(styles.attributeEntry, selected ? styles.selected : '')}>
            <Checkbox id={name} label={name} selected={selected} onChange={onChange} />
            <Shown when={description !== undefined}>
                <div className={styles.description}>{description}</div>
            </Shown>
        </div>
    );
};
