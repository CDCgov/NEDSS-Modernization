import React from 'react';

interface NumericInputProps {
    value?: number;
    onChange: (value: number | undefined) => void;
}

const NumericInput: React.FC<NumericInputProps> = ({ value, onChange }) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = e.target.value === '' ? undefined : parseFloat(e.target.value);
        onChange(newValue);
    };

    return <input type="number" value={value ?? ''} onChange={handleChange} placeholder="Enter value" />;
};

export default NumericInput;
