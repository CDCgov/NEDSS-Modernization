import { Status, useClassicModal } from 'classic';
import { useEffect } from 'react';

type Props = {
    url: string;
    onClose?: () => void;
} & JSX.IntrinsicElements['a'];

export const ClassicModalLink = ({ url, onClose, children, ...defaultProps }: Props) => {
    const { state, open, reset } = useClassicModal();

    const handleClick = () => {
        open(url);
    };

    useEffect(() => {
        if (state.status === Status.Closed) {
            reset();
            onClose && onClose();
        }
    }, [state.status]);

    return (
        <a href="#" target="popup" onClick={handleClick} {...defaultProps}>
            {children}
        </a>
    );
};
