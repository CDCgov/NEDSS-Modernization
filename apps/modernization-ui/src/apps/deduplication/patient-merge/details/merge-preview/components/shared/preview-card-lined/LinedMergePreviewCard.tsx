import React from 'react';
import { Card } from 'design-system/card/Card';
import styles from './LinedMergePreviewCard.module.scss';

type LabelTextPair = {
    label: string;
    text: string;
    lined?: boolean;
};

type LinedMergePreviewCardProps = {
    id: string;
    title: string;
    items: LabelTextPair[];
};

export const LinedMergePreviewCard = ({ id, title, items }: LinedMergePreviewCardProps) => {
    return (
        <div className={styles.card}>
            <Card id={id} title={title} collapsible={false}>
                <div className={styles.content}>
                    {items.map((item, index) => (
                        <div key={index} className={`${styles.item} ${item.lined ? styles.underlined : ''}`}>
                            <div className={styles.labelText}>
                                <span className={styles.label}>{item.label}</span>
                                <span className={styles.text}>{item.text}</span>
                            </div>
                        </div>
                    ))}
                </div>
            </Card>
        </div>
    );
};
