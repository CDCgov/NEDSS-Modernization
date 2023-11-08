import { ReactNode } from 'react';
import { Navigation } from '../../components/Navigation/Navigation';
import './PageBuilder.scss';

type Props = {
    page: string;
    children: any;
    menu?: boolean;
    header?: ReactNode;
};

export const PageBuilder = ({ page, header, children }: Props) => {
    return (
        <div className="page-builder">
            {header ? header : null}
            <div className="page-builder__wrapper">
                <div className="page-builder__navigation">
                    <Navigation active={page}></Navigation>
                </div>
                <div className="page-builder__content">{children}</div>
            </div>
        </div>
    );
};
