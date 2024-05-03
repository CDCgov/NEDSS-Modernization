import { Button } from '@trussworks/react-uswds';
import classNames from 'classnames';
import styles from './signIn.module.scss';
import { LinkButton } from 'components/button';
import { AlertBanner } from 'alert';

export type SignInProps = {
    handleWelcomeEvent?: (value: string) => void;
};

export const SignIn = ({ handleWelcomeEvent }: SignInProps) => {
    return (
        <div className="">
            <h2 className={classNames(styles.heading)}>Login</h2>
            <div className={classNames(styles['alert-banner'])}>
                <AlertBanner type="info" noIcon>
                    <p>
                        Please be sure to avoid entering any real PHI/PIl data on the demo site. All information entered
                        will be viewable by other users.
                    </p>
                </AlertBanner>
            </div>
            <LinkButton href="/nbs/login" type="solid" className="margin-top-2">
                Login to NBS demo site
            </LinkButton>
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
