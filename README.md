# LG Notification Channel Crash

I recently had a [Smoking Log](https://play.google.com/store/apps/details?id=com.ccswe.SmokingLog) user report an issue where the application would cause his LG V20 to soft reboot on start. 

In reviewing the changes between the version that worked vs. the one that first caused the reboot I was able to track down the issue to the following change:

Version 598 (worked):

    notificationChannel.setVibrationPattern(new long[] {0});

Version 600 (rebooted):

    notificationChannel.setVibrationPattern(new long[] {});
    
This application provides a reproducible method of creating the problem. Clicking on the bad notification channel button will immediately cause an LG V20 (are other devices impacted?) to reboot. Clicking on the good notification channel button will work as intended.

Video of the issue:

[![VIDEO - LG Notification Channel Crash](http://img.youtube.com/vi/kXIq_EfV27g/0.jpg)](http://www.youtube.com/watch?v=kXIq_EfV27g "LG Notification Channel Crash")
