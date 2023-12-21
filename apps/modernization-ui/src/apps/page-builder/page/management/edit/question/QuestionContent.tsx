import React from 'react';

import styles from './question-content.module.scss';

type Props = {
    name: string;
    type: string;
};
export const QuestionContent = ({ name, type }: Props) => {
    return (
        <div className={styles.question}>
            <div className={styles.name}>{name}</div>
            <div>Type: {type}</div>
        </div>
    );
};
