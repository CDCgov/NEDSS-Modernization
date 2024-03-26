import { TabControllerService } from 'apps/page-builder/generated';

export const addTab = async (page: number, request: { name: string; visible: boolean }) => {
    return await TabControllerService.createTab({
        page: page,
        requestBody: request
    });
};

export const updateTab = async (page: number, request: { name: string; visible: boolean }, tabId: number) => {
    return await TabControllerService.updateTab({
        page: page,
        requestBody: request,
        tabId: tabId
    });
};

export const deleteTab = async (page: number, tabId: number) => {
    return await TabControllerService.deleteTab({ page: page, tabId: tabId });
};
