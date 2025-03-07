import { useFormContext, useWatch } from 'react-hook-form';
import { AttributeEntry } from '../attribute-entry/AttributeEntry';
import styles from './blocking-criteria-selection.module.scss';
import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { useEffect, useState } from 'react';

export const BlockingCriteriaSelection = () => {
    const form = useFormContext<{ blockingCriteria: BlockingAttribute[] }>();
    const { blockingCriteria } = useWatch(form);
    const [selected, setSelected] = useState<BlockingAttribute[]>([]);

    useEffect(() => {
        if (blockingCriteria) {
            setSelected(blockingCriteria);
        }
    }, [blockingCriteria]);

    const handleOnChange = (attribute: BlockingAttribute) => {
        if (selected.includes(attribute)) {
            setSelected([...selected].filter((a) => a !== attribute));
        } else {
            setSelected([...selected, attribute]);
        }
    };

    return (
        <div className={styles.blockingCriteriaSelection}>
            <AttributeEntry
                name="First name"
                description="The first 4 characters of the person's first name."
                onChange={() => {
                    handleOnChange(BlockingAttribute.FIRST_NAME);
                }}
                selected={selected.includes(BlockingAttribute.FIRST_NAME)}
            />
            <AttributeEntry
                name="Last name"
                description="The first 4 characters of the person's last name."
                onChange={() => {
                    handleOnChange(BlockingAttribute.LAST_NAME);
                }}
                selected={selected.includes(BlockingAttribute.LAST_NAME)}
            />
            <AttributeEntry
                name="Date of birth"
                description="The person's birthdate in the format YYYY-MM-DD."
                onChange={() => {
                    handleOnChange(BlockingAttribute.DATE_OF_BIRTH);
                }}
                selected={selected.includes(BlockingAttribute.DATE_OF_BIRTH)}
            />
            <AttributeEntry
                name="Sex"
                description="The person's sex in the format of M or F."
                onChange={() => {
                    handleOnChange(BlockingAttribute.SEX);
                }}
                selected={selected.includes(BlockingAttribute.SEX)}
            />
            <AttributeEntry
                name="Street address 1"
                description="The first 4 characters of the person's address."
                onChange={() => {
                    handleOnChange(BlockingAttribute.STREET_ADDRESS);
                }}
                selected={selected.includes(BlockingAttribute.STREET_ADDRESS)}
            />
            <AttributeEntry
                name="Zip"
                description="The person's 5 digit zip code."
                onChange={() => {
                    handleOnChange(BlockingAttribute.ZIP);
                }}
                selected={selected.includes(BlockingAttribute.ZIP)}
            />
            <AttributeEntry
                name="Email"
                description="The first 4 characters of the person's email address."
                onChange={() => {
                    handleOnChange(BlockingAttribute.EMAIL);
                }}
                selected={selected.includes(BlockingAttribute.EMAIL)}
            />
            <AttributeEntry
                name="Phone"
                description="The first 4 digits of the person's phone number."
                onChange={() => {
                    handleOnChange(BlockingAttribute.PHONE);
                }}
                selected={selected.includes(BlockingAttribute.PHONE)}
            />
        </div>
    );
};
