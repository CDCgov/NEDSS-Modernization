import { Button, Icon, ModalRef, Tag } from '@trussworks/react-uswds';
import { Template } from 'apps/page-builder/generated';
import { useImportTemplate } from 'apps/page-builder/services/useImportTemplate';
import { Spinner } from 'components/Spinner/Spinner';
import React, { useState } from 'react';
import { AlertBanner } from '../AlertBanner/AlertBanner';
import './ImportTemplate.scss';

type ImportTemplateProps = {
    onTemplateCreated: (template: Template) => void;
    modal: React.RefObject<ModalRef>;
};
export const ImportTemplate = ({ modal, onTemplateCreated }: ImportTemplateProps) => {
    const [file, setFile] = useState<File | undefined>();
    const [fileDrag, setFileDrag] = useState(false);
    const { isLoading, error, reset, importTemplate } = useImportTemplate();

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const files = event.target.files;
        if (files == null) {
            setFile(undefined);
        } else {
            setFile(files[0]);
        }
    };

    const handleSubmit = async () => {
        if (file == undefined) {
            return;
        }
        importTemplate(file)
            .then((template) => {
                onTemplateCreated(template);
                reset();
                modal.current?.toggleModal(undefined, false);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    const handleCancel = () => {
        setFile(undefined);
        reset();
        modal.current?.toggleModal();
    };

    const handleDragEnd = (ev: React.DragEvent<HTMLDivElement>) => {
        ev.preventDefault();
        setFileDrag(false);
    };

    const handleDragOver = (ev: React.DragEvent<HTMLDivElement>) => {
        ev.preventDefault();
        setFileDrag(true);
    };

    const handleFileDrop = (event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
        setFileDrag(false);
        setFile(event.dataTransfer.files[0]);
    };

    return (
        <div className="import-template">
            {isLoading ? <Spinner /> : null}
            <div
                className="drop-container"
                onDragLeave={handleDragEnd}
                onDragOver={handleDragOver}
                onDrop={handleFileDrop}>
                {error ? (
                    <div className="banner">
                        <AlertBanner type="error">{error}</AlertBanner>
                    </div>
                ) : null}

                <div className="heading">
                    <label>Import a new template</label>
                </div>
                <label htmlFor="importTempId">
                    <input
                        value={''}
                        onChange={handleFileChange}
                        name="fileInput"
                        type="file"
                        id="importTempId"
                        accept="text/xml"
                        hidden
                    />

                    <div className={`drop-area ${fileDrag ? 'dragged' : ''}`}>
                        <div className="display-flex gap-10">
                            {file ? (
                                <div className="tag-cover">
                                    <Tag background="#005EA2">{file?.name}</Tag>
                                </div>
                            ) : null}
                        </div>
                        <Icon.Logout size={4} />
                        <label htmlFor="importTempId">
                            Drag & drop or <span>Choose file</span> to upload
                        </label>
                    </div>
                </label>
            </div>
            <div className="button-container">
                <Button
                    disabled={file === undefined || isLoading}
                    className="submit-btn"
                    type="button"
                    onClick={handleSubmit}>
                    Import
                </Button>
                <Button className="cancel-btn" type="button" onClick={handleCancel}>
                    Cancel
                </Button>
            </div>
        </div>
    );
};
