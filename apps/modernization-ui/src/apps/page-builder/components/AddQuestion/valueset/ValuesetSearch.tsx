import { Button, Icon } from '@trussworks/react-uswds';
import { useEffect, useState } from 'react';
import { ButtonBar } from '../../ButtonBar/ButtonBar';
import { CloseableHeader } from '../../CloseableHeader/CloseableHeader';
import { ValuesetSearchTable } from '../../ValuesetSearchTable/ValuesetSearchTable';
import styles from './valueset-search.module.scss';
import { Status, usePage } from 'page';
import { ValuesetSort, useFindValuesets } from 'apps/page-builder/hooks/api/useFindValueset';

type Props = {
    onCancel: () => void;
    onClose: () => void;
    onAccept: (selected: number) => void;
    onCreateNew?: () => void;
};
export const ValuesetSearch = ({ onCancel, onClose, onAccept, onCreateNew }: Props) => {
    const [selectedValueset, setSelectedValueset] = useState<number | undefined>(undefined);
    const { page, ready, firstPage, reload } = usePage();
    const [query, setQuery] = useState<string>('');
    const [sort, setSort] = useState<ValuesetSort | undefined>(undefined);
    const { isLoading, search, response } = useFindValuesets();

    useEffect(() => {
        search({ query: undefined, sort: undefined, page: 0, pageSize: 10 });
    }, []);

    useEffect(() => {
        if (page.status === Status.Requested) {
            setSelectedValueset(undefined);
            search({
                query,
                page: page.current - 1,
                pageSize: page.pageSize,
                sort: sort
            });
        }
    }, [page.status]);

    useEffect(() => {
        if (page.current === 1) {
            reload();
        } else {
            firstPage();
        }
    }, [query, sort]);

    useEffect(() => {
        const currentPage = response?.number ? response?.number + 1 : 1;
        ready(response?.totalElements ?? 0, currentPage);
    }, [response]);

    return (
        <div className={styles.searchValueset}>
            <CloseableHeader
                title={
                    <div className={styles.addValuesetHeader}>
                        <Icon.ArrowBack onClick={onCancel} /> Add value set
                    </div>
                }
                onClose={onClose}
            />
            <div className={styles.scrollableContent}>
                <div className={styles.heading}>
                    <h3>Let's find the right value set for your single choice question</h3>
                </div>
                <div className={styles.tableContainer}>
                    <ValuesetSearchTable
                        onSelectionChange={setSelectedValueset}
                        onQuerySubmit={setQuery}
                        onSortChange={setSort}
                        query={query}
                        valuesets={response?.content ?? []}
                        onCreateNew={onCreateNew}
                        isLoading={isLoading}
                    />
                </div>
            </div>
            <ButtonBar>
                <Button onClick={onCancel} type="button" outline>
                    Cancel
                </Button>
                <Button
                    disabled={selectedValueset === undefined}
                    type="button"
                    onClick={() => selectedValueset && onAccept(selectedValueset)}>
                    Apply value set to question
                </Button>
            </ButtonBar>
        </div>
    );
};
