import { ReactNode } from 'react';
import { CollapsibleCard } from './collapsible';
import { CardHeader, CardHeaderProps } from './CardHeader';

type Props = {
    id: string;
    className?: string;
    children: ReactNode;
    collapsible?: boolean;
} & CardHeaderProps;
const Card = ({ id, title, info, subtext, children, className, level, collapsible = false }: Props) => {
    return (
        <CollapsibleCard
            id={id}
            className={className}
            collapsible={collapsible}
            aria-labelledby={`${id}-title`}
            header={<CardHeader id={`${id}-title`} title={title} level={level} subtext={subtext} info={info} />}>
            {children}
        </CollapsibleCard>
    );
};

export { Card };
