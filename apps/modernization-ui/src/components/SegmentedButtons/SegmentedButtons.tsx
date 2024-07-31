import { Button, Icon, Label } from '@trussworks/react-uswds';
import styles from './segmentedButtons.module.scss';
import { Option } from 'generated';
type Props = {
    title?: string;
    buttons: Option[];
    onBlur?: () => void;
    onChange?: () => void;
    onClick?: (button: any) => void;
    value: string | number;
    required?: boolean;
};

export const SegmentedButtons = ({ title, buttons, value, onBlur, onChange, onClick, required }: Props) => {
    return (
        <div className={`${styles.segmentedButtons} ${required && styles.hidden}`}>
            {title && (
                <Label htmlFor="title">
                    {title}
                    {required && <span> *</span>}
                </Label>
            )}
            <div className={styles.buttons}>
                {buttons.map((button: Option, key: number) => (
                    <Button
                        type="button"
                        key={key}
                        onBlur={onBlur}
                        onClick={onClick ? () => onClick(button) : undefined}
                        onChange={onChange}
                        outline={button.value !== value}
                        className={`${button.value === value ? styles.active : ''} fieldType-option-${key}`}>
                        {value === button.value && <Icon.Check />}
                        {button.name}
                    </Button>
                ))}
            </div>
        </div>
    );
};
