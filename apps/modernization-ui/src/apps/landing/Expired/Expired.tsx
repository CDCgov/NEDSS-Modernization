import { LoginWrapper } from '../Layout/LoginWrapper';
import styles from './Expired.module.scss';

export const Expired = () => {
    return (
        <LoginWrapper>
            <div className={styles.expired}>
                <h1>Session time-out</h1>
                <p>
                    Your session has expired.
                    <br />
                    If you received this message after clicking Submit, any changes you made were not saved. Please
                    reconnect to NBS by clicking the link below.
                </p>
                <p>
                    <a href="/login">Return to NBS</a>
                </p>
            </div>
        </LoginWrapper>
    );
};
