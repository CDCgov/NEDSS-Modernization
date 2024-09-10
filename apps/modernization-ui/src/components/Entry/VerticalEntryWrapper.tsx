import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Label } from '@trussworks/react-uswds';
import { Tooltip, Icon } from '@trussworks/react-uswds';
import styles from './vertical-entry-wrapper.module.scss';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    tooltipDirection?: 'top' | 'left' | 'right' | 'bottom';
    children: ReactNode;
};
const VerticalEntryWrapper = ({ className, htmlFor, label, required, error, tooltipDirection, children }: Props) => (
    <span className={classNames(styles.wrapper, className)}>
        {label && (
            <Label className={classNames({ required })} htmlFor={htmlFor}>
                {label}
            </Label>
        )}
        {tooltipDirection && error && (
            <Tooltip id={htmlFor} label={error} className={classNames(styles.tooltip)} position={tooltipDirection}>
                <Icon.ErrorOutline className={styles.tooltipIcon} />
            </Tooltip>
        )}
        {!tooltipDirection && error && <>{error && <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>}</>}
        {children}
    </span>
);

export { VerticalEntryWrapper };
