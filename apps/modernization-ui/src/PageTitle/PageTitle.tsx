import React from 'react';
import { usePageTitle } from './PageTitleContext';

type PageTitleProps = {
    title: string;
    children: React.ReactNode;
};

export const PageTitle = ({ title, children }: PageTitleProps) => {
    const { setPageTitle } = usePageTitle();
    setPageTitle(title);

    return children;
};
