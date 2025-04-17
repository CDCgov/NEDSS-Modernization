import { Concept, Valueset } from 'apps/page-builder/generated';
import { ConceptSort, useFindConcepts } from 'apps/page-builder/hooks/api/useFindConcepts';
import { PaginationProvider, Status, usePagination } from 'pagination';
import { useEffect, useState } from 'react';
import { EditValuesetDetails } from './EditValuesetDetails';
import { ViewValueset } from './ViewValueset';
import { CreateConcept } from './CreateConcept';
import styles from './edit-valueset.module.scss';
import { EditConcept } from './EditConcept';

type Props = {
    valueset: Valueset;
    onClose: () => void;
    onCancel: () => void;
    onAccept: () => void;
    onValuesetUpdated: () => void;
};
export const EditValueset = (props: Props) => {
    return (
        <PaginationProvider>
            <EditValuesetContent {...props}></EditValuesetContent>
        </PaginationProvider>
    );
};

const EditValuesetContent = ({ valueset, onClose, onAccept, onCancel, onValuesetUpdated }: Props) => {
    const [state, setState] = useState<'view' | 'create-concept' | 'edit-concept' | 'edit-valueset'>('view');
    const [editedConcept, setEditedConcept] = useState<Concept | undefined>(undefined);
    const { response, isLoading, search } = useFindConcepts();
    const { page, ready, firstPage, reload } = usePagination();

    const [sort, setSort] = useState<ConceptSort | undefined>(undefined);

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

    const handleConceptCreated = () => {
        firstPage();
        setState('view');
    };

    const handleEditConcept = (concept: Concept) => {
        setEditedConcept(concept);
        setState('edit-concept');
    };

    const handleConceptUpdated = () => {
        firstPage();
        setState('view');
    };

    return (
        <div className={styles.editValueset}>
            {state === 'view' && (
                <ViewValueset
                    valueset={valueset}
                    concepts={response?.content ?? []}
                    isLoading={isLoading}
                    onClose={handleClose}
                    onEditDetails={() => setState('edit-valueset')}
                    onAddConcept={() => setState('create-concept')}
                    onEditConcept={handleEditConcept}
                    onSort={setSort}
                    onCancel={handleCancel}
                    onAccept={handleAccept}
                />
            )}
            {state === 'edit-valueset' && (
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
                    onCreated={handleConceptCreated}
                />
            )}
            {state === 'edit-concept' && editedConcept && (
                <EditConcept
                    valueset={valueset.code}
                    concept={editedConcept}
                    onClose={handleClose}
                    onCancel={() => setState('view')}
                    onUpdated={handleConceptUpdated}
                />
            )}
        </div>
    );
};
