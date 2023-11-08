import { Icon } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router-dom';
import './PagesBreadcrumb.scss';

type Props = {
    currentPage?: string;
};

export const PagesBreadcrumb = ({ currentPage }: Props) => {
    const navigate = useNavigate();
    const goBack = () => {
        navigate(-1);
    };

    return (
        <div className="pages-breadcrumb" onClick={goBack}>
            <Icon.ArrowBack size={3} />
            <h4>Page library</h4>
            {currentPage ? <h5>{currentPage}</h5> : null}
        </div>
    );
};
