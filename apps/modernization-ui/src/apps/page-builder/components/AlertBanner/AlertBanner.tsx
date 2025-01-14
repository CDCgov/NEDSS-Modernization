import { ReactNode, useEffect, useState } from 'react';
import './AlertBanner.scss';
import { Icon } from '@trussworks/react-uswds';
import classNames from 'classnames';

export type AlertBannerProps = {
    type?: 'success' | 'warning' | 'prompt' | 'info' | 'error' | undefined;
    children?: ReactNode | ReactNode[];
    onClose?: () => void;
    expiration?: number;
    noIcon?: boolean;
    iconSize?: 3 | 4 | 5 | 6 | 7 | 8 | 9;
};

export const AlertBanner = ({ type, children, onClose, expiration, noIcon, iconSize }: AlertBannerProps) => {
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
            {!noIcon && (
                <div className="alert-banner__left">
                    {type === 'success' && <Icon.CheckCircle size={3} />}
                    {type === 'warning' && <Icon.Warning size={3} />}
                    {type === 'prompt' && <Icon.Info size={3} />}
                    {type === 'info' && <Icon.Info size={iconSize} />}
                    {type === 'error' && <Icon.Error size={5} />}
                </div>
            )}
            <div className="alert-banner__right">{children}</div>
            {onClose ? (
                <div className="alert-banner__close" onClick={() => onClose()}>
                    <Icon.Close size={3} />
                </div>
            ) : null}
        </div>
    );
};
