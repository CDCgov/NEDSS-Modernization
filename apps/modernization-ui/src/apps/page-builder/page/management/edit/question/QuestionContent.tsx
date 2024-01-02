import styles from './question-content.module.scss';
import { Input } from 'components/FormInputs/Input';
import { Heading } from 'components/heading';
import { ValueSetControllerService, Concept } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { useEffect, useState } from 'react';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Selectable } from 'options/selectable';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import { Icon } from '@trussworks/react-uswds';

type Props = {
    defaultValue: string;
    isStandard: boolean;
    name: string;
    type: string;
    displayComponent?: number;
    identifier: string;
    valueSet: string;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const participantListId = 1030;
const originalElecDocId = 1036;

export const QuestionContent = ({
    isStandard,
    type,
    valueSet,
    name,
    identifier,
    displayComponent,
    defaultValue
}: Props) => {
    const [conceptState, setConceptState] = useState<Selectable[]>([]);

    useEffect(() => {
        if (valueSet) {
            ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
                authorization: authorization(),
                codeSetNm: valueSet
            }).then((resp: Array<Concept>) => {
                const temp = resp.map((r) => {
                    return { value: r.codesetName ?? '', name: r.longName ?? '', label: r.conceptCode ?? '' };
                });
                setConceptState(temp);
            });
        }
    }, [valueSet]);

    return (
        <div className={styles.question}>
            <div className={styles.reorderIcon}>
                <NbsIcon name={'drag'} />
            </div>
            {isStandard ? (
                <>
                    <div className={styles.content}>
                        <div className={styles.questionHeader}>
                            <Heading level={2}>{name}</Heading>
                            <body className={styles.identifier}>{`(${identifier})`}</body>
                        </div>
                        <div className={styles.questionContent}>
                            <div className={styles.inputContent}>
                                {(displayComponent === 1007 ||
                                    displayComponent === 1013 ||
                                    displayComponent === 1024 ||
                                    displayComponent === 1025 ||
                                    displayComponent === 1027 ||
                                    displayComponent === 1028 ||
                                    displayComponent === 1031) && (
                                    <SelectInput options={conceptState} data-testid="dropdown-input" />
                                )}
                                {/* create custom checkbox component */}
                                {/* need to create an api that grabs the race since it is in another table, once that is done a custom component can be created */}
                                {displayComponent === 1001 && <SelectInput options={conceptState} />}

                                {displayComponent === 1008 && <Input type="text" className={styles.questionInput} />}

                                {(displayComponent === 1009 || displayComponent === 1019) && (
                                    <Input type="text" className={styles.questionInput} multiline />
                                )}
                            </div>

                            {type === 'DATE' && (
                                <Icon.CalendarToday size={4} className={styles.icon} data-testid="calendar-icon" />
                            )}
                        </div>
                    </div>
                </>
            ) : (
                <>
                    {displayComponent === commentsReadOnlyId && <body>{name}</body>}
                    {displayComponent === hyperlinkId && (
                        <>
                            <a href={defaultValue}>{name}</a>
                        </>
                    )}
                    {displayComponent === lineSeparatorId && (
                        <body className={styles.inputContent}>Line separator will be placed here</body>
                    )}
                    {displayComponent === participantListId && (
                        <body className={styles.inputContent}>Participants list will be placed here</body>
                    )}
                    {displayComponent === originalElecDocId && (
                        <body className={styles.inputContent}>Original document list will be placed here</body>
                    )}
                </>
            )}
        </div>
    );
};
