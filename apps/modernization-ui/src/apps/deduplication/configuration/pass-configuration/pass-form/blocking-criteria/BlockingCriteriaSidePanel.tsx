import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { AttributeEntry } from '../attribute-entry/AttributeEntry';
import { SidePanel } from '../side-panel/SidePanel';
import styles from './blocking-criteria-panel.module.scss';
import { Shown } from 'conditional-render';

type Props = {
    selectedAttributes: BlockingAttribute[];
    visible: boolean;
    onChange: (attributes: BlockingAttribute[]) => void;
    onClose: () => void;
};
export const BlockingCriteriaSidePanel = ({ selectedAttributes, visible, onChange, onClose }: Props) => {
    const handleOnChange = (attribute: BlockingAttribute) => {
        if (selectedAttributes.includes(attribute)) {
            onChange([...selectedAttributes].filter((a) => a !== attribute));
        } else {
            onChange([...selectedAttributes, attribute]);
        }
    };

    return (
        <SidePanel heading="Add blocking attribute(s)" visible={visible} onClose={onClose}>
            <div className={styles.blockingCriteriaPanel}>
                <Shown when={visible}>
                    <AttributeEntry
                        name="First name"
                        description="The first 4 characters of the person's first name."
                        onChange={() => {
                            handleOnChange(BlockingAttribute.FIRST_NAME);
                        }}
                        selected={selectedAttributes.includes(BlockingAttribute.FIRST_NAME)}
                    />
                    <AttributeEntry
                        name="Last name"
                        description="The first 4 characters of the person's last name."
                        onChange={() => {
                            handleOnChange(BlockingAttribute.LAST_NAME);
                        }}
                        selected={selectedAttributes.includes(BlockingAttribute.LAST_NAME)}
                    />
                    <AttributeEntry
                        name="Date of birth"
                        description="The person's birthdate in the format YYYY-MM-DD."
                        onChange={() => {
                            handleOnChange(BlockingAttribute.DATE_OF_BIRTH);
                        }}
                        selected={selectedAttributes.includes(BlockingAttribute.DATE_OF_BIRTH)}
                    />
                    <AttributeEntry
                        name="Sex"
                        description="The person's sex in the format of M or F."
                        onChange={() => {
                            handleOnChange(BlockingAttribute.SEX);
                        }}
                        selected={selectedAttributes.includes(BlockingAttribute.SEX)}
                    />
                    <AttributeEntry
                        name="Street address 1"
                        description="The first 4 characters of the person's address."
                        onChange={() => {
                            handleOnChange(BlockingAttribute.STREET_ADDRESS);
                        }}
                        selected={selectedAttributes.includes(BlockingAttribute.STREET_ADDRESS)}
                    />
                    <AttributeEntry
                        name="Zip"
                        description="The person's 5 digit zip code."
                        onChange={() => {
                            handleOnChange(BlockingAttribute.ZIP);
                        }}
                        selected={selectedAttributes.includes(BlockingAttribute.ZIP)}
                    />
                    <AttributeEntry
                        name="Email"
                        description="The first 4 characters of the person's email address."
                        onChange={() => {
                            handleOnChange(BlockingAttribute.EMAIL);
                        }}
                        selected={selectedAttributes.includes(BlockingAttribute.EMAIL)}
                    />
                    <AttributeEntry
                        name="Phone"
                        description="The first 4 digits of the person's phone number."
                        onChange={() => {
                            handleOnChange(BlockingAttribute.PHONE);
                        }}
                        selected={selectedAttributes.includes(BlockingAttribute.PHONE)}
                    />
                </Shown>
            </div>
        </SidePanel>
    );
};
