import { useEffect, useState } from 'react';
import { Heading } from 'components/heading';
import { SearchBar } from 'design-system/search/SearchBar';
import { AlertMessage } from 'design-system/alert/AlertMessage';
import { CaseReportLaboratorySection } from '../components/case-report-and-lab/CaseReportLaboratorySection';
import { DecisionSupportSection } from '../components/decision-support/DecisionSupportSection';
import { EpiLinkSection } from '../components/epi-link-lot-number/EpiLinkSection';
import { MessagingSection } from '../components/messaging/MessagingSection';
import { PageSection } from '../components/page/PageSection';
import { PersonMatchSection } from '../components/person-match/PersonMatchSection';
import { ReportSection } from '../components/report/ReportSection';
import { SecuritySection } from '../components/security/SecuritySection';
import VisibleWrapper from './VisibleWrapper';
import styles from './SystemManagementPage.module.scss';

const SystemManagementPage = () => {
    const [filter, setFilter] = useState('');
    const [alert, setAlert] = useState<null | { type: 'success' | 'error'; message: string }>(null);

    const [visibilityMap, setVisibilityMap] = useState<Record<string, boolean>>({});

    const updateVisibility = (key: string, visible: boolean) => {
        setVisibilityMap((prev) => {
            if (prev[key] === visible) return prev;
            return { ...prev, [key]: visible };
        });
    };

    const cardGroups = [
        [
            { key: 'lab', component: <CaseReportLaboratorySection filter={filter} setAlert={setAlert} /> },
            { key: 'decision', component: <DecisionSupportSection filter={filter} /> }
        ],
        [
            { key: 'epi', component: <EpiLinkSection filter={filter} /> },
            { key: 'messaging', component: <MessagingSection filter={filter} /> },
            { key: 'page', component: <PageSection filter={filter} /> }
        ],
        [
            { key: 'person', component: <PersonMatchSection filter={filter} /> },
            { key: 'report', component: <ReportSection filter={filter} /> },
            { key: 'security', component: <SecuritySection filter={filter} /> }
        ]
    ];

    useEffect(() => {
        // Initialize all visibility to true (assume all cards will be visible)
        const allKeys = cardGroups.flat().map(({ key }) => key);
        const initialMap: Record<string, boolean> = {};
        allKeys.forEach((key) => {
            initialMap[key] = true;
        });
        setVisibilityMap(initialMap);
    }, [filter]); // Reset visibility on filter change

    // push empty columns to the right
    const reorderedGroups = [
        ...cardGroups.filter((group) => group.some(({ key }) => visibilityMap[key])), // visible columns
        ...cardGroups.filter((group) => group.every(({ key }) => !visibilityMap[key])) // empty columns
    ];
    return (
        <div className={styles.systemManagement}>
            <header>
                <div className={styles.titleBar}>
                    <Heading level={1}>System Management</Heading>
                    <SearchBar
                        placeholder={'Filter by keyword'}
                        aria-label="Search"
                        value={filter}
                        onChange={setFilter}
                    />
                </div>
            </header>

            {alert && (
                <div className={styles.alertWrapper}>
                    <AlertMessage type={alert.type} slim={true} onClose={() => setAlert(null)}>
                        {alert.message}
                    </AlertMessage>
                </div>
            )}

            <div className={styles.cardGroup}>
                {reorderedGroups.map((group) => {
                    const visibleCards = group.filter(({ key }) => visibilityMap[key]);

                    return (
                        <div key={group.map(({ key }) => key).join('-')} className={styles.column}>
                            {visibleCards.length > 0 ? (
                                visibleCards.map(({ key, component }) => (
                                    <VisibleWrapper
                                        key={key}
                                        onVisibilityChange={(visible) => updateVisibility(key, visible)}>
                                        {component}
                                    </VisibleWrapper>
                                ))
                            ) : (
                                // Render an empty div so column keeps its place, but doesn't take visual space
                                <div className={styles.emptyColumnPlaceholder} />
                            )}
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default SystemManagementPage;
