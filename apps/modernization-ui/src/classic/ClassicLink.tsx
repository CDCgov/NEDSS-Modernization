import { useRedirect, redirectTo, navigateTo } from './useRedirect';
import { MouseEvent, useEffect } from 'react';

type Props = {
    url: string;
} & JSX.IntrinsicElements['a'];

export const ClassicLink = ({ url, children, ...defaultProps }: Props) => {
    const { redirect, dispatch } = useRedirect();

    const handle = (event: MouseEvent) => {
        event.preventDefault();
        redirectTo(url, dispatch);
    };

    useEffect(() => {
        if (redirect.location) {
            navigateTo(redirect.location);
        }
    }, [redirect.location]);

    return (
        <a href="#" onClick={handle} {...defaultProps}>
            {children}
        </a>
    );
};
