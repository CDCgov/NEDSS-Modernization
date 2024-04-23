import { Button } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './IconButton.module.scss';

type Props = {
    icon: ReactNode;
    outline?: boolean;
    onClick?: () => void;
    destructive?: boolean;
    type?: 'button' | 'submit' | 'reset';
} & JSX.IntrinsicElements['button'];

const IconButton = ({ type = 'button', icon, outline = false, destructive = false, ...defaultProps }: Props) => {
    return (
        <Button
            className={destructive ? styles.destructive : ''}
            {...defaultProps}
            type={type}
            size="big"
            outline={outline}>
            {<>{icon}</>}
        </Button>
    );
};

export { IconButton };
