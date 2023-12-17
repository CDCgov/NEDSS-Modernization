import { authorization } from 'authorization';
import { TabControllerService } from 'apps/page-builder/generated';

export const addTab = async (page: number, request: { name: string; visible: boolean }) => {
    return await TabControllerService.createTabUsingPost({
        page: page,
        authorization: authorization(),
        request: request
    });
};

export const updateTab = async (page: number, request: { name: string; visible: boolean }, tabId: number) => {
    return await TabControllerService.updateTabUsingPut({
        authorization: authorization(),
        page: page,
        request: request,
        tabId: tabId
    });
};

export const deleteTab = async (page: number, tabId: number) => {
    return await TabControllerService.deleteTabUsingDelete({
        authorization: authorization(),
        page: page,
        tabId: tabId
    });
};
