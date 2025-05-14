import { focusedTarget } from 'utils';
import { useSkipLink } from './SkipLinkContext';
import { useEffect } from 'react';

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
