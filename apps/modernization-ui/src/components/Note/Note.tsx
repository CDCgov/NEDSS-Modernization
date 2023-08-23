import { ReactNode } from 'react';
import './Note.scss';

type NoteProps = {
    children: ReactNode;
};

const Note = ({ children }: NoteProps) => (
    <div>
        <span className="note-title">Note:</span>
        {children}
    </div>
);

export { Note };
