
#### Testing Instructions
1. Navigate to the Patient Search form
2. Tab to the Patient ID field
3. Verify initial announcements
4. Test with various inputs:
   - Valid: "123 456, 789; 012"
   - Invalid: "abc123"
5. Verify error messages are announced

#### Known Issues
- None currently documented

---

## General Screen Reader Testing Setup

### Recommended Screen Readers
- NVDA (Windows, Free): [Download](https://www.nvaccess.org/)
- VoiceOver (macOS, Built-in): Command + F5 to enable, on Mac just enable in System Preferences > Accessibility > VoiceOver

### Key Commands
#### NVDA
- Insert + Space: Toggle NVDA
- Insert + Down Arrow: Read from current position
- Ctrl: Stop reading

#### VoiceOver
- Command + F5: Toggle VoiceOver
- VO (Control + Option) + A: Start reading
- Control: Stop reading

### Testing Checklist
- [ ] Focus announcement
- [ ] Helper text announcement
- [ ] Restriction announcement
- [ ] Error message announcement
- [ ] Keyboard navigation
- [ ] Input validation feedback

# Screen Reader Interactions Documentation

## Patient Search Form

### Patient ID Field
**Location**: `BasicInformation.tsx`

#### Expected Announcements

1. **On Focus**:
   - "Patient ID(s) Patient ID(s) -Separate IDs by commas, semicolons, or spaces, edit text"
   - "You are currently on a text field, inside of web content. To enter text in this field, type. To exit this web area, press Control-Option-Shift-UpArrow." 

2. **On Invalid Input**:
   - "Only numbers, spaces, commas, and semicolons are allowed"

3. **On Valid Input**:
   - No specific announcement (standard character echo)

#### Tested Screen Readers

| Screen Reader | Browser | OS | Status | Notes |
|--------------|---------|----|---------| ------|
| VoiceOver    | Chrome  | macOS 15   | ✅ | All announcements clear |

#### Keyboard Interaction
- Tab: Moves focus to the field
- Numbers 0-9: Entered normally
- Comma (,): Entered normally
- Semicolon (;): Entered normally
- Space: Entered normally
- Other keys: Blocked with prevention message