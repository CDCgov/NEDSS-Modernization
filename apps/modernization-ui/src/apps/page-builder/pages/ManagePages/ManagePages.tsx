import { PageBuilder } from '../PageBuilder/PageBuilder';
import './ManagePages.scss';
import { ManagePagesTable } from './ManagePagesTable';

export const ManagePages = () => {
    return (
        <>
            <PageBuilder page="manage-pages">
                <div className="manage-pages">
                    <div className="manage-pages__container">
                        <div className="manage-pages__table">
                            <ManagePagesTable />
                        </div>
                    </div>
                </div>
            </PageBuilder>
        </>
    );
};
