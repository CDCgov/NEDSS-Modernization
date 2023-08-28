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
    if (filter?.newestToOldest || (!search && !filter?.questionType && !filter?.questionSubGroup)) {
        return QuestionControllerService.findAllQuestionsUsingGet({
            authorization,
            page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
            size: pageSize,
            sort
        }).then((response: Page_Question_) => {
            return response || [];
        });
    } else {
        const request = {
            search,
            questionType: filter.questionType,
            questionSubGroup: filter.questionSubGroup
        };
        return QuestionControllerService.findQuestionsUsingPost({
            authorization,
            request,
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
            codeSetNm: 'NBS_QUES_SUBGROUP'
        }).then((response: any) => {
            const data = response || [];
            console.log('data....', data);
            const familyList: any = [];
            data.map((each: { localCode: never }) => {
                familyList.push({ name: each.localCode, value: each.localCode });
            });
            setFamilyOptions(familyList);
        });
    };
    useEffect(() => {
        fetchFamilyOptions();
    }, []);

    return familyOptions;
};
