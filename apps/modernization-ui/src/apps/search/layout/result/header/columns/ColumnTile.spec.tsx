import ColumnProvider from "apps/search/context/ColumnContextProvider";
import { ColumnTile } from "./ColumnTile";
import { render } from "@testing-library/react";

describe('When ColumnTile renders', () => {
    const column = {
        id: 'test',
        name: 'testName',
        sortable: true,
        visible: true
    }
    it('should disable checkbox if ColumnTile lite', () => {
        const { container } = render(
            <ColumnProvider successCallback={jest.fn}>
                <ColumnTile column={column} index={1} lite />
            </ColumnProvider>
        );
        const checkbox = container.getElementsByTagName('input');
        expect(checkbox[0]).toHaveAttribute('disabled');
    });
});
