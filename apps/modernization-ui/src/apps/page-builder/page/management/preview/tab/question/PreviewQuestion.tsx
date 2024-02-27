import { PagesQuestion } from 'apps/page-builder/generated';
import styles from './preview-question.module.scss';
import { Input } from 'components/FormInputs/Input';
import { Icon } from '@trussworks/react-uswds';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { RadioButtons } from 'apps/page-builder/components/RadioButton/RadioButton';
import { useEffect, useState } from 'react';
import { Selectable } from 'options';
import { ConceptOptionsResponse, ConceptOptionsService } from 'generated';
import { authorization } from 'authorization';

type Props = {
    question: PagesQuestion;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const participantListId = 1030;
const originalElecDocId = 1036;

const staticTypes = [hyperlinkId, commentsReadOnlyId, lineSeparatorId, participantListId, originalElecDocId];

export const PreviewQuestion = ({ question }: Props) => {
    const { name, displayComponent, defaultValue, valueSet } = question;
    const [conceptState, setConceptState] = useState<Selectable[]>([]);

    useEffect(() => {
        if (valueSet) {
            ConceptOptionsService.allUsingGet({
                authorization: authorization(),
                name: valueSet
            }).then((resp: ConceptOptionsResponse) => {
                setConceptState(resp.options);
            });
        }
    }, [valueSet]);

    const componentWithoutStaticType = (
        <>
            <div className={styles.questionHeader}>
                <div className={styles.name}>{question.name}</div>
                <div className={styles.identifier}>{`(${question.question ?? ''})`}</div>
            </div>
            <div className={question.dataType === 'DATE' ? styles.inputContent : styles.inputContentCovered}>
                {(displayComponent === 1007 ||
                    displayComponent === 1013 ||
                    displayComponent === 1024 ||
                    displayComponent === 1025 ||
                    displayComponent === 1027 ||
                    displayComponent === 1028 ||
                    displayComponent === 1031) && (
                    <SelectInput
                        onChange={() => {}}
                        defaultValue={''}
                        options={conceptState}
                        data-testid="dropdown-input"
                    />
                )}
                {displayComponent === 1001 && <RadioButtons options={conceptState} />}
                {(displayComponent === 1008 || displayComponent === 1009 || displayComponent === 1019) && (
                    <Input onChange={() => {}} defaultValue="" type="text" className={styles.questionInput} />
                )}
            </div>
            {question.dataType === 'DATE' && (
                <Icon.CalendarToday size={4} className={styles.icon} data-testid="calendar-icon" />
            )}
        </>
    );

    const componentWithStaticType = (
        <>
            {displayComponent === commentsReadOnlyId && <div className={styles.staticComment}>{name}</div>}
            {displayComponent === hyperlinkId && (
                <div className={styles.staticHyperlink}>
                    <a href={defaultValue}>{name}</a>
                </div>
            )}
            {displayComponent === lineSeparatorId && (
                <div className={styles.staticLineSeparator}>
                    <div></div>
                </div>
            )}
            {displayComponent === participantListId && (
                <div className={styles.staticParticipantsList}>Participants list will be placed here</div>
            )}
            {displayComponent === originalElecDocId && (
                <div className={styles.staticOriginalDocList}>Original document list will be placed here</div>
            )}
        </>
    );

    return (
        <div className={styles.question}>
            {!staticTypes.includes(displayComponent ?? 0) ? (
                <div className={styles.borderedContainer}>
                    <div className={styles.content}>
                        <div className={styles.questionContent}>{componentWithoutStaticType}</div>
                    </div>
                </div>
            ) : (
                componentWithStaticType
            )}
        </div>
    );
};
