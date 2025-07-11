import classNames from 'classnames';
import { Sizing } from 'design-system/field';
import styles from './checkbox.module.scss';

type HasVisibleLabel = { label: string };
type HasAriaLabel = { 'aria-label': string };

type Labeled = HasVisibleLabel | HasAriaLabel | (HasVisibleLabel & HasAriaLabel);

const isLabelVisible = (labeled: Labeled): labeled is HasVisibleLabel => 'label' in labeled;

type CheckboxProps = {
    selected?: boolean;
    sizing?: Sizing;
    onChange?: (checked: boolean) => void;
} & Labeled &
    Omit<JSX.IntrinsicElements['input'], 'onChange' | 'checked' | 'value' | 'aria-label'>;

const Checkbox = ({ id, className, sizing, selected = false, onChange, ...remaining }: CheckboxProps) => {
    const handleChange = (checked: boolean) => {
        onChange?.(checked);
    };
    return (
        <div
            className={classNames(styles.checkbox, className, {
                [styles.sized]: sizing,
                [styles.small]: sizing === 'small',
                [styles.medium]: sizing === 'medium',
                [styles.large]: sizing === 'large'
            })}
            data-selected={selected}>
            <label
                className={classNames(styles.label, {
                    [styles.disabled]: remaining.disabled,
                    [styles.labeled]: isLabelVisible(remaining)
                })}>
                {isLabelVisible(remaining) && remaining.label}
                <input
                    id={id}
                    type="checkbox"
                    checked={selected}
                    aria-checked={selected}
                    onChange={(e) => handleChange(e.target.checked)}
                    {...remaining}
                />
            </label>
        </div>
    );
};

export { Checkbox };
export type { CheckboxProps };
