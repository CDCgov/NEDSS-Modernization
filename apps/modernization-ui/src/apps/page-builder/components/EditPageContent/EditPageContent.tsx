import './EditPageContent.scss';

type Props = {
    content: string;
};

export const EditPageContent = ({ content }: Props) => {
    return <div className="edit-page-content">{content} Content</div>;
};
