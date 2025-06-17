import { AvailableQuestion } from 'apps/page-builder/generated';
import { useState } from 'react';
import { TabbedButtonBar } from 'apps/page-builder/components/TabbedButtonBar/TabbedButtonBar';
import styles from './expanded-question.module.scss';
import { NoData } from 'design-system/data';

type Props = {
    question: AvailableQuestion;
};

const Entry = ({ heading, value }: { heading: string; value?: string }) => {
    return (
        <div className={styles.property}>
            <div className={styles.fieldHeading}>{heading}</div>
            <div className={styles.fieldValue}>{value?.length ? value : <NoData />}</div>
        </div>
    );
};

const BasicInfo = ({ question }: Props) => {
    return (
        <>
            <Entry heading="Question type" value={question.type} />
            <Entry heading="Status" value={question.status} />
            <Entry heading="Datatype" value={question.dataType} />
            <Entry heading="Allow for entry of other value" value={question.allowOtherValues ? 'Yes' : 'No'} />
            <Entry heading="Unique ID" value={question.uniqueId} />
            <Entry heading="Subgroup" value={question.subgroupName} />
            {question.valuesetName != undefined && <Entry heading="Value set" value={question.valuesetName} />}
            <Entry heading="Unique name" value={question.uniqueName} />
            <Entry heading="Description" value={question.description} />
        </>
    );
};

const UserInterface = ({ question }: Props) => {
    return (
        <>
            <Entry heading="Label on screen" value={question.label} />
            <Entry heading="Tooltip" value={question.tooltip} />
            <Entry heading="Display control" value={question.displayControlName} />
        </>
    );
};

const DataMart = ({ question }: Props) => {
    return (
        <>
            <Entry heading="Default Label in Report" value={question.defaultLabelInReport} />
            <Entry heading="Default RDB table name" value={question.rdbTableName} />
            <Entry heading="RDB column name" value={question.rdbColumnName} />
            <Entry heading="Data mart column name" value={question.datamartColumn} />
        </>
    );
};

const Messaging = ({ question }: Props) => {
    return (
        <>
            <Entry heading="Included in message" value={question.includedInMessage ? 'Yes' : 'No'} />
            {question.includedInMessage && (
                <>
                    <Entry heading="Message variable ID" value={question.messageVariableId} />
                    <Entry heading="Code system name" value={question.codeSystemName} />
                    <Entry heading="Required in message" value={question.requiredInMessage ? 'Yes' : 'No'} />
                    <Entry heading="HL7 data type" value={question.hl7DataType} />
                    <Entry heading="HL7 Segment" value={question.hl7Segment} />
                </>
            )}
        </>
    );
};

const Administrative = ({ question }: Props) => {
    return <Entry heading="Administrative comments" value={question.adminComments} />;
};

enum Tab {
    BASIC = 'Basic',
    USER_INTERFACE = 'User interface',
    DATA_MART = 'Data mart',
    MESSAGING = 'Messaging',
    ADMIN = 'Administrative'
}
export const ExpandedQuestion = ({ question }: Props) => {
    const [active, setActive] = useState<Tab>(Tab.BASIC);

    const showPanel = (tab: Tab) => {
        switch (tab) {
            case Tab.BASIC:
                return <BasicInfo question={question} />;
            case Tab.USER_INTERFACE:
                return <UserInterface question={question} />;
            case Tab.DATA_MART:
                return <DataMart question={question} />;
            case Tab.MESSAGING:
                return <Messaging question={question} />;
            case Tab.ADMIN:
                return <Administrative question={question} />;
        }
    };

    return (
        <div className={styles.expandedQuestion}>
            <div className={styles.buttonBar}>
                <TabbedButtonBar onChange={(e) => setActive(e as Tab)} entries={Object.values(Tab)} />
            </div>
            <div className={styles.content}>{showPanel(active)}</div>
        </div>
    );
};
