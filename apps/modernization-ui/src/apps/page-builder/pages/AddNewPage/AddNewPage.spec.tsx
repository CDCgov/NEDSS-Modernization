import { fireEvent, render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import { AddNewPage } from './AddNewPage';

import {
    CancelablePromise,
    Concept,
    Condition,
    ConditionControllerService,
    PageControllerService,
    PageCreateResponse,
    Page_Condition_,
    Page_Template_,
    ProgramArea,
    ProgramAreaControllerService,
    TemplateControllerService,
    ValueSetControllerService
} from 'apps/page-builder/generated';

beforeEach(() => {
    jest.spyOn(ConditionControllerService, 'findAllConditionsUsingGet').mockReturnValue(
        Promise.resolve([{ id: '1' }] as Condition[]) as CancelablePromise<Condition[]>
    );
    jest.spyOn(ValueSetControllerService, 'findConceptsByCodeSetNameUsingGet').mockReturnValue(
        Promise.resolve([{ conceptCode: 'concept' }] as Concept[]) as CancelablePromise<Concept[]>
    );
    jest.spyOn(TemplateControllerService, 'findAllTemplatesUsingGet').mockReturnValue(
        Promise.resolve({ content: [{ id: 2 }] }) as CancelablePromise<Page_Template_>
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
    it('should render event type drop down', async () => {
        const { findByText, findByTestId, queryByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </BrowserRouter>
        );
        const label = await findByText('Event type');
        expect(label).toBeInTheDocument();

        const select = await findByTestId('eventTypeDropdown');
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
        const { findByTestId, findByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </BrowserRouter>
        );
        const label = await findByText('Event type');
        expect(label).toBeInTheDocument();

        const select = await findByTestId('eventTypeDropdown');
        expect(select).toBeInTheDocument();

        fireEvent.change(select, { target: { value: 'IXS' } });
        const warning = await findByTestId('event-type-warning');
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

        const { findByTestId, findByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </BrowserRouter>
        );

        const label = await findByText('Event type');
        const select = await findByTestId('eventTypeDropdown');
        fireEvent.change(select, { target: { value: 'IXS' } });

        const submit = await findByText('Create page');
        fireEvent.click(submit);
        expect(setHrefSpy).toHaveBeenCalledWith('/nbs/ManagePage.do?method=addPageLoad');
        expect(savePage).not.toBeCalled();
    });

    it('should display form when Investigation type is selected', async () => {
        const { findByTestId, findByText, queryByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddNewPage />
                </AlertProvider>
            </BrowserRouter>
        );
        const label = await findByText('Event type');
        expect(label).toBeInTheDocument();

        const select = await findByTestId('eventTypeDropdown');
        expect(select).toBeInTheDocument();

        fireEvent.change(select, { target: { value: 'INV' } });
        const warning = queryByText('event type is not supported');
        expect(warning).not.toBeInTheDocument();

        const conditionSelect = await findByText('Condition(s)');
        expect(conditionSelect).toBeInTheDocument();
    });
});
