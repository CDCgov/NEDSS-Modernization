/* eslint-disable camelcase */
import { QuestionControllerService, Page_Question_, ValueSetControllerService } from 'apps/page-builder/generated';
import { useContext, useEffect, useState } from 'react';
import { UserContext } from 'user';

export const fetchQuestion = (
    authorization: string,
    search: any,
    sort: any,
    currentPage: number,
    pageSize: number,
    filter: any
) => {
    if (search) {
        const request = {
            search,
            questionType: filter.questionType,
            questionSubGroup: filter.questionSubGroup
        };
        QuestionControllerService.findQuestionsUsingPost({
            authorization,
            request,
            page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
            size: pageSize,
            sort
        }).then((response: Page_Question_) => {
            return response || [];
        });
    } else {
        return QuestionControllerService.findAllQuestionsUsingGet({
            authorization,
            page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
            size: pageSize,
            sort
        }).then((response: Page_Question_) => {
            return response || [];
        });
    }
};

export const useSubGroupAPI = () => {
    const [familyOptions, setFamilyOptions] = useState([]);
    const { state } = useContext(UserContext);

    const fetchFamilyOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: `Bearer ${state.getToken()}`,
            codeSetNm: 'CONDITION_FAMILY'
        }).then((response: any) => {
            const data = response || [];
            const familyList: any = [];
            data.map((each: { value: never }) => {
                familyList.push({ name: each.value, value: each.value });
            });
            setFamilyOptions(familyList);
        });
    };
    useEffect(() => {
        fetchFamilyOptions();
    }, []);

    return familyOptions;
};
