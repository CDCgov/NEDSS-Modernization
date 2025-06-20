import { ReactNode } from 'react';
import { NoData } from './NoData';

type OrElseNoDataProps = {
    children: ReactNode;
};

const OrElseNoData = ({ children }: OrElseNoDataProps) => {
    return <>{children ? children : <NoData />}</>;
};

export { OrElseNoData };
