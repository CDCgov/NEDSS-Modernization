import { Button } from '@trussworks/react-uswds';
import classNames from 'classnames';
import styles from './signIn.module.scss';
import { LinkButton } from 'components/button';
import { AlertBanner } from 'alert';
import { useSkipLink } from 'SkipLink/SkipLinkContext';
import { useEffect } from 'react';

export type SignInProps = {
    handleWelcomeEvent?: (value: string) => void;
};

export const SignIn = ({ handleWelcomeEvent }: SignInProps) => {
    const { skipTo } = useSkipLink();

    useEffect(() => {
        skipTo('login');
    }, []);

    return (
        <div className="">
            <h2 className={classNames(styles.heading)}>Login</h2>
            <div className={classNames(styles['alert-banner'])}>
                <AlertBanner type="info" noIcon>
                    <p>
                        Please be sure to avoid entering any real PHI/PII data on the demo site. All information entered
                        will be viewable by other users.
                    </p>
                </AlertBanner>
            </div>
            <LinkButton id="login" href="/nbs/login" type="solid" className="margin-top-2">
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
