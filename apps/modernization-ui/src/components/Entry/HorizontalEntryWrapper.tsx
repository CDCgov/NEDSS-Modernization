import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Label } from '@trussworks/react-uswds';
import styles from './horizontal-wrapper.module.scss';
import { Tooltip, Icon } from '@trussworks/react-uswds';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    tooltip?: boolean;
    children: ReactNode;
};

const HorizontalEntryWrapper = ({ className, htmlFor, label, required, error, tooltip, children }: Props) => (
    <div className={classNames(styles.horizontalInput, className)}>
        {label && (
            <Label className={classNames({ required })} htmlFor={htmlFor}>
                {label}
            </Label>
        )}
        <div className={styles.verticalWrapper}>
            {tooltip && error && (
                <Tooltip id={htmlFor} label="blah">
                    <Icon.Info className={styles.tooltipIcon} />
                </Tooltip>
            )}
            {!tooltip && error && <ErrorMessage id={`${htmlFor}-error`}>{error}</ErrorMessage>}
            {children}
        </div>
    </div>
);

export { HorizontalEntryWrapper };
