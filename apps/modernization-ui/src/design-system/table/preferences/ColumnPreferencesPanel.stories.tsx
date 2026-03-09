import { Meta, StoryObj } from '@storybook/react-vite';
import { MemoryRouter } from 'react-router';
import { ColumnPreferenceProvider } from './useColumnPreferences';
import { ColumnPreferencesPanel } from './ColumnPreferencesPanel'; // Moved after dependencies

const meta = {
    title: 'Design System/Table/ColumnPreferencesPanel',
    component: ColumnPreferencesPanel
} satisfies Meta<typeof ColumnPreferencesPanel>;

export default meta;

type Story = StoryObj<typeof meta>;

const storageKey = 'storybook.preferences.columns';
const columns = [
    { id: 'id', name: 'ID', visible: true },
    { id: 'name', name: 'Name', visible: true },
    { id: 'email', name: 'Email', visible: false }
];

const preferences = columns.map((column) => ({
    id: column.id,
    name: column.name,
    moveable: true,
    toggleable: true
}));

export const Default: Story = {
    decorators: [
        (Story) => (
            <MemoryRouter>
                <ColumnPreferenceProvider id={storageKey} defaults={preferences}>
                    <Story />
                </ColumnPreferenceProvider>
            </MemoryRouter>
        )
    ],
    args: {
        close: () => {}
    }
};

export const InPanel: Story = {
    decorators: Default.decorators,
    args: Default.args,
    render: (args) => (
        <div style={{ width: '300px', height: 'auto', backgroundColor: 'white', border: '1px solid black' }}>
            <ColumnPreferencesPanel {...args} />
        </div>
    )
};
