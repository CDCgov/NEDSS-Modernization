import { focusedTarget } from 'utils';
import { useSkipLink } from './SkipLinkContext';
import { useEffect } from 'react';

interface SkipLinkProps {
    id: string;
    focus?: boolean;
}

export const SkipLink = ({ id, focus = false }: SkipLinkProps) => {
    const { skipTo, remove } = useSkipLink();

    useEffect(() => {
        skipTo(id);
        if (focus) {
            focusedTarget(id);
        }

        return () => remove(id);
    }, []);

    return null;
};
