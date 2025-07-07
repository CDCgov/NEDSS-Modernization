import { useState } from 'react';
import { Card } from 'design-system/card/Card';
import { Button } from 'design-system/button';
import { Confirmation } from 'design-system/modal/Confirmation';
import styles from './CaseReportLaboratorySection.module.scss';
import { caseReportLinks } from './caseLinks';
import { Permitted } from '../../../../libs/permission';

type Props = {
    filter: string;
    setAlert: (alert: { type: 'success' | 'error'; message: string } | null) => void;
};

export const CaseReportLaboratorySection = ({ filter, setAlert }: Props) => {
    const [showConfirm, setShowConfirm] = useState(false);

    const handleResetClick = () => {
        setShowConfirm(true);
    };

    const handleConfirm = () => {
        setShowConfirm(false);
        fetch('/nbs/ResetCache.do?method=resetLabMappingCache', {
            method: 'GET',
            credentials: 'include'
        })
            .then((res) => {
                if (!res.ok) throw new Error('Non-200 response');
                setAlert({
                    type: 'success',
                    message:
                        'Labtest program area mapping cache has been successfully reset. Please restart Wildfly to reflect the changes.'
                });
            })
            .catch(() => {
                setAlert({ type: 'error', message: 'Failed to reset Lab Mapping Cache. Please try again later.' });
            });
    };

    const handleCancel = () => {
        setShowConfirm(false);
    };

    const normalizedFilter = filter.trim().toLowerCase();
    const showResetButton = 'reset lab mapping cache'.includes(normalizedFilter);
    const filteredLinks = caseReportLinks.filter((item) => {
        if ('text' in item && item.text) {
            return item.text.toLowerCase().includes(normalizedFilter);
        }
        return true;
    });

    const hasVisibleLinks = filteredLinks.some((item) => 'text' in item && item.text);
    if (!hasVisibleLinks && !showResetButton) return null;

    // Group links dynamically based on last seen group title
    const grouped = [];
    let currentGroup: { title?: string; links: { text: string; href: string }[] } = { links: [] };

    for (const item of filteredLinks) {
        if ('group' in item) {
            if (currentGroup.links.length) grouped.push(currentGroup);
            currentGroup = { title: item.group, links: [] };
        } else {
            currentGroup.links.push(item);
        }
    }
    if (currentGroup.links.length) grouped.push(currentGroup);

    return (
        <Permitted permission={'SRTADMIN-SYSTEM'}>
            <Card
                id="case-report-lab"
                title="Case report & laboratory"
                level={2}
                collapsible={true}
                className={styles.card}>
                <div className={styles.sectionContent}>
                    {/* Render ungrouped links first */}
                    {grouped.length > 0 && grouped[0].title === undefined && (
                        <div className={styles.subGroup}>
                            {grouped[0].links.map((link) => (
                                <a key={link.href} href={link.href}>
                                    {link.text}
                                </a>
                            ))}
                        </div>
                    )}

                    {/* Render all groups */}
                    {grouped.slice(grouped[0]?.title === undefined ? 1 : 0).map((group) => {
                        const groupKey = group.title ?? group.links.map((l) => l.href).join('-');
                        return (
                            <div key={groupKey}>
                                {group.title && <div className={styles.groupTitle}>{group.title}</div>}
                                <div className={styles.subGroup}>
                                    {group.links.map((link) => (
                                        <a key={link.href} href={link.href}>
                                            {link.text}
                                        </a>
                                    ))}
                                </div>
                            </div>
                        );
                    })}

                    {showResetButton && (
                        <Button secondary sizing="small" onClick={handleResetClick}>
                            Reset lab mapping cache
                        </Button>
                    )}

                    {showConfirm && (
                        <Confirmation
                            title="Reset Lab Mapping Cache"
                            confirmText="Yes, reset"
                            onConfirm={handleConfirm}
                            onCancel={handleCancel}>
                            Are you sure you want to reset lab mapping cache?
                        </Confirmation>
                    )}
                </div>
            </Card>
        </Permitted>
    );
};
