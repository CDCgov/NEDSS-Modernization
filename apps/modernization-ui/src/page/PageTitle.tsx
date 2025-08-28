import React, { ReactNode, useEffect } from 'react';
import { usePage } from '.';

type PageTitleProps = {
    title: string;
    children?: React.ReactNode | ReactNode[];
};

export const PageTitle = ({ title, children }: PageTitleProps) => {
    const { setTitle, resetTitle } = usePage();

    useEffect(() => {
        setTitle(title);

        return () => {
            resetTitle();
        };
    }, [title, setTitle, resetTitle]);

    return children;
};
