import './AlertBanner.scss';
import { Icon } from '@trussworks/react-uswds';

export type AlertBannerProps = {
    type?: string;
    children?: any;
    onClose?: () => void;
};

export const AlertBanner = ({ type, children, onClose }: AlertBannerProps) => {
    return (
        <div className={`alert-banner ${type}`}>
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
