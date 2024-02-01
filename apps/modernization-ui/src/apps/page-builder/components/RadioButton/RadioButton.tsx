import styles from './radiobutton.module.scss';
import { Selectable } from 'options';
import { Radio } from '@trussworks/react-uswds';
import { useState } from 'react';

type Props = {
    options: Selectable[];
    onChange?: () => void;
};

export const RadioButtons = ({ options }: Props) => {
    const [currentSelection, setCurrentSelection] = useState<string>('');

    return (
        <div className={styles.radioBtns}>
            <>
                {options.map((s, k) => (
                    <div className={styles.content} key={k}>
                        <Radio
                            label={s.name}
                            id={'radio-btn'}
                            name={'radio-btn'}
                            value={s.value}
                            checked={s.value === currentSelection}
                            onChange={() => setCurrentSelection(s.value)}
                        />
                    </div>
                ))}
            </>
        </div>
    );
};
