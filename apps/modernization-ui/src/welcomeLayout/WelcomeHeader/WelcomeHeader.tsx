import style from './welcomeHeader.module.scss';

export const WelcomeHeader = () => {
    return (
        <div className={style.header}>
            <img src="/nbs-logo.png" height={40} alt="" />
            Welcome to the NBS7 demo site
        </div>
    );
};
