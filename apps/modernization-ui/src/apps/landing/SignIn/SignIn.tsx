import { useEffect } from 'react';
import { Button } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { AlertBanner } from 'alert';
import { LinkButton } from 'components/button';
import { useSkipLink } from 'SkipLink/SkipLinkContext';

import styles from './signIn.module.scss';

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
            <LinkButton id="login" href="/nbs/login" type="solid" className={styles['sign-in']} target="_self">
                Login to NBS demo site
            </LinkButton>
            <div className={classNames(styles.participation)}>
                Want to participate?
                <Button onClick={() => handleWelcomeEvent?.('signUp')} type="button" unstyled>
                    Sign up for demo access
                </Button>
            </div>
        </div>
    );
};
