# Introduction
This project aims to create a system for primary school recruitment agencies that provide the neccessary information all on 1 platform for both their staff and school clients. This software will addresses inefficiencies in connecting education recruitment agencies, supply staff, and primary schools, fostering smoother collaboration between the 3.

The project involves the development of a CRM type platform that simplifies recruitment and staffing for primary schools through agencies. It will enable:

- Recruitment agencies to share detailed candidate profiles with schools.
- Schools to efficiently review candidates and book suitable staff to meet specific needs.
- Supply staff, such as teachers and teaching assistants, to create and manage profiles, highlight experience, and upload essential documents(timesheets).
- Schools will be able to provide written feedback for supply staff at the end of their contract that can then be placed on their profile for other schools clients to be able to see. 

# Background
Schools across the UK are facing a shortage of teachers and teaching assistants, leading to a growing reliance on agencies to provide supply staff. This has become a costly solution, as highlighted in the articles linked below:  

- [£13 million spent on supply teachers in Caerphilly last year](https://www.southwalesargus.co.uk/news/24509537.13m-spent-caerphilly-supply-teachers-last-year/)
- [School staff recruitment 'challenging' says Hertfordshire agency](https://www.bbc.co.uk/news/uk-england-beds-bucks-herts-64810330)
  
Primary schools spend a lot on agency staff to cover teaching and support roles. To get the best value, it’s important for schools to have a insight into candidates interests/experience. 

From my experience working with several teaching agencies for both primary schools and private nurseries, most communication between agencies, supply staff, and schools happens through emails, phone calls, and text messages. Some education recruitment agency still rely on paper for timesheets. However, this process can be inefficient and time-consuming.  

This is article brings up some interesting points about more engagement needed with supply staff as well as mention of software being used in both Scotland, Northern Ireland and soon in Wales to make it easier for both schools and supply staff to find available staff quickly:
- [Scotland and Northern Ireland have it sussed - it’s time England caught up with its neighbours](https://www.theheadteacher.com/staff-management/recruitment/the-teacher-supply-system-isnt-working)

As highlighted in this article, there is already software in use in Scotland, Northern Ireland, and soon in Wales, which makes it easier for schools and supply staff to find suitable candidates quickly. Unfortunately, such a system is not yet available in England, despite having the highest number of schools in the UK.

# Outline

For the scoop of my project, I will be focusing on London schools for the most part. I want to have a main focus on 2 user groups, which is the candiadate and the school client. Although the user persona of a recruiter is still relevant. 

Below listed is will be the key features of the software:

1. **Supply Staff**: Staff can create and update profiles, manage availability through a calendar, and deal with timesheets. This can add a bit more of personal approach when interacting with schools and recruiters
3. **Facilitate Feedback and Reviews**: Schools can provide feedback on supply staff performance, which can be used to build professional profiles and assist future hiring decisions.

This is my MVP for the creation on this project:
Supply Staff:
- Profile creation with qualifications, work history, and school feedback.
- Calendar to mark avaliability.
- Clear details on assigned schools, locations, and roles.
- A personal work history log to build experience and credibility.

In terms of scalability I would create these features for recruiter and school:
  
Schools:
- Ability to browse candidate profiles with qualifications and feedback.
- Give recruiter their selection from the profile.
- Access to a history of supply staff who have previously worked at their school.
- Simplified feedback mechanism for supply staff performance.

Recruiters:
- Platform to manage schools clients and supply staff.
- Tools to manage communication and assignments efficiently.
- Scheduling features for assigning shifts and tracking staff availability.
- Overview of staff performance based on school feedback.

  I am considering using Java Springboot to build my API to handle the data from supply staff and information about school. I might consider using Http Server.
  I am considering using React/Next.js for building the frontend. 

# Conclusion
The development of a CRM type system for primary school recruitment represents a step forward in addressing the challenges faced by recruitment agencies, supply staff, and schools in the education sector. By offering a user-friendly platform, the system aims to enhance  collaboration across all parties involved. 

This solution not only simplifies the recruitment and scheduling process but also empowers supply staff to manage their profiles, availability, and professional growth effectively. Schools benefit from access to detailed candidate profiles, scheduling tools, and performance reviews, enabling them to make informed hiring decisions and improve classroom outcomes.

# Other Projects Ideas

LendLogic

I previously did a group project on mortgage tool for a financial company. The project is linked here: [https://github.com/horiaomar25/Lend-Logic](url) .

We focused on first time buyers and those looking to remortgage. I would like to narrow the scope on this project and focus on one user group (First Time Buyers). The first time buyer dashboard can be better developed.




