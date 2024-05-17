import { ReactNode, useMemo } from 'react';

type Props = {
    when?: boolean;
    children: ReactNode;
};

const Shown = ({ when = false, children }: Props) => useMemo(() => when && children, [when]);

export { Shown };
