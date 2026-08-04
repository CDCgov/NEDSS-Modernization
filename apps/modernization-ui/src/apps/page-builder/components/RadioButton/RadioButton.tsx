import { Selectable } from 'options';

import { Radio } from './Radio';
import styles from './radiobutton.module.scss';

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
