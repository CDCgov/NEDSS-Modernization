import { Link } from '@trussworks/react-uswds';
import { useRedirect, redirectTo, navigateTo } from './useRedirect';
import { useEffect } from 'react';

type Props = {
    url: string;
} & JSX.IntrinsicElements['a'];

export const ClassicLink = ({ url, children, ...defaultProps }: Props) => {
    const { redirect, dispatch } = useRedirect();

    const handle = () => {
        redirectTo(url, dispatch);
    };

    useEffect(() => {
        if (redirect.location) {
            navigateTo(redirect.location);
        }
    }, [redirect.location]);

    return (
        <Link href="#" onClick={handle} {...defaultProps} className={'profile'}>
            {children}
        </Link>
    );
};
