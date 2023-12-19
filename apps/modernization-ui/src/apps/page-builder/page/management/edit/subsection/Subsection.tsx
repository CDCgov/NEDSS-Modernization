import { PageStaticControllerService, PagesQuestion, PagesSubSection } from 'apps/page-builder/generated';
import { SubsectionHeader } from './SubsectionHeader';
import styles from './subsection.module.scss';
import { useState } from 'react';
import { Question } from 'apps/page-builder/components/Question/Question';
import { Button, Icon } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { authorization } from 'authorization';
import { usePageManagement } from '../../usePageManagement';

type Props = {
    subsection: PagesSubSection;
    onAddQuestion: () => void;
};
export const Subsection = ({ subsection, onAddQuestion }: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);
    const { page } = usePageManagement();

    const { showAlert } = useAlert();

    const tempQuestion: PagesQuestion = {
        id: 1154585,
        name: 'something label',
        order: 7
    };

    const handleAlert = (message: string) => {
        showAlert({ message: message, type: 'success' });
    };

    const deleteStaticElement = (id: number) => {
        PageStaticControllerService.deleteStaticElementUsingDelete({
            authorization: authorization(),
            page: page.id,
            request: { componentId: id }
        }).then(() => {
            handleAlert(`deleted a static element`);
        });
    };

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    return (
        <div className={styles.subsection}>
            <SubsectionHeader
                name={subsection.name ?? ''}
                id={subsection.id}
                questionCount={subsection.questions?.length ?? 0}
                onAddQuestion={onAddQuestion}
                onExpandedChange={handleExpandedChange}
                isExpanded={isExpanded}
            />
            <Question question={tempQuestion} />
            <Button type="button" onClick={() => deleteStaticElement(tempQuestion.id)}>
                <Icon.Delete size={3} /> Delete
            </Button>
        </div>
    );
};
