
6.3 Category APIs
Method	Endpoint	Purpose
POST	/categories	Create category
GET	/categories	List categories
PUT	/categories/{id}	Update
DELETE	/categories/{id}	Delete

6.4 Analytics APIs
Method	Endpoint	Purpose
GET	/analytics/daily	Daily analytics
GET	/analytics/weekly	Weekly analytics
GET	/analytics/monthly	Monthly analytics
GET	/analytics/heatmap	Calendar heatmap
GET	/analytics/streaks	Streak information
GET	/analytics/categories	Category performance

6.5 AI APIs
Method	Endpoint	Purpose
POST	/ai/chat	Chat with assistant
POST	/ai/tasks/parse	Parse natural language
POST	/ai/tasks/suggest	Task recommendations
POST	/ai/plans/daily	Generate daily plan
POST	/ai/reports/daily	Generate daily report
POST	/ai/reports/weekly	Generate weekly report
POST	/ai/reports/monthly	Generate monthly report
GET	/ai/conversations	Conversation list
GET	/ai/conversations/{id}	Conversation details

6.6 Planning and reminders APIs
Method	Endpoint	Purpose
POST	/planning/daily	Generate proposed schedule
GET	/planning/conflicts	Find scheduling conflicts
POST	/planning/reschedule	Apply approved changes
POST	/reminders	Create reminder
GET	/reminders	List reminders
DELETE	/reminders/{id}	Cancel reminder