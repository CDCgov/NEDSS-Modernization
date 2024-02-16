import { Button } from '@trussworks/react-uswds';
import { Valueset } from 'apps/page-builder/generated';
import { ConceptSort, useFindConcepts } from 'apps/page-builder/hooks/api/useFindConcepts';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { ConceptTable } from './concept/ConceptTable';
import styles from './edit-valueset.module.scss';
import { useEffect, useState } from 'react';
import { PageProvider, Status, usePage } from 'page';
import { CreateConcept } from './concept/CreateConcept';

type Props = {
    valueset: Valueset;
    onClose: () => void;
    onCancel: () => void;
    onAccept: () => void;
};
export const EditValueset = (props: Props) => {
    return (
        <PageProvider>
            <EditValuesetContent {...props}></EditValuesetContent>
        </PageProvider>
    );
};

const EditValuesetContent = ({ valueset, onClose, onAccept, onCancel }: Props) => {
    const [state, setState] = useState<'view' | 'create'>('view');
    const { response, isLoading, search } = useFindConcepts();
    const { page, ready, firstPage, reload } = usePage();

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

    return (
        <div className={styles.editValueset}>
            <CloseableHeader title={<div className={styles.addValuesetHeader}>Add value set</div>} onClose={onClose} />
            <div className={styles.content}>
                <div className={styles.sectionText}>Value set details</div>
                <div className={styles.valuesetInfo}>
                    <div className={styles.data}>
                        <div className={styles.title}>VALUE SET TYPE</div>
                        <div>{valueset.type}</div>
                    </div>
                    <div className={styles.data}>
                        <div className={styles.title}>VALUE SET CODE</div>
                        <div>{valueset.code}</div>
                    </div>
                    <div className={styles.data}>
                        <div className={styles.title}>VALUE SET NAME</div>
                        <div>{valueset.name}</div>
                    </div>
                    <div className={styles.data}>
                        <div className={styles.title}>VALUE SET DESCRIPTION</div>
                        <div>{valueset.description}</div>
                    </div>
                </div>
                {state === 'view' && (
                    <>
                        <div className={styles.sectionText}>Value set concepts</div>
                        <ConceptTable loading={isLoading} concepts={response?.content ?? []} onSort={setSort} />
                        <div className={styles.noConceptsSection}>
                            <div className={styles.noConceptText}>
                                No value set concept is displayed. Please click the button below to add a new value set
                                concept.
                            </div>
                            <div>
                                <Button type="button" outline onClick={() => setState('create')}>
                                    Add new concept
                                </Button>
                            </div>
                        </div>
                    </>
                )}
                {state === 'create' && (
                    <CreateConcept
                        onCancel={() => setState('view')}
                        onCreated={() => {
                            firstPage();
                            setState('view');
                        }}
                    />
                )}
            </div>
            <ButtonBar>
                <Button disabled={state === 'create'} onClick={onCancel} type="button" outline>
                    Cancel
                </Button>
                <Button
                    disabled={state === 'create' || response?.content?.length == 0}
                    onClick={onAccept}
                    type="button">
                    Continue
                </Button>
            </ButtonBar>
        </div>
    );
};
