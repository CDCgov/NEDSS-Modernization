import React from 'react';
import styles from './subsection.module.scss';
import { Button, Icon } from '@trussworks/react-uswds';

type Props = {
    name: string;
    questionCount: number;
    onAddQuestion: () => void;
    isExpanded: boolean;
    onExpandedChange: (isExpanded: boolean) => void;
};
export const SubsectionHeader = ({ name, questionCount, isExpanded, onExpandedChange, onAddQuestion }: Props) => {
    return (
        <div className={styles.header}>
            <div className={styles.info}>
                <div className={styles.name}>{name}</div>
                <div className={styles.count}>{`${questionCount} question${questionCount > 1 ? 's' : ''}`}</div>
            </div>
            <div className={styles.buttons}>
                <Button type="button" onClick={onAddQuestion} outline>
                    Add question
                </Button>
                <Icon.MoreVert size={4} />
                {isExpanded ? (
                    <Icon.ExpandLess size={4} onClick={() => onExpandedChange(false)} />
                ) : (
                    <Icon.ExpandMore size={4} onClick={() => onExpandedChange(true)} />
                )}
            </div>
        </div>
    );
};
