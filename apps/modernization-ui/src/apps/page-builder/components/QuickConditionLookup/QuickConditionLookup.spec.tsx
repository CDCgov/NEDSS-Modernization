import { render, screen, act, fireEvent, waitFor } from '@testing-library/react';
import { QuickConditionLookup } from './QuickConditionLookup';
import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { Direction } from 'sorting';
import { Column } from 'apps/page-builder/pages/PageLibrary/ManagePagesTable';
import { BrowserRouter } from 'react-router-dom';
import { ConditionControllerService } from 'apps/page-builder/generated/services/ConditionControllerService';
import { ModalRef } from '@trussworks/react-uswds';

const pageContext = {
    currentPage: 1,
    filter: '',
    setFilter: jest.fn(),
    sortBy: Column.PageName,
    setSortBy: jest.fn(),
    sortDirection: Direction.Ascending,
    setSortDirection: jest.fn(),
    searchQuery: '',
    setSearchQuery: jest.fn(),
    setCurrentPage: jest.fn(),
    pageSize: 10,
    setPageSize: jest.fn(),
    isLoading: false,
    setIsLoading: jest.fn()
};
const addConditions = jest.fn();
const mockSearchConditionUsingPost = jest.spyOn(ConditionControllerService, 'searchConditionsUsingPost');

beforeEach(async () => {
    mockSearchConditionUsingPost.mockResolvedValue({
        content: [
            {
                conditionShortNm: 'test name',
                id: '10056',
                familyCd: 'test family',
                investigationFormCd: 'test code',
                progAreaCd: 'test area',
                statusCd: 'A'
            }
        ],
        totalElements: 1,
        totalPages: 1,
        size: 10,
        number: 1,
        numberOfElements: 1,
        first: true,
        last: true,
        empty: false
    });
});

afterEach(() => {
    jest.resetAllMocks();
});

describe('QuickConditionLookup', () => {
    it('should render successfully', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const { baseElement } = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(baseElement).toBeTruthy();
        });
    });

    it('should fetch  the conditions when mounted', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const { container } = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(mockSearchConditionUsingPost).toHaveBeenCalled();
        });
    });

    it('has the correct title', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const { getByText } = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        const title = getByText('Search and add condition(s)');

        await waitFor(() => {
            expect(title).toBeInTheDocument();
        });
    });

    it('has a search bar', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const { container } = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        const searchBar = await screen.findByTestId('condition-search');

        await waitFor(() => {
            expect(searchBar).toBeInTheDocument();
        });
    });

    it('has a search button', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const { container } = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        const searchBtn = await screen.findByTestId('condition-search-btn');

        await waitFor(() => {
            expect(searchBtn).toBeInTheDocument();
        });
    });

    it('should display the correct table headers', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const { container } = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        const condition = await screen.findByText('Condition');
        const conditionCode = await screen.findByText('Code');
        const programArea = await screen.findByText('Program area');
        const conditionFamily = await screen.findByText('Condition Family');
        const investigationPage = await screen.findByText('Investigation page');
        const status = await screen.findByText('Status');

        await waitFor(() => {
            expect(condition).toBeInTheDocument();
            expect(conditionCode).toBeInTheDocument();
            expect(programArea).toBeInTheDocument();
            expect(conditionFamily).toBeInTheDocument();
            expect(investigationPage).toBeInTheDocument();
            expect(status).toBeInTheDocument();
        });
    });

    it('should display the correct table data', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const {} = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        const condition = await screen.findByText('test name');
        const conditionCode = await screen.findByText('test code');
        const programArea = await screen.findByText('test area');
        const conditionFamily = await screen.findByText('test family');
        const status = await screen.findByText('Active');

        await waitFor(() => {
            expect(condition).toBeInTheDocument();
            expect(conditionCode).toBeInTheDocument();
            expect(programArea).toBeInTheDocument();
            expect(conditionFamily).toBeInTheDocument();
            expect(status).toBeInTheDocument();
        });
    });

    it('should have a cancel button', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const { getByTestId } = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        const cancelBtn = getByTestId('condition-cancel-btn');

        await waitFor(() => {
            expect(cancelBtn).toBeInTheDocument();
        });
    });

    it('should have an add condition button', async () => {
        const modal = { current: null };
        const createConditionModal = { current: null };

        const { getByTestId } = render(
            <BrowserRouter>
                <PagesContext.Provider value={pageContext}>
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={addConditions}
                        createConditionModal={createConditionModal}
                    />
                </PagesContext.Provider>
            </BrowserRouter>
        );

        const addBtn = getByTestId('condition-add-btn');

        await waitFor(() => {
            expect(addBtn).toBeInTheDocument();
        });
    });

    describe('when the cancel button is clicked', () => {
        it('should close the modal', async () => {
            const modal: React.RefObject<ModalRef> = { current: null };
            const createConditionModal = { current: null };

            const { getByTestId } = render(
                <BrowserRouter>
                    <PagesContext.Provider value={pageContext}>
                        <QuickConditionLookup
                            modal={modal}
                            addConditions={addConditions}
                            createConditionModal={createConditionModal}
                        />
                    </PagesContext.Provider>
                </BrowserRouter>
            );

            const cancelBtn = getByTestId('condition-cancel-btn');
            act(() => {
                cancelBtn.dispatchEvent(new MouseEvent('click', { bubbles: true }));
            });

            await waitFor(() => {
                expect(modal.current?.modalIsOpen).toBeFalsy();
            });
        });
    });

    describe('when the search button is clicked', () => {
        it('should search for the condition', async () => {
            const modal = { current: null };
            const createConditionModal = { current: null };

            const { getByTestId } = render(
                <BrowserRouter>
                    <PagesContext.Provider value={pageContext}>
                        <QuickConditionLookup
                            modal={modal}
                            addConditions={addConditions}
                            createConditionModal={createConditionModal}
                        />
                    </PagesContext.Provider>
                </BrowserRouter>
            );

            const searchBtn = getByTestId('condition-search-btn');
            act(() => {
                searchBtn.dispatchEvent(new MouseEvent('click', { bubbles: true }));
            });

            await waitFor(() => {
                expect(mockSearchConditionUsingPost).toHaveBeenCalled();
            });
        });

        it('should call the searchConditionUsingPost with the correct parameters', async () => {
            const modal = { current: null };
            const createConditionModal = { current: null };

            const { container, getByTestId } = render(
                <BrowserRouter>
                    <PagesContext.Provider value={pageContext}>
                        <QuickConditionLookup
                            modal={modal}
                            addConditions={addConditions}
                            createConditionModal={createConditionModal}
                        />
                    </PagesContext.Provider>
                </BrowserRouter>
            );

            const searchBtn = getByTestId('condition-search-btn');

            // type data into search bar
            const searchBar = await screen.findByTestId('condition-search');
            // type hello into searchBar
            act(() => {
                fireEvent.change(searchBar, { target: { value: 'hello' } });
            });
            // click search button
            act(() => {
                searchBtn.dispatchEvent(new MouseEvent('click', { bubbles: true }));
            });

            await waitFor(() => {
                expect(mockSearchConditionUsingPost).toHaveBeenCalledWith({
                    authorization: 'Bearer undefined',
                    page: 1,
                    search: {
                        searchText: 'hello'
                    },
                    size: 10
                });
            });
        });
    });

    describe('when the add button is clicked', () => {
        it('should add the selected conditions', async () => {
            const modal = { current: null };
            const createConditionModal = { current: null };

            const { getByTestId } = render(
                <BrowserRouter>
                    <PagesContext.Provider value={pageContext}>
                        <QuickConditionLookup
                            modal={modal}
                            addConditions={addConditions}
                            createConditionModal={createConditionModal}
                        />
                    </PagesContext.Provider>
                </BrowserRouter>
            );

            const addBtn = getByTestId('modal-condition-add-btn');
            act(() => {
                addBtn.dispatchEvent(new MouseEvent('click', { bubbles: true }));
            });

            await waitFor(() => {
                expect(addConditions).toHaveBeenCalled();
            });
        });
    });
});
