import { PageBuilderNavigation } from '../../components/Navigation/PageBuilderNavigation';
import './PageBuilder.scss';

type Props = {
    page: string;
    children: any;
    menu?: boolean;
};

export const PageBuilder = ({ children }: Props) => {
    return (
        <div className="page-builder">
            <div>
                <PageBuilderNavigation />
            </div>
            <div className="page-builder__content">{children}</div>
        </div>
    );
};
