
import userEvent from "@testing-library/user-event";
import { Columns } from "./Columns"
import { render, screen } from "@testing-library/react";
import ColumnProvider from "apps/search/context/ColumnContextProvider";
import { createContext } from 'react';
import { DropResult } from 'react-beautiful-dnd';

describe('When Columns renders', () => {
    it('should display the right amount of tiles after click', () => {

        type DisplayColumn = {
            id: string;
            name: string;
            sortable: boolean;
            visible: boolean;
        };

        type ColumnContextProps = {
            handleDragEnd: (result: DropResult) => void;
            displayColumns: DisplayColumn[];
            saveColumns: () => void;
            resetColumns: () => void;
        };

        const ColumnContext = createContext<ColumnContextProps | undefined>(undefined);

        const columns = [
            {
                id: 'lastNm',
                name: 'Legal name',
                fixed: true,
                sortable: true,
                render: jest.fn()
            },
            { id: 'birthTime', name: 'Date of birth', sortable: true, render: jest.fn() },
            { id: 'sex', name: 'Sex', sortable: true, render: jest.fn() },
            { id: 'id', name: 'Patient ID', render: jest.fn() },
            { id: 'address', name: 'Address', render: jest.fn() },
            { id: 'phoneNumber', name: 'Phone', render: jest.fn() },
            { id: 'names', name: 'Other names', render: jest.fn() },
            { id: 'identification', name: 'ID', render: jest.fn() },
            { id: 'email', name: 'Email', render: jest.fn() }
        ];

        const displayColumns = [
            { id: 'lastNm', name: 'Legal name', sortable: false, visible: true },
            { id: 'birthTime', name: 'Date of birth', sortable: false, visible: true },
            { id: 'sex', name: 'Sex', sortable: false, visible: true },
            { id: 'id', name: 'Patient ID', sortable: false, visible: true },
            { id: 'address', name: 'Address', sortable: true, visible: true },
            { id: 'phoneNumber', name: 'Phone', sortable: true, visible: true },
            { id: 'names', name: 'Other names', sortable: true, visible: true },
            { id: 'identification', name: 'ID', sortable: true, visible: true },
            { id: 'email', name: 'Email', sortable: true, visible: true }
        ];

        const { container } = render(
            <ColumnProvider>
                <ColumnContext.Provider value={{ displayColumns: displayColumns, handleDragEnd: jest.fn, saveColumns: jest.fn, resetColumns: jest.fn }}>
                    <Columns />
                </ColumnContext.Provider>
            </ColumnProvider>
        );

        const button = screen.getByTestId('action');
        userEvent.click(button);
        const content = container.getElementsByClassName('menuContent');
        expect(content).toHaveLength(1);
    });
});
