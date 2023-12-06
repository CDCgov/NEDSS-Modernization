import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import './CustomFieldAdminBanner.scss';
export const CustomFieldAdminBanner = () => {
    return (
        <div className="custom-field-admin">
            <AlertBanner type="info">
                <span style={{ marginRight: '4px' }}>To access the legacy Custom Fields Admin menu,</span>{' '}
                <a href="/nbs/LDFAdminLoad.do">click here</a>.
            </AlertBanner>
        </div>
    );
};
