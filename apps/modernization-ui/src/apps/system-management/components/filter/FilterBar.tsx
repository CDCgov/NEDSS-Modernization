import React from 'react';
import styles from './FilterBar.module.scss';

type Props = {
    value: string;
    onChange: (val: string) => void;
    placeholder?: string;
};

export const FilterBar: React.FC<Props> = ({ value, onChange, placeholder }) => (
    <div className={styles['filter-bar']}>
        <input
            type="text"
            value={value}
            onChange={(e) => onChange(e.target.value)}
            placeholder={placeholder ?? 'Filter...'}
        />
    </div>
);
