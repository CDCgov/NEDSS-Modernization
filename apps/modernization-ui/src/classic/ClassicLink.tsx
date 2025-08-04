import { MouseEvent } from 'react';

import { useRedirect, Destination } from './useRedirect';

type Props = {
    url: string;
    destination?: Destination;
} & JSX.IntrinsicElements['a'];

export const ClassicLink = ({ url, destination, children, ...defaultProps }: Props) => {
    const { redirect } = useRedirect({ destination });

    const handle = (event: MouseEvent) => {
        event.preventDefault();
        redirect(url);
    };

    return (
        <a href="#" onClick={handle} {...defaultProps}>
            {children}
        </a>
    );
};
