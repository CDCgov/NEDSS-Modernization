class SortingPage {
    clickConditionColumnHeader() {
        cy.get('table thead tr th button').eq(0).click();
    }

    conditionsInOrder(type: 'ASC' | 'DSC') {
        this.checkOrder(0, type);
    }

    checkOrder(index: number, sortType: 'ASC' | 'DSC') {
        const list: any[] = [];
        cy.get('tbody tr').each(($tr) => {
            list.push($tr.find('td').eq(index).text());
        });
        let isOrdered = false;
        if (sortType === 'ASC') {
            isOrdered = this.isAscending(list);
        } else if (sortType === 'DSC') {
            isOrdered = this.isDescending(list);
        }
        expect(isOrdered).to.be.true;
    }

    isAscending(list: any[]) {
        return list.every((value, index, array) => {
            if (index === 0) {
                return true;
            }
            return value >= array[index - 1];
        });
    }

    isDescending(list: any[]) {
        return list.every((value, index, array) => {
            if (index === 0) {
                return true;
            }
            return value <= array[index - 1];
        });
    }
}

export const sortingPage = new SortingPage();
