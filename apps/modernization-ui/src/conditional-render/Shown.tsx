import { ReactNode } from 'react';

type Props = {
    when?: boolean;
    children: ReactNode;
    fallback?: ReactNode;
};

const Shown = ({ when = false, children, fallback = undefined }: Props) => {
    return <>{when ? children : fallback}</>;
};

export { Shown };
