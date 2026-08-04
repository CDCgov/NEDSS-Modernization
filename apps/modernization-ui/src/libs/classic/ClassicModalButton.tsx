import { useEffect } from 'react';

import { Button, ButtonProps } from 'design-system/button';
import { post } from 'libs/api';

import { Status, useClassicModal } from './useClassicModal';

const performAction = (action?: string) => (action ? fetch(post(action)) : Promise.resolve());

type ClassicModalButtonProps = {
    url: string;
    onClose?: () => void;
} & ButtonProps;

const ClassicModalButton = ({ url, onClose, ...remaining }: ClassicModalButtonProps) => {
    const { state, open, reset } = useClassicModal();

    useEffect(() => {
        if (state.status === Status.Closed) {
            performAction(state.action).finally(() => {
                reset();
                onClose?.();
            });
        }
    }, [state.status, state.action]);

    return <Button {...remaining} onClick={() => open(url)} />;
};

export { ClassicModalButton };
