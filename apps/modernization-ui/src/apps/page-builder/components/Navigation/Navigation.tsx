import { useNavigate } from 'react-router-dom';
import './Navigation.scss';
import { Config } from 'config';

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
                    <p>Pages</p>
                </div>
                <div className="navigation__button">
                    <a
                        href={`${Config.nbsUrl}/ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&initLoad=true`}>
                        <p>Templates</p>
                    </a>
                </div>
                <div className={`navigation__button ${active === 'condition-library' ? 'active' : ''}`}>
                    <a
                        href={`${Config.nbsUrl}/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&initLoad=true`}>
                        <p>Conditions</p>
                    </a>
                </div>
                <div className={`navigation__button ${active === 'question-library' ? 'active' : ''}`}>
                    <a href={`${Config.nbsUrl}/SearchManageQuestions.do?method=loadQuestionLibrary&initLoad=true`}>
                        <p>Questions</p>
                    </a>
                </div>
                <div className={`navigation__button ${active === 'valueset-library' ? 'active' : ''}`}>
                    <a href={`${Config.nbsUrl}/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true`}>
                        <p>Value sets</p>
                    </a>
                </div>
            </div>
        </div>
    );
};
