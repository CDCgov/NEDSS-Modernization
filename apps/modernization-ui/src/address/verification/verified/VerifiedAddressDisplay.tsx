import './VerrifiedAddressDisplay.scss';
import { VerifiableAdddress } from '../VerifiableAddress';
import classNames from 'classnames';
import { Note } from 'components/Note';

type HighlightedDifferenceProps = {
    standard?: string | null;
    value?: string | null;
};

const HighlightedDifference = ({ standard, value }: HighlightedDifferenceProps) => {
    return (
        <span
            className={classNames({
                difference: standard !== value
            })}>
            {value}
        </span>
    );
};

type Props = {
    input: VerifiableAdddress;
    alternative: VerifiableAdddress;
};

const VerrifiedAddressDisplay = ({ input, alternative }: Props) => {
    return (
        <>
            <p id="verified-address-description">
                You are about to add a new patient with invalid inputs. We found a valid address. Would you like to
                update to the valid address found?
            </p>
            <div className="verified-address-detail">
                <Note>1 Valid address found below</Note>
                <div className="verified-address-selection">
                    <div>
                        <h4>Entered address:</h4>
                        <span className="address-line">
                            <HighlightedDifference value={input.address1} standard={alternative.address1} />
                        </span>
                        <span className="address-line">
                            <HighlightedDifference value={input.city} standard={alternative.city} />
                            {', '}
                            <HighlightedDifference value={input.state?.name} standard={alternative.state?.name} />{' '}
                            <HighlightedDifference value={input.zip} standard={alternative.zip} />
                        </span>
                    </div>
                    <div>
                        <h4>Valid address found:</h4>
                        <span className="address-line">{alternative.address1}</span>
                        <span className="address-line">
                            <HighlightedDifference value={alternative.city} standard={input.city} />
                            {', '}
                            <HighlightedDifference value={alternative.state?.name} standard={input.state?.name} />{' '}
                            <HighlightedDifference value={alternative.zip} standard={input.zip} />
                        </span>
                    </div>
                </div>
            </div>
        </>
    );
};

export { VerrifiedAddressDisplay };
