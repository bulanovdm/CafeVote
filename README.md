[![Build Status](https://travis-ci.org/bulanovdm/CafeVote.svg?branch=master)](https://travis-ci.org/bulanovdm/CafeVote)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/22ce02255f1c4776b636e57e5430a10d)](https://app.codacy.com/gh/bulanovdm/CafeVote?utm_source=github.com&utm_medium=referral&utm_content=bulanovdm/CafeVote&utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/bulanovdm/CafeVote/badge.svg?branch=master)](https://coveralls.io/github/bulanovdm/CafeVote?branch=master)

## CafeVote

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

##### The task is:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
- If it is before 11:00 we assume that he changed his mind.
- If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides a new menu each day.

**Docs**
> http://localhost:8080/swagger-ui/