import { Navigation } from 'components/PageBuilder/Navigation/Navigation';
import './PageBuilder.scss';

type Props = {
    page: string;
    children: any;
};

export const PageBuilder = ({ page, children }: Props) => {
    return (
        <div className="page-builder">
            <Navigation status={page}></Navigation>
            <div className="page-builder__content">{children}</div>
        </div>
    );
};
