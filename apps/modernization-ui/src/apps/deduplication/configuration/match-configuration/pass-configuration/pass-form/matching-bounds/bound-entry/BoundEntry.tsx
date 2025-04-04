import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { Icon } from 'design-system/icon';
import { Numeric } from 'design-system/input/numeric/Numeric';
import RichTooltip from 'design-system/richTooltip/RichTooltip';
import { ReactNode, useRef } from 'react';

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
        <>
            <label htmlFor={name}>
                {label}
                <div ref={lowerBoundRef} style={{ position: 'relative' }}>
                    <Icon name="info_outline" sizing="small" />
                    <RichTooltip marginTop={25} marginLeft={10} anchorRef={lowerBoundRef}>
                        {tooltip}
                    </RichTooltip>
                </div>
            </label>
            <Numeric
                inputMode="decimal"
                id={name}
                value={value}
                disabled={disabled}
                onBlur={onBlur}
                onChange={onChange}
            />
            {error && <InlineErrorMessage id={`${name}-error`}>{error}</InlineErrorMessage>}
        </>
    );
};
