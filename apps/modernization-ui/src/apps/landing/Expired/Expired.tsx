import { LoginWrapper } from 'apps/landing/Layout/LoginWrapper';
import { ReturnMessage } from 'system/return-message';

export const Expired = () => {
    fetch('/logout');
    return (
        <LoginWrapper>
            <ReturnMessage title="Session time-out">
                Your session has expired.
                <br />
                If you received this message after clicking Submit, any changes you made were not saved. Please
                reconnect to NBS by clicking the link below.
            </ReturnMessage>
        </LoginWrapper>
    );
};
