import { ReactNode } from 'react';
import { Sizing } from 'design-system/field';
import { useComponentSizing } from './useComponentSizing';

type ComponentSizingProps = {
    children: (sizing: Sizing) => ReactNode | ReactNode[];
};
const ComponentSizing = ({ children }: ComponentSizingProps) => {
    const sizing = useComponentSizing();

    return children(sizing);
};

export { ComponentSizing };
