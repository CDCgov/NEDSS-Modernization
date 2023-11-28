import { Button } from '@trussworks/react-uswds';
import { AddableQuestionTable } from 'apps/page-builder/components/AddableQuestionTable/AddableQuestionTable';
import { AddableQuestionSort, useFindAddableQuestions } from 'apps/page-builder/hooks/api/useFindAddableQuestions';
import { SelectionMode } from 'components/Table';
import { PageProvider, Status, usePage } from 'page';
import { useEffect, useState } from 'react';

import { ButtonBar } from '../../../components/ButtonBar/ButtonBar';
import { CloseableHeader } from '../../../components/CloseableHeader/CloseableHeader';
import styles from './question-lookup.module.scss';

export const QuestionLookup = (props: Props) => (
    <PageProvider>
        <QuestionLookupContent {...props} />
    </PageProvider>
);

type Props = {
    pageId: number;
    onCreateNew: () => void;
    onCancel: () => void;
    onAccept: (questions: number[]) => void;
};
const QuestionLookupContent = ({ pageId, onCreateNew, onCancel, onAccept }: Props) => {
    const { page, ready, firstPage, reload } = usePage();
    const [query, setQuery] = useState<string>('');
    const [sort, setSort] = useState<AddableQuestionSort | undefined>(undefined);
    const { isLoading, search, response } = useFindAddableQuestions();
    const [selectedQuestions, setSelectedQuestions] = useState<number[]>([]);

    useEffect(() => {
        if (pageId) {
            search({ pageId });
        }
    }, [pageId]);

    useEffect(() => {
        if (page.status === Status.Requested) {
            setSelectedQuestions([]);
            search({
                searchText: query,
                pageId: pageId,
                page: page.current - 1,
                pageSize: page.pageSize,
                sort
            });
        }
    }, [page]);

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

    const handleSelectedQuestionChange = (mode: SelectionMode, id: number) => {
        if (mode === 'deselect') {
            setSelectedQuestions((previous) => previous.filter((e) => e !== id));
        } else {
            setSelectedQuestions((previous) => [...previous, id]);
        }
    };

    return (
        <>
            <CloseableHeader onClose={onCancel} title="Add question" />
            <div className={styles.content}>
                <div className={styles.subHeading}>
                    <div className={styles.helpText}>You can search for an existing question or create a new one</div>
                </div>
                <AddableQuestionTable
                    questions={response?.content ?? []}
                    isLoading={isLoading}
                    onQuerySubmit={(query) => setQuery(query)}
                    onSortChange={setSort}
                    onSelectionChange={handleSelectedQuestionChange}
                />
                {response?.content?.length === 0 && (
                    <div className={styles.createNewNotification}>
                        <div className={styles.message}>Can't find what you're looking for?</div>
                        <Button type="button" outline onClick={onCreateNew}>
                            Create new
                        </Button>
                    </div>
                )}
            </div>
            <ButtonBar>
                <Button onClick={onCancel} type="button" outline>
                    Cancel
                </Button>
                <Button
                    onClick={() => onAccept(selectedQuestions)}
                    type="button"
                    disabled={selectedQuestions == undefined || selectedQuestions.length === 0}>
                    Add to page
                </Button>
            </ButtonBar>
        </>
    );
};
