import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { Icon } from './Icon';
import { Icons } from './types';

describe('When displaying Icons', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<Icon name="calendar" />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('applies the correct class names', () => {
        const { getByRole } = render(<Icon name="calendar" className="custom-class" />);

        const svgElement = getByRole('img', { hidden: true });

        expect(svgElement).toHaveClass('custom-class');
    });

    it('applies sizing class when sizing prop is provided', () => {
        const { getByRole } = render(<Icon name="calendar" sizing="large" />);

        const svgElement = getByRole('img', { hidden: true });

        expect(svgElement).toHaveClass('large');
    });

    it('applies sizing class when fallback size prop is provided', () => {
        const { getByRole } = render(<Icon name="calendar" sizing="small" />);

        const svgElement = getByRole('img', { hidden: true });

        expect(svgElement).toHaveClass('small');
    });

    it('is not visible to accessibility when aria-label and aria-labelledby are not provided', () => {
        const { getByRole } = render(<Icon name="calendar" />);

        const svgElement = getByRole('img', { hidden: true });

        expect(svgElement).toHaveAttribute('aria-hidden', 'true');
    });

    it('is not visible to accessibility when explicity hidden', () => {
        const { getByRole } = render(<Icon name="calendar" aria-hidden="true" aria-label="labeled" />);

        const svgElement = getByRole('img', { hidden: true });

        expect(svgElement).toHaveAttribute('aria-hidden', 'true');
    });

    it('is visible to accessibility when aria-label is provided', () => {
        const { getByRole } = render(<Icon name="calendar" aria-label="labeled" />);

        const svgElement = getByRole('img');

        expect(svgElement).toHaveAttribute('aria-hidden', 'false');
    });

    it('is visible to accessibility when aria-label is provided', () => {
        const { getByRole } = render(<Icon name="calendar" aria-labelledby="labeled-by" />);

        const svgElement = getByRole('img');

        expect(svgElement).toHaveAttribute('aria-hidden', 'false');
    });

    it.each([
        'accessibility_new',
        'accessible_forward',
        'account_balance',
        'account_box',
        'account_circle',
        'add',
        'add_circle',
        'add_circle_outline',
        'alarm',
        'alternate_email',
        'announcement',
        'api',
        'arrow_back',
        'arrow_downward',
        'arrow_drop_down',
        'arrow_drop_up',
        'arrow_forward',
        'arrow_upward',
        'assessment',
        'attach_file',
        'attach_money',
        'autorenew',
        'backpack',
        'bathtub',
        'bedding',
        'bookmark',
        'bug_report',
        'build',
        'calendar_today',
        'campaign',
        'camping',
        'cancel',
        'chat',
        'check',
        'check_box_outline_blank',
        'check_circle',
        'check_circle_outline',
        'checkroom',
        'chevron_left',
        'chevron_right',
        'clean_hands',
        'close',
        'closed_caption',
        'clothes',
        'cloud',
        'code',
        'comment',
        'connect_without_contact',
        'construction',
        'construction_worker',
        'contact_page',
        'content_copy',
        'coronavirus',
        'credit_card',
        'deck',
        'delete',
        'device_thermostat',
        'directions',
        'directions_bike',
        'directions_bus',
        'directions_car',
        'directions_walk',
        'do_not_disturb',
        'do_not_touch',
        'drag_handle',
        'eco',
        'edit',
        'electrical_services',
        'emoji_events',
        'error',
        'error_outline',
        'event',
        'expand_less',
        'expand_more',
        'facebook',
        'fast_forward',
        'fast_rewind',
        'favorite',
        'favorite_border',
        'fax',
        'file_download',
        'file_present',
        'file_upload',
        'filter_alt',
        'filter_list',
        'fingerprint',
        'first_page',
        'flag',
        'flickr',
        'flight',
        'flooding',
        'folder',
        'folder_open',
        'format_quote',
        'format_size',
        'forum',
        'github',
        'grid_view',
        'group_add',
        'groups',
        'hearing',
        'help',
        'help_outline',
        'highlight_off',
        'history',
        'home',
        'hospital',
        'hotel',
        'hourglass_empty',
        'hurricane',
        'identification',
        'image',
        'info',
        'info_outline',
        'insights',
        'instagram',
        'keyboard',
        'label',
        'language',
        'last_page',
        'launch',
        'lightbulb',
        'lightbulb_outline',
        'link',
        'link_off',
        'linkedin',
        'list',
        'local_cafe',
        'local_fire_department',
        'local_gas_station',
        'local_grocery_store',
        'local_hospital',
        'local_laundry_service',
        'local_library',
        'local_offer',
        'local_parking',
        'local_pharmacy',
        'local_police',
        'local_taxi',
        'location_city',
        'location_on',
        'lock',
        'lock_open',
        'lock_outline',
        'login',
        'logout',
        'loop',
        'mail',
        'mail_outline',
        'map',
        'masks',
        'medical_services',
        'menu',
        'military_tech',
        'more_horiz',
        'more_vert',
        'my_location',
        'navigate_before',
        'navigate_far_before',
        'navigate_far_next',
        'navigate_next',
        'near_me',
        'notifications',
        'notifications_active',
        'notifications_none',
        'notifications_off',
        'park',
        'people',
        'person',
        'pets',
        'phone',
        'photo_camera',
        'print',
        'priority_high',
        'public',
        'push_pin',
        'radio_button_unchecked',
        'rain',
        'reduce_capacity',
        'remove',
        'remove_circle',
        'report',
        'restaurant',
        'rss_feed',
        'safety_divider',
        'sanitizer',
        'save_alt',
        'schedule',
        'school',
        'science',
        'search',
        'security',
        'send',
        'sentiment_dissatisfied',
        'sentiment_neutral',
        'sentiment_satisfied',
        'sentiment_satisfied_alt',
        'sentiment_very_dissatisfied',
        'settings',
        'severe_weather',
        'share',
        'shield',
        'shopping_basket',
        'snow',
        'soap',
        'social_distance',
        'sort_arrow',
        'spellcheck',
        'star',
        'star_half',
        'star_outline',
        'store',
        'support',
        'support_agent',
        'text_fields',
        'thumb_down_alt',
        'thumb_up_alt',
        'timer',
        'toggle_off',
        'toggle_on',
        'topic',
        'tornado',
        'translate',
        'trending_down',
        'trending_up',
        'twitter',
        'undo',
        'unfold_less',
        'unfold_more',
        'update',
        'upload_file',
        'verified',
        'verified_user',
        'visibility',
        'visibility_off',
        'volume_off',
        'warning',
        'wash',
        'wifi',
        'work',
        'youtube',
        'zoom_in',
        'zoom_out',
        'zoom_out_map'
    ] as Icons[])('should display USWDS icons', (name: Icons) => {
        const { getByRole } = render(<Icon name={name} />);

        const icon = getByRole('img', { hidden: true });

        expect(icon.querySelector('use')?.getAttribute('xlink:href')).toContain(`#${name}`);
    });

    it.each([
        'table',
        'file',
        'file-pdf',
        'sort_asc_alpha',
        'sort_des_alpha',
        'sort_asc_numeric',
        'sort_des_numeric',
        'sort_asc_default',
        'sort_des_default'
    ] as Icons[])('should display icons added in the design system', (name: Icons) => {
        const { getByRole } = render(<Icon name={name} />);

        const icon = getByRole('img', { hidden: true });

        expect(icon.querySelector('use')?.getAttribute('xlink:href')).toContain(`#${name}`);
    });

    it.each([
        'calendar',
        'down-arrow-blue',
        'down-arrow-white',
        'drag',
        'expand',
        'expand-more',
        'group',
        'icon-dot-gov',
        'icon-https',
        'multi-drop',
        'multi-select',
        'navigate-next',
        'question',
        'reorder',
        'single-select',
        'subsection',
        'textarea',
        'textbox',
        'ungroup'
    ] as Icons[])('should display icons added in the design system as individual assets', (name: Icons) => {
        const { getByRole } = render(<Icon name={name} />);

        const icon = getByRole('img', { hidden: true });

        expect(icon.querySelector('use')?.getAttribute('xlink:href')).toContain(`/icons/${name}.svg`);
    });
});
