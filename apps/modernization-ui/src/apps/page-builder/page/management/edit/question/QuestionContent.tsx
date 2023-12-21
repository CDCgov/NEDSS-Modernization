import React from 'react';

import styles from './question-content.module.scss';

type Props = {
    name: string;
    type: string;
    displayComponent?: number;
};
export const QuestionContent = ({ name, type, displayComponent }: Props) => {
    return (
        <div className={styles.question}>
            <div className={styles.name}>{name}</div>
            <div>Type: {type}</div>
            <div>Display component: {displayComponent}</div>
        </div>
    );
};
