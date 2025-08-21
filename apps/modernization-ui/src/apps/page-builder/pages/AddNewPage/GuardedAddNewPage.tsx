import { FeatureGuard } from 'feature';
import { AddNewPage } from './AddNewPage';

const GuardedAddNewPage = () => (
    <FeatureGuard guard={(features) => features?.pageBuilder?.page?.management?.create?.enabled}>
        <AddNewPage />
    </FeatureGuard>
);

export { GuardedAddNewPage };
