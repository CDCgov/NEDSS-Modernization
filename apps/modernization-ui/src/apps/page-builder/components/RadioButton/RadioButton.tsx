import styles from './radiobutton.module.scss';
import { Selectable } from 'options';
import { Icon } from '@trussworks/react-uswds';

type Props = {
    options: Selectable[];
    onChange?: () => void;
};

export const RadioButton = ({ options }: Props) => {
    return (
        <div className={styles.radioBtns}>
            <>
                {options.map((s, k) => (
                    <div className={styles.content} key={k}>
                        <div className={styles.select}>
                            <Icon.RadioButtonUnchecked size={3} />
                        </div>
                        <div className={styles.title}>{s.name}</div>
                    </div>
                ))}
            </>
        </div>
    );
};
