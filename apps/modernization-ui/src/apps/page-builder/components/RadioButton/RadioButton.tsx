import styles from './radiobutton.module.scss';
import { Selectable } from 'options';
import { Radio } from '@trussworks/react-uswds';

type Props = {
    options: Selectable[];
    isChecked: boolean;
    onChange?: () => void;
};

export const RadioButtons = ({ options, isChecked }: Props) => {
    return (
        <div className={styles.radioBtns}>
            <>
                {options.map((s, k) => (
                    <div className={styles.content} key={k}>
                        <Radio label={s.name} id={'radio-btn'} name={'radio-btn'} checked={isChecked} />
                    </div>
                ))}
            </>
        </div>
    );
};
