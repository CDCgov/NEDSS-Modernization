import { Button } from '@trussworks/react-uswds';
import { useRedirect } from './useRedirect';
import { Destination } from './Destination';

type Props = {
    url: string;
    className?: string;
    outline?: boolean;
    /**
     * The destination for the resolved location
     */
    destination?: Destination;
} & JSX.IntrinsicElements['button'];

/**
 * A USWDS styled button that interacts with specialized API endpoints which prepare the NBS6 session and then provides
 *  a url to redirect to within the {@code Location} header of the response.  This interaction pattern has since been
 * replaced by a standard redirect issued from the response.
 *
 * This component exists to maintain compatibility with API's that provide this type of interaction and should only be
 * used with those API endpoints.
 *
 * @param {Props} props
 */
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
