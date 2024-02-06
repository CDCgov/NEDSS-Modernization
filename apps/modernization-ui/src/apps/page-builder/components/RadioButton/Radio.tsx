import { Icon } from '@trussworks/react-uswds';
import styles from './radio.module.scss';

type Props = {
    value: string;
    onChange: () => void;
};

export const Radio = ({ value, onChange }: Props) => {
    return (
        <div className={styles.radioBtn}>
            <Icon.RadioButtonUnchecked size={3} className={styles.select} onChange={onChange} />
            <div className={styles.text}>{value}</div>
        </div>
    );
};
