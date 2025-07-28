import { Destination } from './Destination';
import { Button, StandardButtonProps } from 'design-system/button';
import { Status, useClassicModal } from './useClassicModal';
import { useEffect } from 'react';

type Props = {
    url: string;
    destination?: Destination;
    onClose?: () => void;
} & JSX.IntrinsicElements['button'] &
    StandardButtonProps;

const ClassicModalButton = ({
    url,
    className,
    sizing,
    icon,
    active,
    tertiary,
    secondary,
    destructive,
    disabled,
    children,
    onClose,
    ...remaining
}: Props) => {
    // const { location, redirect } = useRedirect({ destination: 'none' });

    const { state, open, reset } = useClassicModal();

    const handleClick = () => {
        open(url);
    };

    // const handleKeyDown = (event: KeyboardEvent) => {
    //     switch (event.key) {
    //         case 'enter':
    //         case 'space': {
    //             event.preventDefault();
    //             redirect(url);
    //         }
    //     }
    // };

    // useEffect(() => {
    //     if (location) {
    //         open(location);
    //     }
    // }, [location]);

    useEffect(() => {
        if (state.status === Status.Closed) {
            reset();
            onClose && onClose();
        }
    }, [state.status]);

    const labelPosition = 'labelPosition' in remaining ? remaining.labelPosition : undefined;

    return (
        <a href={url} target="popup" onClick={handleClick}>
            <Button
                className={className}
                sizing={sizing}
                icon={icon}
                labelPosition={labelPosition}
                active={active}
                tertiary={tertiary}
                secondary={secondary}
                destructive={destructive}
                disabled={disabled}>
                {children}
            </Button>
        </a>
    );
};

export { ClassicModalButton };
