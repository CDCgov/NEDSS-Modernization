import { Fragment, createElement, ReactNode } from 'react';
import { internalizeDate } from 'date';
import { CellValue } from './DataTable';

const defaultCellValueRenderer = (value?: CellValue): ReactNode | undefined => {
    if (typeof value === 'string') {
        return value;
    } else if (value instanceof Date) {
        return internalizeDate(value);
    } else {
        return createElement(Fragment, {}, value);
    }
};

export { defaultCellValueRenderer };
