import { Button } from '@trussworks/react-uswds';
import { AddableQuestionSort, useFindAddableQuestions } from 'apps/page-builder/hooks/api/useFindAvailableQuestions';
import { SelectionMode } from 'components/Table';
import { Status, usePagination } from 'pagination';
import { useEffect, useState } from 'react';

import { QuestionSearchTable } from './table/QuestionSearchTable';
import { CloseableHeader } from 'apps/page-builder/components/CloseableHeader/CloseableHeader';
import { ButtonBar } from 'apps/page-builder/components/ButtonBar/ButtonBar';
import styles from './question-search.module.scss';
import { useAlert } from 'libs/alert';

type Props = {
    pageId: number;
    onCreateNew: () => void;
    onCancel: () => void;
    onAccept: (questions: number[]) => void;
};
export const QuestionSearch = ({ pageId, onCreateNew, onCancel, onAccept }: Props) => {
    const { page, ready, firstPage, reload } = usePagination();
    const [query, setQuery] = useState<string>('');
    const [sort, setSort] = useState<AddableQuestionSort | undefined>(undefined);
    const { isLoading, search, response, error } = useFindAddableQuestions();
    const [selectedQuestions, setSelectedQuestions] = useState<number[]>([]);
    const { showError } = useAlert();

    useEffect(() => {
        if (page.status === Status.Requested && !isLoading) {
            setSelectedQuestions([]);
            search({
                searchText: query,
                pageId: pageId,
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
    }, [query, sort]);

    useEffect(() => {
        if (response) {
            const currentPage = response.number ? response.number + 1 : 1;
            ready(response.totalElements ?? 0, currentPage);
        } else if (error) {
            showError('Failed to retrieve questions');
        }
    }, [response, error]);

    const handleSelectedQuestionChange = (mode: SelectionMode, id: number) => {
        if (mode === 'deselect') {
            setSelectedQuestions((previous) => previous.filter((e) => e !== id));
        } else {
            setSelectedQuestions((previous) => [...previous, id]);
        }
    };

    const handleAccept = () => {
        setSelectedQuestions([]);
        setQuery('');
        onAccept(selectedQuestions);
    };

    const handleClose = () => {
        setQuery('');
        setSelectedQuestions([]);
        onCancel();
    };

    return (
        <>
            <CloseableHeader onClose={handleClose} title="Add question" />
            <div className={styles.content}>
                <div className={styles.subHeading}>
                    <div className={styles.helpText}>You can search for an existing question or create a new one</div>
                </div>
                <QuestionSearchTable
                    questions={response?.content ?? []}
                    isLoading={isLoading}
                    query={query}
                    onQuerySubmit={setQuery}
                    onSortChange={setSort}
                    onSelectionChange={handleSelectedQuestionChange}
                    onCreateNew={onCreateNew}
                />
                {response?.content?.length === 0 && (
                    <div className={styles.createNewNotification}>
                        <div className={styles.message}>Can't find what you're looking for?</div>
                        <Button type="button" outline onClick={onCreateNew} className="addQuestionCreateNewBtn">
                            Create new
                        </Button>
                    </div>
                )}
            </div>
            <ButtonBar>
                <Button onClick={handleClose} type="button" outline>
                    Cancel
                </Button>
                <Button
                    onClick={handleAccept}
                    type="button"
                    disabled={selectedQuestions == undefined || selectedQuestions.length === 0}>
                    Apply to page
                </Button>
            </ButtonBar>
        </>
    );
};
