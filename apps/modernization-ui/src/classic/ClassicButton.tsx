import { Button } from '@trussworks/react-uswds';
import { useRedirect, redirectTo, navigateTo } from './useRedirect';
import { useEffect } from 'react';
import React from 'react';

type Props = {
    url: string;
} & JSX.IntrinsicElements['button'];

export const ClassicButton = ({ url, children, ...defaultProps }: Props) => {
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
        <Button type="button" className="grid-row" onClick={handle} {...defaultProps}>
            {children}
        </Button>
    );
};
