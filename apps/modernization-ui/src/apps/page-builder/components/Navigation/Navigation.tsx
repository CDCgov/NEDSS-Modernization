import './Navigation.scss';

type Props = {
    status: string;
};

export const Navigation = ({ status }: Props) => {
    return (
        <div className="navigation">
            <div className="navigation__heading">
                <h1>Page builder</h1>
            </div>
            <div className="navigation__menu">
                <div className={`navigation__button ${status ? 'active' : ''}`}>
                    <p>Pages</p>
                </div>
                <div className="navigation__button">
                    <p>Templates</p>
                </div>
                <div className="navigation__button">
                    <p>Conditions</p>
                </div>
                <div className="navigation__button">
                    <p>Questions</p>
                </div>
                <div className="navigation__button">
                    <p>Value sets</p>
                </div>
            </div>
        </div>
    );
};
