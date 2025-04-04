import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { Modal } from 'design-system/modal';
import React, { ReactNode, useMemo, useState } from 'react';
import styles from './import-modal.module.scss';

type Props = {
    id: string;
    title?: string;
    infoText?: string;
    dropSectionContent?: ReactNode | ReactNode[];
    accept?: string; // mime type to accept. Ex: 'application/json,application/pdf'
    visible: boolean;
    error?: string;
    onImport: (file: File) => void;
    onCancel: () => void;
};
export const ImportModal = ({
    id,
    title = 'Import file',
    infoText,
    dropSectionContent,
    accept,
    visible,
    error,
    onImport,
    onCancel
}: Props) => {
    const [selectedFile, setSelectedFile] = useState<File | undefined>();
    const [dragOver, setDragOver] = useState(false);

    const handleAccept = () => {
        if (selectedFile) {
            onImport(selectedFile);
        }
        setSelectedFile(undefined);
    };

    const handleCancel = () => {
        setSelectedFile(undefined);
        onCancel();
    };

    const handleFileDrop = (e: React.DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        const file = e.dataTransfer.files[0];
        if (accept === undefined || accept?.includes(file.type)) {
            setSelectedFile(e.dataTransfer.files[0]);
        }
        setDragOver(false);
    };

    const handleDragOver = (e: React.DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        setDragOver(true);
    };

    const handleDragEnd = (e: React.DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        setDragOver(false);
    };

    const fileIcon: 'file' | 'file-json' | 'file-pdf' = useMemo(() => {
        switch (selectedFile?.type) {
            case 'application/json':
                return 'file-json';
            case 'application/pdf':
                return 'file-pdf';
            default:
                return 'file';
        }
    }, [selectedFile?.type]);

    const footer = () => (
        <>
            <Button outline onClick={handleCancel} data-close-modal>
                Cancel
            </Button>
            <Button disabled={!selectedFile} onClick={handleAccept} data-close-modal>
                Import
            </Button>
        </>
    );

    return (
        <Shown when={visible}>
            <Modal id={id} size="small" title={title} onClose={onCancel} footer={footer}>
                <div
                    className={styles.importModal}
                    onDragOver={handleDragOver}
                    onDragLeave={handleDragEnd}
                    onDragEnd={handleDragEnd}
                    onDrop={handleFileDrop}>
                    <Shown when={error !== undefined}>
                        <div className={styles.errorBar} />
                    </Shown>
                    <div className={styles.inputSection}>
                        <Shown when={infoText !== undefined}>
                            <div className={styles.info}>{infoText}</div>
                        </Shown>
                        <Shown when={error !== undefined}>
                            <div className={styles.errorText}>{error}</div>
                        </Shown>
                        <Shown
                            when={selectedFile !== undefined}
                            fallback={
                                <Unselected
                                    htmlFor={`${id}-file-input`}
                                    dragOver={dragOver}
                                    error={error}
                                    dropSectionContent={dropSectionContent}
                                />
                            }>
                            <Selected
                                htmlFor={`${id}-file-input`}
                                fileIcon={fileIcon}
                                fileName={selectedFile?.name ?? ''}
                            />
                        </Shown>
                        <input
                            id={`${id}-file-input`}
                            type="file"
                            accept={accept}
                            onChange={(e) => setSelectedFile(e.target.files?.[0])}
                        />
                    </div>
                </div>
            </Modal>
        </Shown>
    );
};

type UnselectedProps = {
    htmlFor: string;
    dragOver: boolean;
    error?: string;
    dropSectionContent?: ReactNode | ReactNode[];
};
const Unselected = ({ htmlFor, dragOver, error, dropSectionContent }: UnselectedProps) => {
    return (
        <label
            htmlFor={htmlFor}
            className={classNames(
                styles.selectFileLabel,
                dragOver ? styles.dragOver : '',
                error ? styles.errorBorder : ''
            )}>
            <Shown
                when={dropSectionContent !== undefined}
                fallback={
                    <>
                        Drag file here or <span>choose from folder</span>
                    </>
                }>
                {dropSectionContent}
            </Shown>
        </label>
    );
};

type SelectedProps = {
    htmlFor: string;
    fileIcon: 'file' | 'file-json' | 'file-pdf';
    fileName: string;
};
const Selected = ({ htmlFor, fileIcon, fileName }: SelectedProps) => {
    return (
        <div className={styles.selectedFile}>
            <div>
                <div>Selected file</div>
                <label htmlFor={htmlFor}>Change file</label>
            </div>
            <div className={styles.fileInfo}>
                <Icon name={fileIcon} sizing="large" />
                {fileName}
            </div>
        </div>
    );
};
