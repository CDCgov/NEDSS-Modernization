import styles from './radiobutton.module.scss';
import { Selectable } from 'options';
import { Radio } from './Radio';

type Props = {
    options: Selectable[];
    onChange?: () => void;
};

export const RadioButtons = ({ options, onChange }: Props) => {
    return (
        <div className={styles.radioBtns}>
            <>
                {options.map((s, k) => (
                    <div className={styles.content} key={k}>
                        <Radio value={s.name} onChange={() => onChange?.()} />
                    </div>
                ))}
            </>
        </div>
    );
};
