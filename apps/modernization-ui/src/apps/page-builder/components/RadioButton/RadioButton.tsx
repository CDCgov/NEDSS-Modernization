import { Icon as NbsIcon } from 'components/Icon/Icon';
import styles from './radiobutton.module.scss';
import { Selectable } from 'options';

type Props = {
    options: Selectable[];
    onChange?: () => void;
};

export const RadioButton = ({ options }: Props) => {
    return (
        <div className={styles.radioBtns}>
            <>
                {console.log(options)}
                {options.map((s, k) => (
                    <div className={styles.content} key={k}>
                        <div className={styles.select}>
                            <NbsIcon name={'multi-select'} />
                        </div>
                        <div className={styles.title}>{s.name}</div>
                    </div>
                ))}
            </>
        </div>
    );
};
