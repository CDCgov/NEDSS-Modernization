import { Icon } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router-dom';
import './Breadcrumb.scss';

type Props = {
    currentPage?: string;
    link?: string;
    header?: string;
    className?: string;
};

export const Breadcrumb = ({ currentPage, link, header, className }: Props) => {
    const navigate = useNavigate();
    const goBack = () => {
        history.go(-1);
        navigate(link || '');
    };

    return (
        <div className={`pages-breadcrumb ${className || ''}`} onClick={goBack}>
            <Icon.ArrowBack size={3} />
            <h4>{header}</h4>
            {currentPage ? <h5>{currentPage}</h5> : null}
        </div>
    );
};
