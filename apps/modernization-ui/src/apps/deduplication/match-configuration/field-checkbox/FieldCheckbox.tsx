import classNames from 'classnames';
import { Checkbox } from 'design-system/checkbox';
import { BlockingFieldOption } from '../model/Blocking';
import { MatchingFieldOption } from '../model/Matching';
import styles from './field-checkbox.module.scss';

type Props = {
    field: BlockingFieldOption | MatchingFieldOption;
    selected: boolean;
    disabled?: boolean;
    onChange: (field: BlockingFieldOption | MatchingFieldOption) => void;
};
export const FieldCheckbox = ({ field, selected, disabled, onChange }: Props) => {
    return (
        <div className={classNames(styles.fieldCheckbox, selected ? styles.selected : '')}>
            <Checkbox
                name={field.value}
                label={field.name}
                id={field.value}
                selected={selected}
                disabled={disabled}
                onChange={() => onChange(field)}
            />
        </div>
    );
};
