import { LoginWrapper } from 'apps/landing/Layout/LoginWrapper';
import { ReturnMessage } from 'system/return-message';

const Logout = () => {
    return (
        <LoginWrapper>
            <ReturnMessage title="Logout confirmation">
                You have successfully logged out or your session expired.
                <br /> Thank you for using NBS.
            </ReturnMessage>
        </LoginWrapper>
    );
};

export default Logout;
