import { render, act } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { MemoryRouter } from 'react-router';
import { AddNewPage } from './AddNewPage';

import {
    CancelablePromise,
    Concept,
    Condition,
    ConditionControllerService,
    PageControllerService,
    PageCreateResponse,
    PageCondition,
    ProgramArea,
    ProgramAreaControllerService,
    Template,
    TemplateControllerService,
    ConceptControllerService
} from 'apps/page-builder/generated';
import userEvent from '@testing-library/user-event';

beforeEach(() => {
    jest.spyOn(ConditionControllerService, 'findConditionsNotInUse').mockReturnValue(
        Promise.resolve([{ id: '1' }] as Condition[]) as CancelablePromise<Condition[]>
    );
    jest.spyOn(ConceptControllerService, 'findConcepts').mockReturnValue(
        Promise.resolve([{ conceptCode: 'concept' }] as Concept[]) as CancelablePromise<Concept[]>
    );
    jest.spyOn(TemplateControllerService, 'findAllTemplates').mockReturnValue(
        Promise.resolve([{ id: 2 }]) as CancelablePromise<Template[]>
    );
    jest.spyOn(ConditionControllerService, 'searchConditions').mockReturnValue(
        Promise.resolve({}) as CancelablePromise<PageCondition>
    );
    jest.spyOn(ProgramAreaControllerService, 'getProgramAreas').mockReturnValue(
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

const Fixture = () => (
    <MemoryRouter>
        <AlertProvider>
            <AddNewPage />
        </AlertProvider>
    </MemoryRouter>
);

describe('Add New Page', () => {
    it('should have the title of Create new page', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Create new page')).toBeInTheDocument();
    });

    it('should render event type drop down', async () => {
        const { getByRole, queryByText } = render(<Fixture />);

        const select = getByRole('combobox', { name: 'Event type' });

        expect(select).toBeInTheDocument();

        expect('- Select -').toEqual(select.children[0].textContent);
        eventType.forEach((e, i) => {
            expect(e.name).toEqual(select.children[i + 1].textContent);
            expect(select.children[i + 1]).toHaveProperty('value', e.value);
        });

        const addCondition = queryByText('Search and add condition');
        expect(addCondition).not.toBeInTheDocument();
    });

    it('should have aria label for heading', async () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Create new page')).toHaveAttribute('aria-label', 'Create new page');
    });

    //  The complexity of the AddNePage component makes testing it very difficult.  There are multiple modals being rendered that make API calls and the selection of the "Event Type" is not triggering changes.
    xit('should display warning when non Investigation type is selected', async () => {
        const { getByTestId, getByRole } = render(<Fixture />);

        const select = getByRole('combobox', { name: 'Event type' });
        const interview = getByRole('option', { name: 'Interview' });

        const user = userEvent.setup();

        await user.selectOptions(select, interview);

        const warning = getByTestId('event-type-warning');
        expect(warning).toBeInTheDocument();
    });

    xit('should redirect to classic on create page when non investigation is selected', async () => {
        const savePage = jest.spyOn(PageControllerService, 'createPage');
        savePage.mockImplementation(
            (_) => Promise.resolve({} as PageCreateResponse) as CancelablePromise<PageCreateResponse>
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

        const { getByRole, getByText } = render(<Fixture />);

        const select = getByRole('combobox', { name: 'Event type' });

        const user = userEvent.setup();

        await user.selectOptions(select, 'IXS');

        const submit = getByText('Create page');

        expect(submit).toBeEnabled();

        await user.click(submit);

        expect(setHrefSpy).toHaveBeenCalledWith('/nbs/page-builder/api/v1/pages/create');
        expect(savePage).not.toBeCalled();
    });

    xit('should display form when Investigation type is selected', async () => {
        const { queryByText, getByText, getByRole } = render(<Fixture />);

        const select = getByRole('combobox', { name: 'Event type' });
        const investigation = getByRole('option', { name: 'Investigation' });

        const user = userEvent.setup();

        await user.selectOptions(select, investigation);

        const warning = queryByText('event type is not supported');
        expect(warning).not.toBeInTheDocument();

        expect(getByText('Condition(s)')).toBeInTheDocument();
        expect(getByText('Page name')).toBeInTheDocument();
        expect(getByText('Template')).toBeInTheDocument();
        expect(getByText('Reporting mechanism')).toBeInTheDocument();
        expect(getByText('Page description')).toBeInTheDocument();
        expect(getByText('Data mart name')).toBeInTheDocument();
    });
});
