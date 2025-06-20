import { Heading } from 'components/heading';
import { SearchBar } from 'design-system/search/SearchBar';
import styles from './SystemManagementPage.module.scss';
import { CaseReportLaboratorySection } from '../components/case-report-and-lab/CaseReportLaboratorySection';
import { useState } from 'react';
import { AlertMessage } from '../../../design-system/alert/AlertMessage';
import { links as caseReportLinks } from '../shared/caseLinks';

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
                    <AlertMessage type={alert.type} slim={true}>
                        {alert.message}
                    </AlertMessage>
                </div>
            )}
            <CaseReportLaboratorySection filter={filter} setAlert={setAlert} links={caseReportLinks} />
        </div>
    );
};

export default SystemManagementPage;
