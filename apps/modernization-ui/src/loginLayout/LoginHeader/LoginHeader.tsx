import classNames from 'classnames';
import style from './loginHeader.module.scss';

export const LoginHeader = () => {
    return (
        <div className={classNames(style.header)}>
            <img src="/nedssLogo.jpeg" height={40} alt="" />
            Welcome to the NBS demo site (Version 7.x)
        </div>
    );
};
