import { Status, useClassicModal } from 'classic';
import { useRedirect } from './useRedirect';
import { KeyboardEvent as ReactKeyboardEvent, MouseEvent as ReactMouseEvent, useEffect } from 'react';
import { Destination } from './Destination';

type Props = {
    url: string;
    destination?: Destination;
    onClose?: () => void;
} & JSX.IntrinsicElements['a'];

export const ClassicModalLink = ({ url, destination = 'current', onClose, children, ...defaultProps }: Props) => {
    const { location, redirect } = useRedirect({ destination });

    const { state, open, reset } = useClassicModal();

    const handleClick = (event: ReactMouseEvent) => {
        event.preventDefault();
        redirect(url);
    };

    const handleKeyDown = (event: ReactKeyboardEvent) => {
        switch (event.key) {
            case 'enter':
            case 'space': {
                event.preventDefault();
                redirect(url);
            }
        }
    };

    useEffect(() => {
        if (location) {
            open(location);
        }
    }, [location]);

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
