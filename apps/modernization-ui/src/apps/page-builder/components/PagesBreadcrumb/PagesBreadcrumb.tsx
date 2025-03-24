import { Icon } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router';
import './PagesBreadcrumb.scss';

type Props = {
    currentPage?: string;
    path?: string;
};

export const PagesBreadcrumb = ({ currentPage, path }: Props) => {
    const navigate = useNavigate();
    const goBack = () => {
        if (path) {
            navigate(path);
        } else {
            navigate(-1);
        }
    };

    return (
        <div className="pages-breadcrumb">
            <div onClick={goBack} id="pageLibraryLink">
                <Icon.ArrowBack size={3} />
                <h4>Page library</h4>
            </div>
            {currentPage ? <h5>/ {currentPage}</h5> : null}
        </div>
    );
};
