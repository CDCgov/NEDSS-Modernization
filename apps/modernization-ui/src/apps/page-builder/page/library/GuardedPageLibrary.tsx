import { FeatureGuard } from 'feature';
import { PageLibrary } from './PageLibrary';

const GuardedPageLibrary = () => (
    <FeatureGuard guard={(features) => features?.pageBuilder?.page?.library?.enabled}>
        <PageLibrary />
    </FeatureGuard>
);

export { GuardedPageLibrary };
