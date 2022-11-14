[nav](../nav.md)

# Working habits

As a group we have decided on a few working habits that we have agreed to follow. We made these with the intention of improving group efficency and dynamic. We also think these habits will reduce miss communications and create a clear view of how we want the project to be developed.

This document will be updated countinously as the group encounters new challenges, and find need for new working-habits.

### **Documentation**

We use [JavaDoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html) to document public and "hard-to-understand" methods. JavaDoc will not be used for getters, because of their self explanatory nature. Inline comments will be used for all other purposes.

Each module has their own documentation written in markdown, which explains the module's contents and functionality. The documentation should also contain information about how to test the module, and how to generate reports.

Documentation is key to creating a clear and understandable project, for developers and reviewers. This is why the navigation document should always be updated, so that every markdown file is accessible from the nav, and the nav is accessible from all other md`s.

### **Commits**

Every commit should contain a descriptive title and refer to an issue. The message should include a more in-depth description of what is done, and why. It should NOT include implementation details. If other members of the group have contributed to the code, they should be mentioned in the commit-message as

    Title (#issueNr)

    Body (What and why)
    
    Co-authored-by: Firstname Lastname <email>

### **Branches**

We are all a bit new to using git, and try to avoid rookie mistakes. In the first deliverable we pushed to master, afterwards, we realised that this created a lot of mess.

We decided that from now on we only merge to master at each deliverable. On a daily basis we use the development branch. Each issue should be branched out from the development branch, and merged back to dev when the issue is completely fixed. The qa branch, or the quality assurance branch is reserved for testing purposes. This requires the branches to update from the other quite regularly. The documentation branch is used for writing documentation, to keep it separate from other branches.

The master and development branch has been set to "protected". Meaning the branches cannot be pushed to, to protect the branches from unwanted commits.

The documentation branch shall not be deleted after merge, as this will be continously used throughout the project. Ideally the documentation branch should only be pushed to develop before the develop branch shall be merged into master.

### **Issues**

The workload shall be divided into issues. Each issue should have an asignee. Every commit should be connected with a specific issue.

This way, the workload can be distributed evenly, and everyone know exactly what they are responsible for.

Issues that are time dependent should be assigned a due-date.

Bigger issues should be divided into smaller tasks, with appropriate description.

Issues should also be labeled with appropriate description.

### **Merges**

Merges into the master branch should be approved by every member before pushed.

Merges to development branch should be approved and reviewed by one other group-member.

Merges into other branches dont need approval, as this will create unecessary delays to development.

### **Test**

We have considered test-driven development. We concluded that this was not necessary. Instead, we aspired to test the project as objectively and thoroughly as possible. By this we mean creating the test with the correct application-behavior in mind. Contrary to creating the tests with the specific method in mind. This to make sure the tests are not tailored to methods.

### **Meetings**

The workload will be distributed at meetings. It is expected that all members have finished their workload before the next meeting. 

Meetings are held twice a week, to ensure continual progress.

Misunderstandings and similar issues should be adressed at meetings or in the group's online chat.

### **Pair programming**

Pair programming should be used whenever two or more members are working on the same issue. It is up to the members how they wish to program together, but it should be one person programming and one observing. This allows the observer to think freely of the problem, without worrying about writing code. If pair programming is done, the partner should be credited in the commit message as described above.