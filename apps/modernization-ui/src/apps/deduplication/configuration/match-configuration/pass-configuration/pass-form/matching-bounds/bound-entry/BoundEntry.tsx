import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { Icon } from 'design-system/icon';
import { Numeric } from 'design-system/input/numeric/Numeric';
import { ReactNode, useRef } from 'react';
import RichTooltip from 'design-system/richTooltip/RichTooltip';
import styles from './bound-entry.module.scss';
import classNames from 'classnames';

type Props = {
    name: string;
    label: string;
    error?: string;
    onBlur: () => void;
    onChange: () => void;
    value?: number;
    tooltip: ReactNode;
    disabled?: boolean;
};
export const BoundEntry = ({ name, label, value, error, tooltip, disabled = false, onBlur, onChange }: Props) => {
    const lowerBoundRef = useRef(null);

    return (
        <div className={classNames(styles.boundEntry, error ? styles.hasError : '')}>
            <label htmlFor={name}>
                {label}
                <div ref={lowerBoundRef} style={{ position: 'relative' }}>
                    <Icon name="info_outline" sizing="small" />
                    <RichTooltip marginTop={25} marginLeft={10} anchorRef={lowerBoundRef}>
                        {tooltip}
                    </RichTooltip>
                </div>
            </label>
            <div>
                {error && <InlineErrorMessage id={`${name}-error`}>{error}</InlineErrorMessage>}
                <Numeric
                    inputMode="decimal"
                    id={name}
                    value={value}
                    disabled={disabled}
                    onBlur={onBlur}
                    onChange={onChange}
                />
            </div>
        </div>
    );
};
