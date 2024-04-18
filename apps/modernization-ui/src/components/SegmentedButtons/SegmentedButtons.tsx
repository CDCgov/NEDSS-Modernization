import { Button, Icon } from '@trussworks/react-uswds';
import styles from './segmentedButtons.module.scss';
import { Option } from 'generated';
type Props = {
    buttons: Option[];
    onBlur?: () => void;
    onChange?: () => void;
    onClick?: (button: any) => void;
    value: string | number;
};

export const SegmentedButtons = ({ buttons, value, onBlur, onChange, onClick }: Props) => {
    return (
        <div className={styles.segmentedButtons}>
            {buttons.map((button: Option, key: number) => (
                <Button
                    type="button"
                    key={key}
                    onBlur={onBlur}
                    onClick={onClick ? () => onClick(button) : undefined}
                    onChange={onChange}
                    outline={button.value !== value}
                    className={button.value === value ? styles.active : ''}>
                    {value === button.value && <Icon.Check />}
                    {button.name}
                </Button>
            ))}
        </div>
    );
};
