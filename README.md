# Counting App
Build a Basic Android App with a Local Database Using Room

## Documentation
### Overview
> Simple Android App with Three Activities and Local Database Using Room

> Main Activity -  Launcher Activity
> - Settings Button Directs User to Settings Activity
> - Three Counter Event Buttons 
>   - Updates Total Count Displayed and Respective Event Counter on Click
>   - Ability to Rename Counters in Settings
>   - Redirects to Settings if Events are Unnamed (At Application Start)
> - Displays Total Count
> - Show My Counts Button Directs User to Data Activity

> Settings Activity - Child Activity of Main
> - Displays Form to Name All Buttons and Define Max Count
> - Toggle Between Edit and Display Mode
>   - Edit Mode: Displays Editable Form W/ Currently Saved Values or Display Hint if No Previous Values
>   - Display Mode: Displays Uneditable Form W/ Currently Saved Values by Default
> - Click Save Button to Update Settings and Toggle Back to Display Mode
> - Validate Inputs with Toast Messages
>   - All Form Inputs Must Be Filled
>   - Button Names Can Only Contain Up to 20 Alphabetical Characters and Spaces
>   - Max Count Can Only Be a Number Between 5 and 200
> - Navigate Back to Main Activity with Up Navigation

> Data Activity - Child Activity of Main
> - Displays Count for Each Event and Total Number of Events
> - Displays Scrollable List of All Events in Order
> - Toggle Between Event Names and Numbered Modes
>   - Default Mode: Displays Event Names
>   - Button Number Mode: Displays Event Numbered 1-3
> - Navigate Back to Main Activity with Up Navigation

## Dependencies
Used Room to Create a Local Database
