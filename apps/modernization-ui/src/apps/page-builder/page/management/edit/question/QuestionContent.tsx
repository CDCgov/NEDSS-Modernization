import React from 'react';

import styles from './question-content.module.scss';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';

type Props = {
    name: string;
    type: string;
    displayComponent?: number;
};
export const QuestionContent = ({ name, type, displayComponent }: Props) => {
    return (
        <div className={styles.question}>
            <AlertBanner type="warning">Work in progress</AlertBanner>
            <div className={styles.name}>{name}</div>
            <div>Type: {type}</div>
            <div>Display component: {displayComponent}</div>
        </div>
    );
};
