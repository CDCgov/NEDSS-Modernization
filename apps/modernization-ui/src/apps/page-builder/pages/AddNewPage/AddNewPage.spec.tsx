import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { MemoryRouter } from 'react-router-dom';
import { AddNewPage } from './AddNewPage';

import {
    CancelablePromise,
    Concept,
    Condition,
    ConditionControllerService,
    PageControllerService,
    PageCreateResponse,
    Page_Condition_,
    ProgramArea,
    ProgramAreaControllerService,
    Template,
    TemplateControllerService,
    ConceptControllerService
} from 'apps/page-builder/generated';
import userEvent from '@testing-library/user-event';

beforeEach(() => {
    jest.spyOn(ConditionControllerService, 'findAllConditionsUsingGet').mockReturnValue(
        Promise.resolve([{ id: '1' }] as Condition[]) as CancelablePromise<Condition[]>
    );
    jest.spyOn(ConceptControllerService, 'findConceptsUsingGet').mockReturnValue(
        Promise.resolve([{ conceptCode: 'concept' }] as Concept[]) as CancelablePromise<Concept[]>
    );
    jest.spyOn(TemplateControllerService, 'findAllTemplatesUsingGet').mockReturnValue(
        Promise.resolve([{ id: 2 }]) as CancelablePromise<Template[]>
    );
    jest.spyOn(ConditionControllerService, 'searchConditionsUsingPost').mockReturnValue(
        Promise.resolve({}) as CancelablePromise<Page_Condition_>
    );
    jest.spyOn(ProgramAreaControllerService, 'getProgramAreasUsingGet').mockReturnValue(
        Promise.resolve([] as ProgramArea[]) as CancelablePromise<ProgramArea[]>
    );
});

const eventType = [
    { value: 'CON', name: 'Contact Record' },
    { value: 'IXS', name: 'Interview' },
    { value: 'INV', name: 'Investigation' },
    { value: 'ISO', name: 'Lab Isolate Tracking' },
    { value: 'LAB', name: 'Lab Report' },
    { value: 'SUS', name: 'Lab Susceptibility' },
    { value: 'VAC', name: 'Vaccination' }
];

describe('Add New Page', () => {
    it('should have the title of Create new page', () => {
        const { getByText } = render(
            <MemoryRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </MemoryRouter>
        );

        expect(getByText('Create new page')).toBeInTheDocument();
    });

    it('should render event type drop down', async () => {
        const { queryByText } = render(
            <MemoryRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </MemoryRouter>
        );

        const label = screen.getByText('Event type');
        expect(label).toBeInTheDocument();

        const select = screen.getByTestId('eventTypeDropdown');
        expect(select).toBeInTheDocument();

        expect('- Select -').toEqual(select.children[0].textContent);
        eventType.forEach((e, i) => {
            expect(e.name).toEqual(select.children[i + 1].textContent);
            expect(select.children[i + 1]).toHaveProperty('value', e.value);
        });

        const addCondition = queryByText('Search and add condition');
        expect(addCondition).not.toBeInTheDocument();
    });

    it('should display warning when non Investigation type is selected', async () => {
        render(
            <MemoryRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </MemoryRouter>
        );
        const label = screen.getByText('Event type');
        expect(label).toBeInTheDocument();

        const select = screen.getByTestId('eventTypeDropdown');
        expect(select).toBeInTheDocument();

        fireEvent.change(select, { target: { value: 'IXS' } });
        const warning = screen.getByTestId('event-type-warning');
        expect(warning).toBeInTheDocument();
    });

    it('should redirect to classic on create page when non investigation is selected', async () => {
        const savePage = jest.spyOn(PageControllerService, 'createPageUsingPost');
        savePage.mockImplementation(
            (params) => Promise.resolve({} as PageCreateResponse) as CancelablePromise<PageCreateResponse>
        );

        const { location } = window;
        const setHrefSpy = jest.fn((href) => href);
        const mockLocation = { ...location };
        Object.defineProperty(mockLocation, 'href', {
            set: setHrefSpy
        });

        // @ts-expect-error : location is mocked to check that the href is changed
        delete window.location;
        window.location = mockLocation;

        render(
            <MemoryRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </MemoryRouter>
        );

        const select = screen.getByTestId('eventTypeDropdown');
        userEvent.selectOptions(select, 'IXS');

        const submit = screen.getByText('Create page');

        await waitFor(() => {
            expect(submit).toBeEnabled();
        });

        userEvent.click(submit);
        expect(setHrefSpy).toHaveBeenCalledWith('/nbs/page-builder/api/v1/pages/create');
        expect(savePage).not.toBeCalled();
    });

    it('should display form when Investigation type is selected', async () => {
        const { queryByText, getByText } = render(
            <MemoryRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </MemoryRouter>
        );
        const label = getByText('Event type');
        expect(label).toBeInTheDocument();

        const select = screen.getByTestId('eventTypeDropdown');
        expect(select).toBeInTheDocument();

        userEvent.selectOptions(select, 'INV');
        const warning = queryByText('event type is not supported');
        expect(warning).not.toBeInTheDocument();

        expect(getByText('Condition(s)')).toBeInTheDocument();
        expect(getByText('Page name')).toBeInTheDocument();
        expect(getByText('Template')).toBeInTheDocument();
        expect(getByText('Reporting mechanism')).toBeInTheDocument();
        expect(getByText('Page description')).toBeInTheDocument();
        expect(getByText('Data mart name')).toBeInTheDocument();
    });

    it('should have aria label for heading', async () => {
        const { getByText } = render(
            <MemoryRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </MemoryRouter>
        );
        expect(getByText('Create new page')).toHaveAttribute('aria-label', 'Create new page');
    });
});
