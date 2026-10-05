param
(
    [string]$title,
    [string]$message,
    $icon
)

Add-Type -AssemblyName System.Windows.Forms

$notify = New-Object System.Windows.Forms.NotifyIcon
$notify.Icon = [System.Drawing.SystemIcons]::Information
$notify.visible = $true

$notify.ShowBalloonTip(5000, $title, $message, [System.Windows.Forms.ToolTipIcon]::None)
$notify.Dispose()