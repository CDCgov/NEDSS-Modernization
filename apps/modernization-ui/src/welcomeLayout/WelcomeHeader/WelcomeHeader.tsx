import classNames from 'classnames';
import style from './welcomeHeader.module.scss';

export const WelcomeHeader = () => {
    return (
        <div className={classNames(style.header)}>
            <img src="/nedssLogo.jpeg" height={40} alt="" />
            Welcome to the NBS demo site (Version 7.x)
        </div>
    );
};
