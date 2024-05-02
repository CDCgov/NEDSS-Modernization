import { LoginWrapper } from 'apps/landing/Layout/LoginWrapper';
import style from './logout.module.scss';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';

const Logout = () => {
    return (
        <LoginWrapper>
            <div className={style.logout}>
                <div className={style.logoutCard}>
                    <h2 className={style.logoutText}>Logout confirmation</h2>
                    <p>
                        You have successfully logged out.
                        <br /> Thank you for using NBS.
                    </p>

                    <NavLinkButton to="/nbs/login" className={style.linkButton}>
                        Return to NBS
                    </NavLinkButton>
                </div>
            </div>
        </LoginWrapper>
    );
};

export default Logout;
