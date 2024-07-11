import { render } from "@testing-library/react"
import { Pagination } from "./Pagination"
import { PageProvider } from "page";
import { BrowserRouter } from "react-router-dom";

describe('When Pagination renders', () => {
    it('should render the right number of tiles', () => {
        const Wrapper = () => {
            return (
                <BrowserRouter>
                    <PageProvider>
                        <Pagination />
                    </PageProvider>
                </BrowserRouter>
            );
        }
        const { container } = render(<Wrapper />);
        const buttons = container.getElementsByTagName('button');
        expect(buttons).toHaveLength(0);
    });
});
