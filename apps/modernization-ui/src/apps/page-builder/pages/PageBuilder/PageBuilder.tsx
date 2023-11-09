import { PageBuilderSideNav } from '../../components/Navigation/PageBuilderSideNav';
import './PageBuilder.scss';

type Props = {
    page: string;
    children: any;
    menu?: boolean;
};

export const PageBuilder = ({ children }: Props) => {
    return (
        <div className="page-builder">
            <PageBuilderSideNav />
            <div className="page-builder__content">{children}</div>
        </div>
    );
};
