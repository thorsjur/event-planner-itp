[nav](../nav.md)

# Working habits

As a group we have decided on a few working habits that all of us will try our best to follow. We made these with the intention of improving group efficency and dynamic. We also think these habits will reduce misscommunications and create a clear view of how we want the project to be developed.

This document will be updated countinously as the group encounters new challenges, and find need for new working-habits.

### Documentation

We want to use [javadoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html) for public and "hard-to-understand" -methodes. JavaDoc will not be used for getters. Inline-comments will be used for all other purposes.

We want to create readme-markdowns for ever module, wich explain what the module contains and how it works. The readme should also contain information about how to test the module, and how to generate reports.

Documentation is key to creating a clear and understandable project. This is why the nav should always be updated, so that every md is accessible from the nav, and the nav is accessible from all other md`s.

### Commits

Every commit should contain a descriptive title and refer to an issue. The message should include a more in-depth description of what is done and why. It should NOT include how it is done. If other group-members have contributed to the code, they should be mentioned in the commit-message.

    Title (#issueNr)

    Message (What and why)
    
    (Co-authored-by: Name <email>)

### Branches

We are all a bit new to using git, and try to avoid the newbie-mistakes. In the first deliverable we pushed to master, afterwards, we realised that this created a lot of mess.

We decided that from now on we only merge to master at each deliverable. On a daily basis we use the development-branch. Each issues should be branched out from the development-branch, and merged back to development when the issue is completely fixed.

The master and development branch has been set to "protected". Meaning the branches cannot be pushed to. 

The documentation-branch shall not be deleted after merge, as this will be continously used throughout the project. Ideally the documentation-branch should only be pushed to develop before the develop-branch shall be merged into master.

### Issues

The workload shall be divided into issues. Each issue should have an asignee. Every commit should be connected with a specific issue.

This way, the workload can be distributed evenly, and everyone know excactly what they are responsible for.

Issues that are time-dependant should be asigneed a due-date.

Bigger issues should be divided into smaller tasks, with appropriate description.

Issues should also be labeled with appropriate description.

### Merges

Merges into the master-branch should be approved by every member before pushed.

Merges to development-branch should be approved and reviewed by one other group-member.

Merges into other branches dont need approval, as this only creates delay.

### Test

We have considered test-driven development. We concluded that this was not necessary. Instead, we aspired to test the project as objectively as possible. By this we mean creating the test with the correct application-behavior in mind. Contrary to creating the tests with the specific method in mind. This to make sure the tests are not tailorded to methodes.

### Meetings

The workload will be distributed at meetings. It is expected that all members have finished their workload before the next meeting. 

Misunderstandings and so on should be adressed at meetings or in the group-chat.

### Pair-programming

Pair-programming should be used whenever two or more members are working on the same issue. It is up to the members how they wish to program together. Wheter they want to use liveshare or create their own branches.