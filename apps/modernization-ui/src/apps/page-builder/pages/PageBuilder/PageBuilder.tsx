import { Navigation } from '../../components/Navigation/Navigation';
import './PageBuilder.scss';

type Props = {
    page: string;
    children: any;
    menu?: boolean;
};

export const PageBuilder = ({ page, children, menu }: Props) => {
    return (
        <div className="page-builder">
            <Navigation status={page} menu={menu}></Navigation>
            <div className="page-builder__content">{children}</div>
        </div>
    );
};
