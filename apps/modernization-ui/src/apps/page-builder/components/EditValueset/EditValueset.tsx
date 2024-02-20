import { Valueset } from 'apps/page-builder/generated';
import { ConceptSort, useFindConcepts } from 'apps/page-builder/hooks/api/useFindConcepts';
import { PageProvider, Status, usePage } from 'page';
import { useEffect, useState } from 'react';
import { EditValuesetDetails } from './EditValuesetDetails';
import { ViewValueset } from './ViewValueset';
import { CreateConcept } from './CreateConcept';
import styles from './edit-valueset.module.scss';

type Props = {
    valueset: Valueset;
    onClose: () => void;
    onCancel: () => void;
    onAccept: () => void;
    onValuesetUpdated: () => void;
};
export const EditValueset = (props: Props) => {
    return (
        <PageProvider>
            <EditValuesetContent {...props}></EditValuesetContent>
        </PageProvider>
    );
};

const EditValuesetContent = ({ valueset, onClose, onAccept, onCancel, onValuesetUpdated }: Props) => {
    const [state, setState] = useState<'view' | 'create-concept' | 'edit'>('view');
    const { response, isLoading, search } = useFindConcepts();
    const { page, ready, firstPage, reload } = usePage();

    const [sort, setSort] = useState<ConceptSort | undefined>(undefined);

    const handleClose = () => {
        setState('view');
        onClose();
    };

    const handleCancel = () => {
        setState('view');
        onCancel();
    };

    const handleAccept = () => {
        setState('view');
        onAccept();
    };

    useEffect(() => {
        search({ codeSetNm: valueset.code });
    }, []);

    useEffect(() => {
        if (page.status === Status.Requested) {
            search({
                codeSetNm: valueset.code,
                page: page.current - 1,
                pageSize: page.pageSize,
                sort
            });
        }
    }, [page.status]);

    useEffect(() => {
        if (page.current === 1) {
            reload();
        } else {
            firstPage();
        }
    }, [sort, valueset]);

    useEffect(() => {
        const currentPage = response?.number ? response?.number + 1 : 1;
        ready(response?.totalElements ?? 0, currentPage);
    }, [response]);

    return (
        <div className={styles.editValueset}>
            {state === 'view' && (
                <ViewValueset
                    valueset={valueset}
                    concepts={response?.content ?? []}
                    isLoading={isLoading}
                    onClose={handleClose}
                    onEditDetails={() => setState('edit')}
                    onAddConcept={() => setState('create-concept')}
                    onSort={setSort}
                    onCancel={handleCancel}
                    onAccept={handleAccept}
                />
            )}
            {state === 'edit' && (
                <EditValuesetDetails
                    valueset={valueset}
                    onValuesetUpdated={onValuesetUpdated}
                    onClose={handleClose}
                    onCancel={() => setState('view')}
                />
            )}
            {state === 'create-concept' && (
                <CreateConcept
                    valuesetName={valueset.code}
                    onCancel={() => setState('view')}
                    onClose={handleClose}
                    onCreated={() => {
                        firstPage();
                        setState('view');
                    }}
                />
            )}
        </div>
    );
};
