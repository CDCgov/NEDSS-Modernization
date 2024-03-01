import { AdditionalQuestionFields } from 'apps/page-builder/components/AddQuestion/QuestionForm';
import { AdministrativeFields } from 'apps/page-builder/components/AddQuestion/fields/AdministrativeFields';
import { BasicInformationFields } from 'apps/page-builder/components/AddQuestion/fields/BasicInformationFields';
import { DataMartFields } from 'apps/page-builder/components/AddQuestion/fields/DataMartFields';
import { MessagingFields } from 'apps/page-builder/components/AddQuestion/fields/MessagingFields';
import { QuestionSpecificFields } from 'apps/page-builder/components/AddQuestion/fields/QuestionSpecificFields';
import { UserInterfaceFields } from 'apps/page-builder/components/AddQuestion/fields/UserInterfaceFields';
import { HorizontalRule } from 'apps/page-builder/components/FormDivider/HorizontalRule';
import { PagesQuestion } from 'apps/page-builder/generated';
import { UpdatePageQuestionRequest } from 'apps/page-builder/hooks/api/useUpdatePageQuestion';
import { EditFields } from './EditFields';
import styles from './edit-question-form.module.scss';

export type EditPageQuestionForm = Omit<UpdatePageQuestionRequest & AdditionalQuestionFields, 'codeSet'> & {
    codeSet: 'LOCAL' | 'PHIN';
};

type Props = {
    page: number;
    question?: PagesQuestion;
    onFindValueSet: () => void;
};

export const EditPageQuestion = ({ page, question, onFindValueSet }: Props) => {
    return (
        <div className={styles.form}>
            <BasicInformationFields editing />
            <QuestionSpecificFields editing published={question?.isPublished} onFindValueSet={onFindValueSet} />
            <HorizontalRule />
            <UserInterfaceFields published={question?.isPublished} />
            <EditFields />
            <HorizontalRule />
            <DataMartFields editing page={page} questionId={question?.id} />
            <HorizontalRule />
            <MessagingFields />
            <HorizontalRule />
            <AdministrativeFields />
        </div>
    );
};
