import { Navigation } from '../../components/Navigation/Navigation';
import './PageBuilder.scss';

type Props = {
    page: string;
    children: any;
    menu?: boolean;
};

export const PageBuilder = ({ page, children }: Props) => {
    return (
        <div className="page-builder">
            <div className="page-builder__navigation">
                <Navigation active={page}></Navigation>
            </div>
            <div className="page-builder__content">{children}</div>
        </div>
    );
};
