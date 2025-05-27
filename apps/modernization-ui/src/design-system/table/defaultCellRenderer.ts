import { Fragment, createElement, ReactNode } from 'react';
import { internalizeDate } from 'date';

const defaultCellRenderer = <V>(value?: V): ReactNode | undefined => {
    if (typeof value === 'string') {
        return value;
    } else if (value instanceof Date) {
        return internalizeDate(value);
    } else if (value !== null && value !== undefined) {
        return createElement(Fragment, {}, `${value}`);
    }
};

export { defaultCellRenderer };
