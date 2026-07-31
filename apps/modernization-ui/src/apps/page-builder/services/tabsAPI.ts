import { TabControllerService } from 'apps/page-builder/generated';

export const addTab = async (page: number, request: { name: string; visible: boolean }) => {
    return await TabControllerService.createTab({
        page,
        requestBody: request,
    });
};

export const updateTab = async (page: number, request: { name: string; visible: boolean }, tabId: number) => {
    return await TabControllerService.updateTab({
        page,
        requestBody: request,
        tabId,
    });
};

export const deleteTab = async (page: number, tabId: number) => {
    return await TabControllerService.deleteTab({ page, tabId });
};
