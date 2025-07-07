import { ReactNode } from 'react';
import { exists } from 'utils/exists';
import { NoData } from './NoData';

type OrElseNoDataProps = {
    children: ReactNode;
};

/**
 * Renders the children component or the "no data" placeholder of "---" if it does not {@link exists}.
 *
 * @param {OrElseNoDataProps}
 * @return {ReactNode}
 */
const OrElseNoData = ({ children }: OrElseNoDataProps) => (exists(children) ? children : <NoData />);

export { OrElseNoData };
