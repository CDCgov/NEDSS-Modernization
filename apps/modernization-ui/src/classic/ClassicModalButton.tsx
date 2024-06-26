import { KeyboardEvent, MouseEvent, useEffect } from 'react';
import { Button } from '@trussworks/react-uswds';
import { Status, useClassicModal } from 'classic';
import { useRedirect } from './useRedirect';
import { Destination } from './Destination';

type Props = {
    url: string;
    destination?: Destination;
    onClose?: () => void;
} & JSX.IntrinsicElements['button'];

const ClassicModalButton = ({ url, onClose, children, ...defaultProps }: Props) => {
    const { location, redirect } = useRedirect({ destination: 'none' });

    const { state, open, reset } = useClassicModal();

    const handleClick = (event: MouseEvent) => {
        event.preventDefault();
        redirect(url);
    };

    const handleKeyDown = (event: KeyboardEvent) => {
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
        <Button type="button" className="grid-row" onClick={handleClick} onKeyDown={handleKeyDown} {...defaultProps}>
            {children}
        </Button>
    );
};

export { ClassicModalButton };
