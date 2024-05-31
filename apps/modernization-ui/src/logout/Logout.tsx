import { LoginWrapper } from 'apps/landing/Layout/LoginWrapper';
import style from './logout.module.scss';
import { LinkButton } from 'components/button';

const Logout = () => {
    return (
        <LoginWrapper>
            <div className={style.logout}>
                <div className={style.logoutCard}>
                    <h1 className={style.logoutText}>Logout confirmation</h1>
                    <p>
                        You have successfully logged out.
                        <br /> Thank you for using NBS.
                    </p>

                    <LinkButton href="/welcome" className={style.linkButton}>
                        Return to NBS
                    </LinkButton>
                </div>
            </div>
        </LoginWrapper>
    );
};

export default Logout;
