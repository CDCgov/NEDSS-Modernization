import { Status, useClassicModal } from 'classic';
import { useRedirect, redirectTo } from './useRedirect';
import { KeyboardEvent, MouseEvent, useEffect } from 'react';

type Props = {
    url: string;
    onClose?: () => void;
} & JSX.IntrinsicElements['a'];

export const ClassicModalLink = ({ url, onClose, children, ...defaultProps }: Props) => {
    const { redirect, dispatch } = useRedirect();

    const { state, open, reset } = useClassicModal();

    const handleClick = (event: MouseEvent) => {
        event.preventDefault();
        redirectTo(url, dispatch);
    };

    const handleKeyDown = (event: KeyboardEvent) => {
        switch (event.key) {
            case 'enter':
            case 'space': {
                event.preventDefault();
                redirectTo(url, dispatch);
            }
        }
    };

    useEffect(() => {
        if (redirect.location) {
            open(redirect.location);
        }
    }, [redirect.location]);

    useEffect(() => {
        if (state.status === Status.Closed) {
            reset();
            onClose && onClose();
        }
    }, [state.status]);

    return (
        <a href="#" onClick={handleClick} onKeyDown={handleKeyDown} {...defaultProps}>
            {children}
        </a>
    );
};
