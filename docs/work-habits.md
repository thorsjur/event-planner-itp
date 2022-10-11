[nav](nav.md)

# Working habits

As a group we have decided on a few working habits that all of us will try our best to follow. We made these with the intention of improving group efficency and dynamic. We also think these habits will reduce misscommunications and create a clear view of how we want the project to be developed.

This document will be updated countinously as the group encounters new challenges, and find need for new working-habits.

### Documentation

We want to use [javadoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html) for public and "hard-to-understand" -methodes. Inline-comments will be used for all other purposes.

We want to create readme-markdowns for ever module, wich explain what the module contains and how it works.

Documentation is key to creating a clear and understandable project. This is why the nav should always be updated, so that every md is accessible from the nav, and the nav is accessible from all other md`s.

### Commits

Every commit should contain a descriptive title and refer to an issue. The message should include an more in-depth description of what is done and why. It should NOT include how it is done.

    Title (#issueNr)

    Message (What and why)

### Branches

We are all a bit new to using git, and try to avoid the newbie-mistakes. In the first deliverable we pushed to master, afterwards, we realised that this created a lot of mess. 

We decided that from now on we only push to master at each deliverable. On a daily basis we use the development-branch. Each issues should be branched out from the development-branch, and merged back to development when the issue is completely fixed.

The documentation-branch shall not be deleted after merge, as this will be continously used throughout the project. Ideally the documentation-branch should only be pushed to develop before the develop-branch shall be merged into master.

### Issues

The workload shall be divided into issues. Each issue should have an asignee. Every commit should be connected with a specific issue.

This way, the workload can be distributed evenly, and everyone know excactly what they are responsible for.

Issues that are time-dependant should be asigneed a due-date.

Bigger issues should be divided into smaller tasks , with appropriate description.

### Merges

Merges into the master-branch should be approved by every member before pushed.

Merges to development-branch should be approved by one other group-member.

Merges into other branches dont need approval, as this only creates delay.

### Test

We long to create a test-driven project. We did not manage to this this for deliverable 1, we tried for deliverable 2.

From now on, test-driven will be main practice.

We belive in test-driven programming, as this creates fewer redoes. Test-driven programming also contributes to "cleaner" code, as the test create the guidelines for the code that needs to be written.

Creating test before methodes also reduces human-errors. It is easy to subconsciously create test tailored for the methodes when you write the methodes before the tests. If the tests are writtes first, the correct behavior is forced.

### Meetings

The workload will be distributed at meetings. It is expected that all members have finished their workload before the next meeting. 

Misunderstandings and so on should be adressed at meetings or in the group-chat.

### Pair-programming

Pair-P. should be used whenever two or more members are working on the same issue. It is up to the members how they wish to program together. Wheter they want to use liveshare or create their own branches.