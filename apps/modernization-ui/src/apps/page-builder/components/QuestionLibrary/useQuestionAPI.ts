/* eslint-disable camelcase */
import {
    QuestionControllerService,
    Page_Question_,
    Question,
    ValueSetControllerService
} from 'apps/page-builder/generated';
import { Status, usePage } from 'page';
import { useContext, useEffect, useState } from 'react';
import { UserContext } from 'user';
//
// type filter = {
//     questionType?: string;
//     questionSubGroup?: string;
//     newestToOldest?: boolean;
// };
export const useQuestionAPI = (search?: string, sort?: string, filter?: any) => {
    const [question, setQuestion] = useState([] as Question[]);
    const { page, firstPage, ready } = usePage();
    const { state } = useContext(UserContext);
    const fetchQuestion = () => {
        setQuestion([]);
        const request = {
            search,
            questionType: filter.questionType,
            questionSubGroup: filter.questionSubGroup
        };
        QuestionControllerService.findQuestionsUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            request,
            page: page.current - 1,
            size: page.pageSize,
            sort
        }).then((response: Page_Question_) => {
            setQuestion(response.content || []);
            ready(response.totalElements || 0, page.current);
        });
    };
    const fetchRecentQuestion = () => {
        QuestionControllerService.findAllQuestionsUsingGet({
            authorization: `Bearer ${state.getToken()}`,
            page: page.current - 1,
            size: page.pageSize,
            sort
        }).then((response: Page_Question_) => {
            setQuestion(response.content || []);
            ready(response.totalElements || 0, page.current);
        });
    };

    useEffect(() => {
        if (page.status === Status.Requested) {
            if (filter.newestToOldest || (!search && !filter?.questionType && !filter.questionSubGroup)) {
                fetchRecentQuestion();
            } else {
                fetchQuestion();
            }
        }
    }, [page]);

    useEffect(() => {
        firstPage();
    }, [search, sort, filter]);

    return question;
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
