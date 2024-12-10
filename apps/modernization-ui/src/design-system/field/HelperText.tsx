import classNames from 'classnames';
import styles from './helper-text.module.scss';

type HelperTextProps = {
    id: string;
    children: string;
} & Omit<JSX.IntrinsicElements['span'], 'id' | 'children'>;

const HelperText = ({ id, children, className }: HelperTextProps) => (
    <span id={id} className={classNames(styles.helptext, className)}>
        {children}
    </span>
);

export { HelperText };
