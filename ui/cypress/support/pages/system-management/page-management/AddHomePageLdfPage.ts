import BasePage from '../../BasePage';

enum Selector {
    TYPE_DROPDOWN = 'input[name=typeCd_textbox]',
    LABEL_TEXTAREA = 'textarea[id=label]',
    DISPLAY_ORDER_INPUT = 'input[id=orderNbr]',
    TOOLTIP_TEXTAREA = 'textarea[id=toolTip]',
    LINK_URL_TEXTAREA = 'textarea[id=linkUrl]',
    COMMENTS_TEXTAREA = 'textarea[id=comment]',
    SUBMIT_BUTTON = 'input[value=Submit]'
}
export default class AddHomePageLdfPage extends BasePage {
    constructor() {
        super('/LocalFields.do?method=createLoadLDF#localField');
    }

    createLdf(ldf: {
        type: 'Hyperlink' | 'Subheading (for display only)' | 'Comments (Read only text)';
        label: string;
        displayOrder: string;
        comment: string;
        linkUrl?: string;
        tooltip?: string;
    }): void {
        this.setType(ldf.type);
        this.setLabel(ldf.label);
        this.setDisplayOrder(ldf.displayOrder);
        if (ldf.type === 'Hyperlink') {
            this.setLinkUrl(ldf.linkUrl ?? '');
        } else {
            this.setTooltip(ldf.tooltip ?? '');
        }
        this.setComments(ldf.comment);
        this.clickSubmit();
    }

    setType(type: string): void {
        this.setText(Selector.TYPE_DROPDOWN, type);
    }

    setLabel(label: string): void {
        this.setText(Selector.LABEL_TEXTAREA, label);
    }

    setDisplayOrder(displayOrder: string): void {
        this.setText(Selector.DISPLAY_ORDER_INPUT, displayOrder);
    }

    setComments(comment: string): void {
        this.setText(Selector.COMMENTS_TEXTAREA, comment);
    }

    setLinkUrl(linkUrl: string): void {
        this.setText(Selector.LINK_URL_TEXTAREA, linkUrl);
    }

    setTooltip(tooltip: string): void {
        this.setText(Selector.TOOLTIP_TEXTAREA, tooltip);
    }

    clickSubmit(): void {
        this.click(Selector.SUBMIT_BUTTON);
    }
}
