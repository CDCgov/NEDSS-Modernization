import { TabControllerService } from '../generated';

export const addTab = async (token: string, page: number, request: { name: string; visible: boolean }) => {
    return await TabControllerService.createTabUsingPost({
        page: page,
        authorization: token,
        request: request
    });
};

export const updateTab = async (
    token: string,
    page: number,
    request: { name: string; visible: boolean },
    tabId: number
) => {
    return await TabControllerService.updateTabUsingPut({
        authorization: token,
        page: page,
        request: request,
        tabId: tabId
    });
};

export const deleteTab = async (token: string, page: number, tabId: number) => {
    return await TabControllerService.deleteTabUsingDelete({
        authorization: token,
        page: page,
        tabId: tabId
    });
};
