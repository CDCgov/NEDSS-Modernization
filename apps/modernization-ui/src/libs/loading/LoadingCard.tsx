import { Loading } from 'components/Spinner';
import { Card, CardProps } from 'design-system/card';
import { ReactNode } from 'react';

type LoadingCardProps = { children?: ReactNode } & Omit<CardProps, 'children'>;

const LoadingCard = ({ children = <></>, ...remaining }: LoadingCardProps) => {
    return (
        <Card {...remaining} flair={<Loading />}>
            {children}
        </Card>
    );
};

export { LoadingCard };
