import { PageBuilderSideNav } from '../../components/Navigation/PageBuilderSideNav';
import './PageBuilder.scss';

type Props = {
    page?: string;
    children: any;
    nav?: boolean;
};

export const PageBuilder = ({ nav = false, children }: Props) => {
    return (
        <div className="page-builder">
            {nav ? (
                <div className="side-nav">
                    <PageBuilderSideNav />
                </div>
            ) : null}
            <div className="page-builder__content">{children}</div>
        </div>
    );
};
