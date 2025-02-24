import React from 'react';

interface CheckboxProps {
    checked?: boolean;
    onChange: (checked: boolean) => void;
}

const Checkbox: React.FC<CheckboxProps> = ({ checked, onChange }) => {
    return <input type="checkbox" checked={checked ?? false} onChange={(e) => onChange(e.target.checked)} />;
};

export default Checkbox;
