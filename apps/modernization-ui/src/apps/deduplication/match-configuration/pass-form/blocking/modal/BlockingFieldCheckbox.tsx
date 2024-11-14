import { BlockingFieldOption } from 'apps/deduplication/match-configuration/model/Blocking';
import classNames from 'classnames';
import { Checkbox } from 'design-system/checkbox';
import styles from './blocking-field-option.module.scss';

type Props = {
    field: BlockingFieldOption;
    selected: boolean;
    onChange: (field: BlockingFieldOption) => void;
};
export const BlockingFieldCheckbox = ({ field, selected, onChange }: Props) => {
    return (
        <div className={classNames(styles.blockingFieldOption, selected ? styles.selected : '')}>
            <Checkbox
                name={field.value}
                label={field.name}
                id={field.value}
                selected={selected}
                onChange={() => onChange(field)}
            />
        </div>
    );
};
