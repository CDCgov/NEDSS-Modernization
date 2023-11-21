import { Button } from '@trussworks/react-uswds';
import { useRedirect } from './useRedirect';
import { Destination } from './Destination';

type Props = {
    url: string;
    className?: string;
    outline?: boolean;
    destination?: Destination;
} & JSX.IntrinsicElements['button'];

export const ClassicButton = ({
    url,
    className,
    outline = false,
    destination = 'current',
    children,
    ...defaultProps
}: Props) => {
    const { redirect } = useRedirect({ destination });

    return (
        <Button type="button" outline={outline} className={className} onClick={() => redirect(url)} {...defaultProps}>
            {children}
        </Button>
    );
};
