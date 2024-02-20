import { Button, Icon } from '@trussworks/react-uswds';
import { Concept, Valueset } from 'apps/page-builder/generated';
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
    const [state, setState] = useState<'view' | 'create' | 'edit'>('view');
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
            <CloseableHeader
                title={<div className={styles.addValuesetHeader}>Edit value set</div>}
                onClose={handleClose}
            />
            <div className={styles.content}>
                {state === 'view' && (
                    <>
                        <ValuesetDetails valueset={valueset} onEdit={() => setState('edit')} />
                        <ValuesetContent
                            isLoading={isLoading}
                            concepts={response?.content ?? []}
                            onSort={setSort}
                            onAddNew={() => setState('create')}
                        />
                    </>
                )}
                {state === 'create' && (
                    <CreateConceptContent
                        valuesetName={valueset.code}
                        onCancel={() => setState('view')}
                        onCreated={() => {
                            firstPage();
                            setState('view');
                        }}
                    />
                )}
            </div>
            <ButtonBar>
                <Button disabled={state === 'create'} onClick={handleCancel} type="button" outline>
                    Cancel
                </Button>
                <Button
                    disabled={state !== 'view' || response?.content?.length == 0}
                    onClick={handleAccept}
                    type="button">
                    Continue
                </Button>
            </ButtonBar>
        </div>
    );
};

type ValuesetDetailsProps = {
    onEdit: () => void;
    valueset: Valueset;
};
const ValuesetDetails = ({ valueset, onEdit }: ValuesetDetailsProps) => {
    return (
        <>
            <div className={styles.detailsHeader}>
                <div className={styles.sectionText}>Value set details</div>
                <Icon.Edit size={3} onClick={onEdit} />
            </div>
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
        </>
    );
};

type ValuesetContentProps = {
    isLoading: boolean;
    concepts: Concept[];
    onSort: (sort: ConceptSort | undefined) => void;
    onAddNew: () => void;
};
const ValuesetContent = ({ isLoading, concepts, onSort, onAddNew }: ValuesetContentProps) => {
    return (
        <>
            <div className={styles.sectionText}>Value set concepts</div>
            <ConceptTable loading={isLoading} concepts={concepts} onSort={onSort} />
            {concepts.length === 0 ? (
                <div className={styles.noConceptsSection}>
                    <div className={styles.noConceptText}>
                        No value set concept is displayed. Please click the button below to add a new value set concept.
                    </div>
                    <div>
                        <Button type="button" outline onClick={onAddNew}>
                            Add new concept
                        </Button>
                    </div>
                </div>
            ) : (
                <div className={styles.addConceptLinkSection}>
                    <button className={styles.addConceptButton} onClick={onAddNew}>
                        <Icon.Add size={3} /> Add new concept
                    </button>
                </div>
            )}
        </>
    );
};

type CreateConceptProps = {
    valuesetName: string;
    onCreated: () => void;
    onCancel: () => void;
};
const CreateConceptContent = ({ valuesetName, onCreated, onCancel }: CreateConceptProps) => {
    return <CreateConcept valuesetName={valuesetName} onCancel={onCancel} onCreated={onCreated} />;
};
