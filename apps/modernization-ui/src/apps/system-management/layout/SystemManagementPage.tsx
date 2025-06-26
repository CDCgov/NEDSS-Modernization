import { Heading } from 'components/heading';
import { SearchBar } from 'design-system/search/SearchBar';
import styles from './SystemManagementPage.module.scss';
import { CaseReportLaboratorySection } from '../components/case-report-and-lab/CaseReportLaboratorySection';
import { useState } from 'react';
import { AlertMessage } from '../../../design-system/alert/AlertMessage';
import { DecisionSupportSection } from '../components/decision-support/DecisionSupportSection';
import { EpiLinkSection } from '../components/epi-link-lot-number/EpiLinkSection';
import { MessagingSection } from '../components/messaging/MessagingSection';
import { PageSection } from '../components/page/PageSection';
import { PersonMatchSection } from '../components/person-match/PersonMatchSection';
import { ReportSection } from '../components/report/ReportSection';
import { SecuritySection } from '../components/security/SecuritySection';
import { caseReportLinks } from '../shared/caseLinks';
import { decisionSupportLinks } from '../shared/decisionSupportLinks';
import { epiLinkLinks } from '../shared/epiLinkLinks';
import { messgaingLinks } from '../shared/messgaingLinks';
import { pageLinks } from '../shared/pageLinks';
import { personMatchLinks } from '../shared/personMatchLinks';
import { reportLinks } from '../shared/reportLinks';
import { securityLinks } from '../shared/securityLinks';

const SystemManagementPage = () => {
    const [filter, setFilter] = useState('');
    const [alert, setAlert] = useState<null | { type: 'success' | 'error'; message: string }>(null);
    return (
        <div className={styles.systemManagement}>
            <header>
                <div className={styles.titleBar}>
                    <Heading level={1}>System Management </Heading>
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
                <div className={styles.column}>
                    <CaseReportLaboratorySection filter={filter} setAlert={setAlert} links={caseReportLinks} />
                    <DecisionSupportSection filter={filter} links={decisionSupportLinks} />
                </div>
                <div className={styles.column}>
                    <EpiLinkSection filter={filter} links={epiLinkLinks} />
                    <MessagingSection filter={filter} links={messgaingLinks} />
                    <PageSection filter={filter} links={pageLinks} />
                </div>
                <div className={styles.column}>
                    <PersonMatchSection filter={filter} links={personMatchLinks} />
                    <ReportSection filter={filter} links={reportLinks} />
                    <SecuritySection filter={filter} links={securityLinks} />
                </div>
            </div>
        </div>
    );
};

export default SystemManagementPage;
