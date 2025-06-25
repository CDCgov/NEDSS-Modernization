import { createPortal } from 'react-dom';
import classNames from 'classnames';
import { AlertMessage } from 'design-system/message';
import { Alert } from './alert';

import styles from './alert-toast.module.scss';

type AlertToastStatus = 'showing' | 'leaving';

type AlertToastProps = {
    status: AlertToastStatus;
    children: Alert;
};

const AlertToast = ({ status, children }: AlertToastProps) =>
    createPortal(
        <AlertMessage
            type={children.type}
            title={children.title}
            className={classNames(styles.toast, {
                [styles.showing]: status === 'showing',
                [styles.leaving]: status === 'leaving'
            })}>
            {children.message}
        </AlertMessage>,
        document.body
    );

export { AlertToast };
export type { AlertToastStatus };
