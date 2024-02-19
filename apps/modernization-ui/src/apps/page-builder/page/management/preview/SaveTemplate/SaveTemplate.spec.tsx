import { SaveTemplate } from "./SaveTemplate";
import { render } from "@testing-library/react";
import { PageManagementProvider } from "../../usePageManagement";
import { PagesResponse } from "apps/page-builder/generated";
import { AlertProvider } from "alert";

describe('When SaveTemplate renders', () => {
    const modalRef = { current: null };
    const content: PagesResponse = {
        id: 123,
        name: 'Test Page',
        status: 'status',
        tabs: [
            {
                id: 123456,
                name: 'Test Tab',
                visible: true,
                order: 1,
                sections: [
                    {
                        id: 1234,
                        name: 'Section1',
                        visible: true,
                        order: 1,
                        subSections: []
                    },
                    {
                        id: 5678,
                        name: 'Section2',
                        visible: true,
                        order: 2,
                        subSections: []
                    }
                ]
            }
        ]
    };
    it('should display inputs', () => {
        const { container } = render(
            <PageManagementProvider page={content} fetch={jest.fn()} refresh={jest.fn()}>
                <AlertProvider>
                    <SaveTemplate modalRef={modalRef} />
                </AlertProvider>
            </PageManagementProvider>
        )
        const inputs = container.getElementsByTagName('input');
        expect(inputs).toHaveLength(2);
    });
    it('should display input labels', () => {
        const { container } = render(
            <PageManagementProvider page={content} fetch={jest.fn()} refresh={jest.fn()}>
                <AlertProvider>
                    <SaveTemplate modalRef={modalRef} />
                </AlertProvider>
            </PageManagementProvider>
        )
        const label = container.getElementsByTagName('label');
        expect(label).toHaveLength(2);
    });
});
