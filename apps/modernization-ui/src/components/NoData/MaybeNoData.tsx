import React from 'react';
import { NoData, NoDataProps } from './NoData';

type MaybeNoDataProps = NoDataProps & {
    children: React.ReactNode;
};

const MaybeNoData = ({ children, ...noDataProps }: MaybeNoDataProps) => {
    if (children === null || children === '' || (typeof children === 'string' && children.trim() === '')) {
        return <NoData {...noDataProps} />;
    }
    return <>{children}</>;
};

export { MaybeNoData };
