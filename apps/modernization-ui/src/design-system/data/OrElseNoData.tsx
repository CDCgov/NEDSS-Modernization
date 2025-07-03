import { ReactNode } from 'react';
import { NoData } from './NoData';

type OrElseNoDataProps = {
    children: ReactNode;
};

const OrElseNoData = ({ children }: OrElseNoDataProps) => (children ? children : <NoData />);

export { OrElseNoData };
