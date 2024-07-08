import { render } from "@testing-library/react"
import { Pagination } from "./Pagination"
import { PageProvider } from "page";
import { BrowserRouter } from "react-router-dom";

describe('When Pagination renders', () => {
    it('should render the right number of tiles', () => {
        const { container } = render(
            <BrowserRouter>
                <PageProvider>
                    <Pagination path="page" total={25} pageSize={10} />
                </PageProvider>
            </BrowserRouter>
        );
        const buttons = container.getElementsByTagName('button');
        expect(buttons).toHaveLength(3);
    });
});
