/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import { Question } from "./Question";

export type Subsection = {
    id: number;
    name: string;
    pageQuestions: Question[];
    visible: string;
};
