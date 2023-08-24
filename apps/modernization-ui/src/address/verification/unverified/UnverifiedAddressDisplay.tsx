import { Note } from 'components/Note';

const UnverifiedAddressDisplay = () => (
    <>
        <p id="unverified-address-description">
            We were unable to find a valid address matching what was entered. Are you sure you want to continue?
        </p>
        <div>
            <Note>You may go back to modify the address.</Note>
        </div>
    </>
);

export { UnverifiedAddressDisplay };
