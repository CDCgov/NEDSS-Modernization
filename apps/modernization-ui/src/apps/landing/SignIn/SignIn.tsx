import { Button } from '@trussworks/react-uswds';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import classNames from 'classnames';
import styles from './signIn.module.scss';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';

export type SignInProps = {
    handleWelcomeEvent?: (value: string) => void;
};

export const SignIn = ({ handleWelcomeEvent }: SignInProps) => {
    return (
        <div className="">
            <h2 className={classNames(styles.heading)}>Login</h2>
            <div className={classNames(styles['alert-banner'])}>
                <AlertBanner type="info">
                    <p>
                        Please be sure to avoid entering any real PHI/PIl data on the demo site. All information entered
                        will be viewable by other users.
                    </p>
                </AlertBanner>
            </div>
            <NavLinkButton to="/nbs/login" className="margin-top-2">
                Login to NBS demo site
            </NavLinkButton>

            <div className={classNames(styles.signUpDemoText)}>
                Want to participate?
                <Button
                    onClick={() => handleWelcomeEvent?.('signUp')}
                    type="button"
                    unstyled
                    className={classNames(styles.signUpButton)}>
                    Sign up for demo access
                </Button>
            </div>
        </div>
    );
};