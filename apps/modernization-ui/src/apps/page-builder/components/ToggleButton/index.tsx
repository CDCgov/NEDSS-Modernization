import React from 'react';
import './ToggleBtn.scss';

type Props = {
    checked?: boolean;
    name?: string;
    className?: string;
    id?: string;
    onChange?: (e: any) => void;
};

export const ToggleButton = ({ checked, className, ...Props }: Props) => {
    return (
        <div className={`toggle-btn ${className}`}>
            <label className="switch">
                <input type="checkbox" {...Props} checked={checked} />
                <span className="slider round"></span>
            </label>
        </div>
    );
};
