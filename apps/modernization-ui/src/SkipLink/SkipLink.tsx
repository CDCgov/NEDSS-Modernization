import { useEffect } from 'react';

import { focusedTarget } from 'utils';

import { useSkipLink } from './SkipLinkContext';

interface SkipLinkProps {
    id: string;
    autoFocus?: boolean;
}

export const SkipLink = ({ id, autoFocus = false }: SkipLinkProps) => {
    const { skipTo, remove } = useSkipLink();

    useEffect(() => {
        skipTo(id);
        if (autoFocus) {
            focusedTarget(id);
        }

        return () => remove(id);
    }, []);

    return <></>;
};
