import { Icon } from '@trussworks/react-uswds';
import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useFormContext, useWatch } from 'react-hook-form';
import styles from './blocking-criteria.module.scss';

type Props = {
    onShowAttributes: () => void;
};
export const BlockingCriteria = ({ onShowAttributes }: Props) => {
    const form = useFormContext<{ blockingCriteria: BlockingAttribute[] }>();
    const { blockingCriteria } = useWatch(form);

    const handleRemoveAttribute = (attribute: BlockingAttribute) => {
        const current = [...(blockingCriteria ?? [])];
        form.setValue(
            'blockingCriteria',
            current.filter((a) => a !== attribute)
        );
    };

    return (
        <div className={styles.blockingCriteria}>
            <div className={styles.heading}>
                <Heading level={2}>1. Blocking criteria</Heading>
                <span>Include records that meet all these conditions</span>
            </div>
            <div className={styles.body}>
                <Shown
                    when={blockingCriteria && blockingCriteria.length > 0}
                    fallback={
                        <div className={styles.noBlockingCriteriaText}>Please add blocking criteria to get started</div>
                    }>
                    <BlockingCriteriaAttribute
                        label="First name"
                        description="The first 4 characters of the patient's first name"
                        visible={blockingCriteria?.includes(BlockingAttribute.FIRST_NAME) ?? false}
                        onRemove={() => handleRemoveAttribute(BlockingAttribute.FIRST_NAME)}
                    />
                    <BlockingCriteriaAttribute
                        label="Last name"
                        description="The first 4 characters of the patient's last name"
                        visible={blockingCriteria?.includes(BlockingAttribute.LAST_NAME) ?? false}
                        onRemove={() => handleRemoveAttribute(BlockingAttribute.LAST_NAME)}
                    />
                    <BlockingCriteriaAttribute
                        label="Date of birth"
                        description="The person's birthdate in the format YYYY-MM-DD."
                        visible={blockingCriteria?.includes(BlockingAttribute.DATE_OF_BIRTH) ?? false}
                        onRemove={() => handleRemoveAttribute(BlockingAttribute.DATE_OF_BIRTH)}
                    />
                    <BlockingCriteriaAttribute
                        label="Sex"
                        description="The person's sex in the format of M or F."
                        visible={blockingCriteria?.includes(BlockingAttribute.SEX) ?? false}
                        onRemove={() => handleRemoveAttribute(BlockingAttribute.SEX)}
                    />
                    <BlockingCriteriaAttribute
                        label="Street address 1"
                        description="The first 4 characters of the person's address."
                        visible={blockingCriteria?.includes(BlockingAttribute.STREET_ADDRESS) ?? false}
                        onRemove={() => handleRemoveAttribute(BlockingAttribute.STREET_ADDRESS)}
                    />
                    <BlockingCriteriaAttribute
                        label="Zip"
                        description="The person's 5 digit zip code."
                        visible={blockingCriteria?.includes(BlockingAttribute.ZIP) ?? false}
                        onRemove={() => handleRemoveAttribute(BlockingAttribute.ZIP)}
                    />
                    <BlockingCriteriaAttribute
                        label="Email"
                        description="The first 4 characters of the person's email address."
                        visible={blockingCriteria?.includes(BlockingAttribute.EMAIL) ?? false}
                        onRemove={() => handleRemoveAttribute(BlockingAttribute.EMAIL)}
                    />
                    <BlockingCriteriaAttribute
                        label="Phone"
                        description="The first 4 digits of the person's phone number."
                        visible={blockingCriteria?.includes(BlockingAttribute.PHONE) ?? false}
                        onRemove={() => handleRemoveAttribute(BlockingAttribute.PHONE)}
                    />
                </Shown>
                <div className={styles.buttonContainer}>
                    <Button
                        icon={<Icon.Add size={3} />}
                        labelPosition="right"
                        outline
                        onClick={onShowAttributes}
                        sizing="small"
                        className={styles.addButton}>
                        Add blocking attribute(s)
                    </Button>
                </div>
            </div>
        </div>
    );
};

type AttributeProps = {
    label: string;
    description: string;
    visible: boolean;
    onRemove: () => void;
};
const BlockingCriteriaAttribute = ({ label, description, visible, onRemove }: AttributeProps) => {
    return (
        <Shown when={visible}>
            <div className={styles.attribute}>
                <div>
                    <div className={styles.label}>{label}</div>
                    <div className={styles.description}>{description}</div>
                </div>
                <div className={styles.deleteButton}>
                    <Button outline onClick={onRemove}>
                        <Icon.Delete size={3} />
                    </Button>
                </div>
            </div>
        </Shown>
    );
};
