import './AlertModal.scss';
import { Icon } from '@trussworks/react-uswds';
import React from 'react';

export type AlertBannerProps = {
    type?: string;
    children?: any;
    message?: string;
    header?: string;
};

export const AlertModal = ({ type, message, header }: AlertBannerProps) => {
    return (
        <div className={`usa-alert_body ${type}`}>
            <div className="msg-container">
                <span className="alert-left-cont">
                    {type === 'success' && <Icon.CheckCircle size={4} />}
                    {type === 'warning' && <Icon.Warning size={4} />}
                    {type === 'prompt' && <Icon.Info size={3} />}
                    {type === 'error' && <Icon.Error size={5} />}
                </span>
                <span>
                    {header && <h2 className="header"> {header}</h2>}
                    <span className="msg-text">{message}</span>
                </span>
            </div>
        </div>
    );
};
