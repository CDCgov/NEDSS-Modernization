import { ChangeEvent } from 'react';

import './ToggleBtn.scss';

type Props = {
    checked?: boolean;
    disabled?: boolean;
    defaultChecked?: boolean;
    name?: string;
    className?: string;
    id?: string;
    onChange?: (e: ChangeEvent<HTMLInputElement>) => void;
};

export const ToggleButton = ({ checked, className, ...props }: Props) => {
    return (
        <div className={`toggle-btn ${className}`}>
            <label className="switch">
                <input type="checkbox" {...props} checked={checked} />
                <span className="slider round"></span>
            </label>
        </div>
    );
};
