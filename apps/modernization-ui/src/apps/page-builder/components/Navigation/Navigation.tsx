import './Navigation.scss';
import { useNavigate } from 'react-router-dom';

type Props = {
    active: string;
};

export const Navigation = ({ active }: Props) => {
    const navigate = useNavigate();
    const goBack = (string: string) => {
        navigate(`/page-builder/manage/${string}`);
    };

    return (
        <div className="navigation">
            <div className="navigation__heading">
                <h2>Page Management</h2>
            </div>
            <div className="navigation__menu">
                <div
                    className={`navigation__button ${active === 'manage-pages' ? 'active' : ''}`}
                    onClick={() => goBack('pages')}>
                    <p>Page Library</p>
                </div>

                <div
                    className={`navigation__button ${active === 'condition-library' ? 'active' : ''}`}
                    onClick={() => goBack('condition-library')}>
                    <p>Condition Library</p>
                </div>
                <div
                    className={`navigation__button ${active === 'question-library' ? 'active' : ''}`}
                    onClick={() => goBack('question-library')}>
                    <p>Question Library</p>
                </div>
                <div className="navigation__button">
                    <p>Template Library</p>
                </div>
                <div
                    className={`navigation__button ${active === 'valueset-library' ? 'active' : ''}`}
                    onClick={() => goBack('valueset-library')}>
                    <p>Value set Library</p>
                </div>
            </div>
        </div>
    );
};
