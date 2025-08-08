import { useEffect } from 'react';
import { Button, ButtonProps } from 'design-system/button';
import { Status, useClassicModal } from './useClassicModal';

type ClassicModalButtonProps = {
    url: string;
    onClose?: () => void;
} & ButtonProps;

const ClassicModalButton = ({ url, onClose, ...remaining }: ClassicModalButtonProps) => {
    const { state, open, reset } = useClassicModal();

    useEffect(() => {
        if (state.status === Status.Closed) {
            reset();
            onClose?.();
        }
    }, [state.status]);

    return <Button {...remaining} onClick={() => open(url)} />;
};

export { ClassicModalButton };
