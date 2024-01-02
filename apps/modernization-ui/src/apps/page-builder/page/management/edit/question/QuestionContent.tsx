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
            {isStandard ? (
                <>
                    <div className={styles.reorderIcon}>
                        <NbsIcon name={'drag'} />
                    </div>
                    <div className={styles.content}>
                        <div className={styles.questionHeader}>
                            <Heading level={2}>{name}</Heading>
                            <p className={styles.identifier}>{`(${identifier})`}</p>
                        </div>
                        <div className={styles.questionContent}>
                            <div className={styles.inputContent}>
                                {displayComponent === 1007 && <SelectInput options={conceptState} />}
                                {/* create custom checkbox component */}
                                {/* need to create an api that grabs the race since it is in another table, once that is done a custom component can be created */}
                                {displayComponent === 1001 && <SelectInput options={conceptState} />}

                                {(displayComponent === 1008 || displayComponent === 1009) && (
                                    <Input type="text" className={styles.questionInput} />
                                )}
                            </div>

                            {type === 'DATE' && <Icon.CalendarToday size={4} className={styles.icon} />}
                        </div>
                    </div>
                </>
            ) : (
                <>
                    {displayComponent === commentsReadOnlyId && <p>{name}</p>}
                    {displayComponent === hyperlinkId && (
                        <>
                            <a href={defaultValue}>{name}</a>
                        </>
                    )}
                </>
            )}
        </div>
    );
};
