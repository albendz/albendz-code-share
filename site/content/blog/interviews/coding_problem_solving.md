---
title: Preparing for Coding and Problem Solving Interviews
description: Preparing for Coding and Problem Solving Interviews
date: Created
tags: interviews
---

# Preparing for Coding and Problem Solving

## Types of Coding Questions

**Leetcode:** Leecode style questions focus on problem solving, data structures & algorithms,
complexity analysis, clean code, and communication. These are generally focused on Leetcode medium questions.

**Pair programming:** Pair programming exercises focus primarily on communication, collaboration, and code quality while keeping
the problems more straightforward and less tricky.

**Take home assessments**: Take home assessments primarily focus on correctness of code as they are hard pass/fail assessments and
secondarily focus on clean code. They do not evaluate communication (unless there is a free text written portion) and usually do
not require complexity analysis but the tool will check your complexity.

## Preparing for Leetcode Questions

The goal of Leetcode question preparation is to be able to efficiently and cleanly implement a correct and optimal solution
to an approximately medium level Leetcode question within 30 minutes. Always check with your recruiter what difficulty level
to target. Most companies target easy to medium but there are some that recommend hard.

My recommended study plan is:
1. Review data structures and algorithm basics
2. "Open book", untimed practice questions to review what each topic's questions look like
3. Timed practice questions following the interview format

### Data structures and algorithms review

For each topic, study in whatever way is effective for you (videos, textbooks, blogs) and make sure you can fill in the following from memory:

```
<Data structure>
Description:
Time Complexity in terms of input size n:
- Access: O(x)
- Search: O(x)
- Insertion: O(x)
- Deletion: O(x)
Space Complexity: O(x)
Primary use cases:
- A
- B
- C
Comparison with other data structures
-
```

or for algorithms
```
<Algorithm>
Description:
Time complexity: O(x)
Space complexity: O(x)
Algorithm steps:
1. A
2. B
Data structures used:
- A
- B
Primary use cases:
- A
- B
Comparison with other algorithms:
- 
```

For algorithms, I highly recommend actually implementing them as variations on their
implementation come up in interview questions.

### Open book review

For the open book review, you can primarily focus on familiarizing yourself with the problem solving style,
identifying how each topic shows up in questions, and ensuring you know how to solve Leetcode problems in your language
of choice.

For each topic, I recommend doing 1 Leetcode easy and 2 Leetcode medium for this phase of review.

### Mock Interview Timed Review

For this type of review, focus on creating an interview-like situation where you solve each problem without interruption and without looking anything up with a goal of solving
Leetcode easy within 15 min and Leetcode medium within 30 min. Try to do at least one per topic and then focus on areas where you need more practice. After a certain point, I just use the Leetcode random question button and try to make sure I practice topics evenly.

Make sure to follow this rough outline when solving problems:
1. Clarify the problem: repeat back the problem and confirm you understand the examples, provide another example if you can think of one
2. Outline the method signature and input/output characteristics
3. Outline which data structure or algorithms you plan to use to solve the problem and why
4. Outline your coding approach - you can use comments to segment your code
5. Implement your solution
6. Walk through one of the provided test cases for your solution
7. Analyze the time and space complexity of the solution with respect to the input size; **Be careful to know the time and space complexity of your language's APIs**
8. List out additional test cases you would use when writing unit tests for the code; **Some interviews require you to write unit tests**. Make sure you know how to write
unit tests for your code in your language of choice.

### List of topics

This is the list of topics I use for Leetcode-style interviews. I have them split into prioritized groups based on how
frequently I see some problems and what I have time to study. I recommend you reference
what the company interview prep says and consider how much time you have to study to create your own set of higher or lower priority topics.

**More common topics:**

|Topic|Theory Review|Easy|Medium|
|---|---|---|---|
|Array| - [ ] |[]|[][]|
|Matrix| - [ ] |[]|[][]|
|Linked List| - [ ] |[]|[][]|
|HashMap| - [ ] |[]|[][]|
|Binary Tree| - [ ] |[]|[][]|
|Binary Search Tree| - [ ] |[]|[][]|
|Tree| - [ ] |[]|[][]|
|Graph| - [ ] |[]|[][]|
|Queue| - [ ] |[]|[][]|
|Stack| - [ ] |[]|[][]|
|Binary Search| - [ ] |[]|[][]|
|Graph Search| - [ ] |[]|[][]|
|Sorting: Merge Sort| - [ ] |[]|[][]|
|Sorting: Quick Sort| - [ ] |[]|[][]|
|Backtracking| - [ ] |[]|[][]|
|Strings| - [ ] |[]|[][]|
|Recursion| - [ ] |[]|[][]|
|Sets| - [ ] |[]|[][]|
|Sliding Window| - [ ] |[]|[][]|

**Less common topics** 

|Topic|Theory Review|Easy|Medium|
|---|---|---|---|
|Heaps| - [ ] |[]|[][]|
|Priority Queues (usually but not always a heap)| - [ ] |[]|[][]|
|Dynamic Programming| - [ ] |[]|[][]|
|Topological Sort| - [ ] |[]|[][]|
|Prefix sum| - [ ] |[]|[][]|
|Greedy| - [ ] |[]|[][]|
|Mathematics (probability, statistics, geometry, number theory)| - []| [] | [] []|

There are more topics that you can study that I've never seen come up
in interviews (ex. red-black trees, AVL trees, A*, Djikstra's algorithm). I have
rarely seen any company recommend I study these but always refer to the company's
preparation guidance.

### Leetcode Preparation Resources

There are many interview preparation resources out there. These are the resources
I've used most recently. I haven't ever paid for any interview preparation resources so 
I can't comment on whether paying is worth it or not.

* [GeeksForGeeks](https://www.geeksforgeeks.org/dsa-tutorial-learn-data-structures-and-algorithms/) for theory
* [Leetcode](https://Leetcode.com/problemset/)
* [Hackerrank](https://www.hackerrank.com/interview/preparation-kits)

### Leetcode Preparation Checklist

This is a table that I use when I'm getting ready to do interview preparation from scratch and am focusing on the theory and "open book" stages of preparation.

|Topic|Theory Review|Easy|Medium|
|---|---|---|---|
|Array| - [ ] |[]|[][]|
|Matrix| - [ ] |[]|[][]|
|Linked List| - [ ] |[]|[][]|
|HashMap| - [ ] |[]|[][]|
|Binary Tree| - [ ] |[]|[][]|
|Binary Search Tree| - [ ] |[]|[][]|
|Tree| - [ ] |[]|[][]|
|Graph| - [ ] |[]|[][]|
|Queue| - [ ] |[]|[][]|
|Stack| - [ ] |[]|[][]|
|Binary Search| - [ ] |[]|[][]|
|Graph Search| - [ ] |[]|[][]|
|Sorting: Merge Sort| - [ ] |[]|[][]|
|Sorting: Quick Sort| - [ ] |[]|[][]|
|Backtracking| - [ ] |[]|[][]|
|Strings| - [ ] |[]|[][]|
|Recursion| - [ ] |[]|[][]|
|Dynamic Programming| - [ ] |[]|[][]|
|Sets| - [ ] |[]|[][]|
|Sliding Window| - [ ] |[]|[][]|
|Topological Sort| - [ ] |[]|[][]|
|Prefix sum| - [ ] |[]|[][]|
|Greedy| - [ ] |[]|[][]|

## Preparing for Pair Programming and Take Home Assessments

Preparing for pair programming interviews requires:
* Familiarity with the common topics above but not necessarily able to solve tricker Leetcode problems
* Familiarity with common tools and libraries to build applications in your area of expertise (ex. web application frameworks, testing frameworks, http clients)
* Strong code design and organization (ex. Object-oriented design, functional design, package structure)
* (Company dependent) A working local development setup that where you can run and test code
* (Company dependent) A Github account and local setup to clone a Github repo

My recommendation is to do the first two parts of Leetcode prep - theory review and open book problems - and then locally build and run applications to refresh your skills
if you're not actively using them at work. The application I usually build is a simple REST service with multiple different APIs, unit tests, and a script that runs multiple GET and POST API calls, similar to an integration test. For example, build a product service that allows you to create, update, and search for products with a goal to write a script that calls your
service to run a request against each of the APIs.

## General Tips

* Double check what the interview style will be, recommended topics, time constraints, pass/fail criteria, and required development setup is for the interview
* If you find yourself having a hard time debugging in Leetcode, I recommend you spend the time to set up your local environment with unit tests so you can step through your code with a debugger
  * This also helps if you want to do practice questions offline
* If you are running your local development environment, make sure you already have a project set up with running unit tests on a dummy file at least a day before your interview and have it up and running an hour before your interview
* Know how the underlying data structures and algorithms in your language of choice work
  * For example, if you are a Java developer, know the difference between HashMap, TreeMap, and LinkedHashMap.
  * For example, if you are a Python developer, be aware that the `list()` data type is implemented as a dynamic/resizing array.
  * If your language has utilities like `.sort()`, `.substring()`, `.replace()`, `.find()`, etc., make sure you know the time and space complexity with special attention to when a new object is created vs. when something is updated in place.
     * I have seen several Leetcode solutions that claim to be O(n) but there's an implicit string copy or string traversal via a utility method and it's actually O(n^2)
* If a company tells you they will use a specific took ([Hackkerrank](https://www.hackerrank.com/interview/preparation-kits), [Codility](https://www.codility.com/), [CodeSignal](https://codesignal.com/), etc.) make sure to practice using it ahead of time

## Preparation Timeline

How long do you need to study Leetcode before being ready to interview?

* For me, it takes 4 - 6 weeks from day 0 if I study for 4 - 6 hours per week. I can usually complete the open book and timed reviews for the most common topics.
* FANG/MANG+ companies are often aware of how long this takes and can schedule interviews 3 - 4 weeks out. Smaller companies or companies with fewer openings are often looking to schedule interviews within 2 weeks.
* It usually takes at least 2 weeks to hear back from a company after applying and you may never hear back from them (don't take it personally).
* If you put all this together and use me as an example, it is best to be 2 - 3 weeks into your interview preparation - done with theory review and working through medium open book questions - before applying.

This does not include time to study and prepare for system design or behavioral interviews.