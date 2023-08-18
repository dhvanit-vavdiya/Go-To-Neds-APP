# Go-To-Neds-APP


Task
Create an Android app that displays ‘Next to Go’ races using our API.
A user should always see 5 races, and they should be sorted by time ascending. Race should disappear
from the list after 1 min past the start time, if you are looking for inspiration look at
https://www.neds.com.au/next-to-go


Requirements
1. As a user, I should be able to see a time ordered list of races ordered by advertised start ascending
2. As a user, I should not see races that are one minute past the advertised start
Ans: - I implemented the advertised start but all the races are already finished, So it's not possible to setup that one minute past of race.
3. As a user, I should be able to filter my list of races by the following categories: Horse, Harness &amp;
   Greyhound racing
Ans: - Implemented the sort filter of different categories: Horse, Harness and Greyhound.
4. As a user, I can deselect all filters to show the next 5 from of all racing categories
Ans: - I implemented drop down menu so we can select a specific category of race.
5. As a user I should see the meeting name, race number and advertised start as a countdown for
   each race.
Ans: - Implemented as per requirement.
6. As a user, I should always see 5 races and data should automatically refresh
Ans: - I'm not sure that what function is required swipe to refresh or refresh button fo update the races.

Technical Requirements
- Use Jetpack compose : - Implemented
- Use Kotlin: - Implemented
- Has some level of testing. Full coverage is not necessary, but there should be at least some testing
  for key files. - Done
- Documentation - Done
- Use scalable layouts so your app can respond to font scale changes - Done
- Use material design elements over custom elements - Done
- (Optional) use data binding
- (Optional) add accessibility labels such that you can navigate via voiceover




Categories are defined by IDs and are the following:
- Greyhound racing: category_id: 9daef0d7-bf3c-4f50-921d-8e818c60fe61
- Harness racing: category_id: 161d9be2-e909-4326-8c2c-35ed71fb460b
- Horse racing: category_id: 4a2788f8-e825-4d36-9894-efd4baf1cfae
  GET https://api.neds.com.au/rest/v1/racing/?method=nextraces&amp;count=10
  Content-type: application/json
