import { Icon } from '@trussworks/react-uswds';
import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Card } from 'design-system/card';
import { useFormContext, useWatch } from 'react-hook-form';
import { BlockingCriteriaAttribute } from './attribute/BlockingCriteriaAttribute';
import styles from './blocking-criteria.module.scss';

type Props = {
    onAddAttributes: () => void;
};
export const BlockingCriteria = ({ onAddAttributes: onShowAttributes }: Props) => {
    const form = useFormContext<{ blockingCriteria: BlockingAttribute[] }>();
    const registeredBlockingCriteria = form.register('blockingCriteria', { required: true, minLength: 1 });
    const { blockingCriteria } = useWatch(form);

    const handleRemoveAttribute = (attribute: BlockingAttribute) => {
        const value = [...(blockingCriteria ?? [])].filter((a) => a !== attribute);

        registeredBlockingCriteria.onChange({
            target: { name: 'blockingCriteria', value: value }
        });
    };

    return (
        <Card
            id="blockingCriteriaCard"
            title="1. Blocking criteria"
            subtext="Include records that meet all these conditions">
            <div className={styles.blockingCriteria}>
                <Shown
                    when={blockingCriteria && blockingCriteria.length > 0}
                    fallback={
                        <div className={styles.noBlockingCriteriaText}>
                            Please add blocking criteria to get started.
                        </div>
                    }>
                    <BlockingCriteriaAttribute
                        label="First name"
                        description="The first 4 characters of the patient's first name"
                        attribute={BlockingAttribute.FIRST_NAME}
                        onRemove={handleRemoveAttribute}
                    />
                    <BlockingCriteriaAttribute
                        label="Last name"
                        description="The first 4 characters of the patient's last name"
                        attribute={BlockingAttribute.LAST_NAME}
                        onRemove={handleRemoveAttribute}
                    />
                    <BlockingCriteriaAttribute
                        label="Date of birth"
                        description="The person's birthdate in the format YYYY-MM-DD."
                        attribute={BlockingAttribute.BIRTHDATE}
                        onRemove={handleRemoveAttribute}
                    />
                    <BlockingCriteriaAttribute
                        label="Sex"
                        description="The person's sex in the format of M or F."
                        attribute={BlockingAttribute.SEX}
                        onRemove={handleRemoveAttribute}
                    />
                    <BlockingCriteriaAttribute
                        label="Street address 1"
                        description="The first 4 characters of the person's address."
                        attribute={BlockingAttribute.ADDRESS}
                        onRemove={handleRemoveAttribute}
                    />
                    <BlockingCriteriaAttribute
                        label="Zip"
                        description="The person's 5 digit zip code."
                        attribute={BlockingAttribute.ZIP}
                        onRemove={handleRemoveAttribute}
                    />
                    <BlockingCriteriaAttribute
                        label="Email"
                        description="The first 4 characters of the person's email address."
                        attribute={BlockingAttribute.EMAIL}
                        onRemove={handleRemoveAttribute}
                    />
                    <BlockingCriteriaAttribute
                        label="Phone"
                        description="The first 4 digits of the person's phone number."
                        attribute={BlockingAttribute.PHONE}
                        onRemove={handleRemoveAttribute}
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
        </Card>
    );
};
