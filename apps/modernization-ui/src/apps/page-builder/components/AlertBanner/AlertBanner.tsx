import { useEffect, useState } from 'react';
import './AlertBanner.scss';
import { Icon } from '@trussworks/react-uswds';
import classNames from 'classnames';

export type AlertBannerProps = {
    type?: string;
    children?: any;
    onClose?: () => void;
    expiration?: number;
};

export const AlertBanner = ({ type, children, onClose, expiration }: AlertBannerProps) => {
    const [hidden, setHidden] = useState(false);

    useEffect(() => {
        if (expiration) {
            const timerId = setTimeout(() => {
                setHidden(true);
            }, expiration);
            return () => clearTimeout(timerId);
        }
    }, []);

    return (
        <div className={classNames('alert-banner', type, hidden ? 'hidden' : '')}>
            <div className="alert-banner__left">
                {type === 'success' && <Icon.CheckCircle size={3} />}
                {type === 'warning' && <Icon.Warning size={3} />}
                {type === 'prompt' && <Icon.Info size={3} />}
                {type === 'info' && <Icon.Info size={3} />}
                {type === 'error' && <Icon.Error size={5} />}
            </div>
            <div className="alert-banner__right">{children}</div>
            {onClose ? (
                <div className="alert-banner__close" onClick={() => onClose()}>
                    <Icon.Close />
                </div>
            ) : null}
        </div>
    );
};
