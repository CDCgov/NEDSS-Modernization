import { render } from "@testing-library/react";
import { NameFieldsTable } from "./NameFieldsTable"

describe(' When NameFieldsTable renders', () => {
    it('should display table headings, 7 including colspan', () => {
        const { container } = render(<NameFieldsTable />);
        const headings = container.getElementsByTagName('th');
        expect(headings).toHaveLength(8);
        expect(headings[0]).toHaveTextContent('As of');
        expect(headings[1]).toHaveTextContent('Type');
        expect(headings[2]).toHaveTextContent('Last');
        expect(headings[3]).toHaveTextContent('First');
        expect(headings[4]).toHaveTextContent('Middle');
        expect(headings[5]).toHaveTextContent('Suffix');
    });
});